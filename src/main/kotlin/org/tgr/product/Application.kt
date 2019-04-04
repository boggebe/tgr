package org.tgr.product

import org.slf4j.LoggerFactory
import org.tgr.product.app.ApplicationModule
import org.tgr.product.handler.ProductFetchHandler
import ratpack.guice.Guice
import ratpack.server.RatpackServer

class Application {
    companion object {
        private val log = LoggerFactory.getLogger(Application::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            log.debug("starting application")

            try {
                createServer().start()
            } catch (e: Exception) {
                log.error("error starting application", e)
                System.exit(-1)
            }
        }

        private fun createServer() : RatpackServer = RatpackServer.of { spec -> spec
            .registry(Guice.registry { bindings -> bindings
                .module(ApplicationModule::class.java)
            })

            .handlers { chain -> chain
                .prefix("api") { api -> api
                    .prefix("products") { products -> products
                        .get(":id", ProductFetchHandler::class.java)
                        .all { ctx -> ctx
                            .render("listing products")
                        }
                    }
                }
            }
        }
    }
}