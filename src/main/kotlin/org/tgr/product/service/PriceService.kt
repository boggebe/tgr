package org.tgr.product.service

import ratpack.exec.Promise

interface PriceService {
    fun getPriceForProduct(id: String): Promise<Float?>

    fun setPriceForProduct(id: String, price: Float): Promise<Float?>
}