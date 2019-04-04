package org.tgr.product.service

import org.tgr.product.model.Product
import ratpack.exec.Promise

interface PriceService {
    fun getPriceForProduct(id: String): Promise<Product.Price?>

    fun setPriceForProduct(id: String, price: Product.Price): Promise<Product.Price?>
}