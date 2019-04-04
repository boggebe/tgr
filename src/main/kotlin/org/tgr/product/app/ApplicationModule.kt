package org.tgr.product.app

import com.google.inject.AbstractModule
import org.tgr.product.handler.ProductFetchHandler

class ApplicationModule : AbstractModule() {

    override fun configure() {
        bind(ProductFetchHandler::class.java)
    }
}