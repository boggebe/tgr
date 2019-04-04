package org.tgr.product.handler

import org.slf4j.LoggerFactory
import ratpack.handling.Context
import ratpack.handling.Handler

class RequestLoggingHandler : Handler {
    private val log = LoggerFactory.getLogger(RequestLoggingHandler::class.java)

    override fun handle(ctx: Context) {
        log.info("handling request: ${ctx.request.path}?${ctx.request.query}")
        ctx.next()
    }
}