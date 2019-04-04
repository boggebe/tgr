package org.tgr.product.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.Inject
import com.typesafe.config.Config
import org.slf4j.LoggerFactory
import ratpack.exec.Promise
import ratpack.http.client.HttpClient
import java.lang.IllegalStateException
import java.net.URI

class NameServiceImpl @Inject constructor(conf: Config) : NameService {
    private val log = LoggerFactory.getLogger(NameServiceImpl::class.java)

    @Inject
    var httpClient: HttpClient? = null

    private val url = conf.getConfig("tgr").getConfig("nameServer").getString("url")
    private val query = conf.getConfig("tgr").getConfig("nameServer").getString("query")

    override fun getNameForProduct(id: String): Promise<Any?> {
        var http = httpClient
        if (http != null) {
            return http.get(URI("$url$id?$query"))
                .map { response ->
                    val productInfo = ObjectMapper().readValue<Map<String, Any>>(response.body.text, object : TypeReference<Map<String, Any>>() {})
                    var name = ""
                    // TODO: there has to be a better way to manage this map access
                    if (!((productInfo["product"] as Map<String, Any>)["item"] as Map<String, Any>).isEmpty()) {
                        name = ((((productInfo["product"] as Map<String, Any>)["item"] as Map<String, Any>)["product_description"] as Map<String, Any>)["title"]).toString()
                    }
                    name
                }
        } else {
            throw IllegalStateException("httpClient is null")
        }
    }
}