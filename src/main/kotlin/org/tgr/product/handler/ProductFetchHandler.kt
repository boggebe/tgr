package org.tgr.product.handler

import org.slf4j.LoggerFactory
import org.tgr.product.model.Product
import org.tgr.product.service.NameService
import org.tgr.product.service.PriceService
import ratpack.exec.Operation
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.jackson.Jackson

class ProductFetchHandler : Handler {
    private val log = LoggerFactory.getLogger(ProductFetchHandler::class.java)

    override fun handle(ctx: Context) {
        val id: String = ctx.pathTokens["id"] ?: ""
        val product = Product(id)
        log.debug("fetching product: $id")

        val nameService = ctx.get(NameService::class.java)
        nameService.getNameForProduct(id).then { name ->
            product.name = name as String?
        }

        val priceService = ctx.get(PriceService::class.java)
        priceService.getPriceForProduct(id).then { price ->
            product.price = price
        }

        Operation.of {
            ctx.render(Jackson.json(product))
        }.then()
    }
}