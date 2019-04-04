package org.tgr.product.service

import com.datastax.oss.driver.api.core.CqlSession
import com.google.inject.Inject
import com.typesafe.config.Config
import org.slf4j.LoggerFactory
import ratpack.exec.Blocking
import ratpack.exec.Promise

class PriceServiceImpl @Inject constructor(val conf: Config) : PriceService {

    private val log = LoggerFactory.getLogger(PriceServiceImpl::class.java)

    private var session: CqlSession

    init {
        log.info("initializing cassandra session")
        session = CqlSession.builder()
            .withKeyspace(conf.getConfig("tgr").getConfig("cassandra").getString("keyspace"))
            .build()
    }

    override fun getPriceForProduct(id: String): Promise<Float?> {
        return Blocking.get {
            val row = session.execute("select * from product_price where id = $id").one()
            row?.getFloat("price") ?: 0.0f
        }
    }

    override fun setPriceForProduct(id: String, price: Float): Promise<Float?> {
        return Blocking.get {
            val row = session.execute("update product_price set price = $price where id = $id").one()
            row?.getFloat("price") ?: 0.0f
        }
    }
}