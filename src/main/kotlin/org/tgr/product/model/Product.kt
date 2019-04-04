package org.tgr.product.model

/**
 * A Product.
 */
class Product(val id: String) {

    var name: String? = null
    var price: Price? = null

    class Price(var value: Float?, var currency: String?) {
        constructor(): this(null, null)
    }
}