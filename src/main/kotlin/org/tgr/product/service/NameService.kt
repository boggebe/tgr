package org.tgr.product.service

import ratpack.exec.Promise

interface NameService {
    fun getNameForProduct(id: String): Promise<Any?>
}