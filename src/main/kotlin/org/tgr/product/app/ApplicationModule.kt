package org.tgr.product.app

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Scopes
import com.google.inject.Singleton
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import org.tgr.product.handler.ProductFetchHandler
import org.tgr.product.handler.ProductPriceUpdateHandler
import org.tgr.product.handler.RequestLoggingHandler
import org.tgr.product.service.NameService
import org.tgr.product.service.NameServiceImpl
import org.tgr.product.service.PriceService
import org.tgr.product.service.PriceServiceImpl

class ApplicationModule : AbstractModule() {

    override fun configure() {
        bind(ProductFetchHandler::class.java)
        bind(ProductPriceUpdateHandler::class.java)
        bind(RequestLoggingHandler::class.java)
        bind(NameService::class.java).to(NameServiceImpl::class.java).`in`(Scopes.SINGLETON)
        bind(PriceService::class.java).to(PriceServiceImpl::class.java).`in`(Scopes.SINGLETON)
    }

    @Provides
    @Singleton
    fun config(): Config {
        return ConfigFactory.load("application.json")
    }
}