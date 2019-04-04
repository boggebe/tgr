package org.tgr.product.handler

import com.datastax.oss.driver.api.core.CqlSession
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.tgr.product.model.Product
import ratpack.exec.Blocking
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.http.client.HttpClient
import ratpack.jackson.Jackson
import java.net.URI

class ProductFetchHandler : Handler {
    // TODO: log things
    private val log = LoggerFactory.getLogger(ProductFetchHandler::class.java)

    private var session: CqlSession // TODO: convert/move to service and clean up

    init {
        session = CqlSession.builder()
            .withKeyspace("tgr")
            .build()
    }

    override fun handle(ctx: Context) {
        val id: String? = ctx.pathTokens["id"]
        Blocking.get {
            val price: Float = getPrice(ctx, id)
            Product(id.toString(), "", price)
        }.then { product -> product
            val httpClient = ctx.get(HttpClient::class.java)
            httpClient.get(URI("http://redsky.target.com/v2/pdp/tcin/$id?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics"))
                .then { response -> response
                    val productInfo = ObjectMapper().readValue<Map<String, Any>>(response.body.text, object : TypeReference<Map<String, Any>>() {})
                    product.name = ((((productInfo["product"] as Map<String, Any>)["item"] as Map<String, Any>)["product_description"] as Map<String, Any>)["title"]).toString()
                    ctx.render(Jackson.json(product))
                }
        }
    }

    private fun getPrice(ctx: Context, productId: String?): Float {
        val row = session.execute("select * from product_price where partNumber = $productId").one()
        val price: Float = row?.getFloat("price") ?: 0.0f
        return price
    }
}