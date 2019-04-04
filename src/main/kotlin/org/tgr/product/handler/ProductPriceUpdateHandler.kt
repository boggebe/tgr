package org.tgr.product.handler

import org.slf4j.LoggerFactory
import org.tgr.product.service.PriceService
import ratpack.handling.Context
import ratpack.handling.Handler

class ProductPriceUpdateHandler : Handler {
    private val log = LoggerFactory.getLogger(ProductPriceUpdateHandler::class.java)

    override fun handle(ctx: Context) {
        val id: String = ctx.pathTokens["id"] ?: ""

        val priceService = ctx.get(PriceService::class.java)
        ctx.request.body.then { body ->
            val price = body.text.toFloat()
            priceService.setPriceForProduct(id, price).then {
                ctx.response.status(200)
                ctx.render("{\"result\": \"ok\"}")
            }
        }
    }
}