package org.tgr.product.app

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.tgr.product.Application
import ratpack.test.MainClassApplicationUnderTest

object ApplicationSpek: Spek({

    given("the application is running") {
        val aut = MainClassApplicationUnderTest(Application::class.java)

        on("fetch of a valid product") {
            val resp = aut.httpClient.get("api/products/13860428").body.text
            it("has a valid response") {
                assertThat(resp, equalTo("{\"name\":\"The Big Lebowski (Blu-ray)\",\"price\":19.98,\"id\":\"13860428\"}"))
            }
        }

        on("set price of a product") {
            var resp = aut.httpClient.requestSpec { spec ->
                    spec.body.text("10.99")
                }.put("api/products/12345/price").body.text
            it("updates successfully") {
                assertThat(resp, equalTo("{\"result\": \"ok\"}"))

                resp = aut.httpClient.get("api/products/12345").body.text
                it("has the updated price when retrieved") {
                    assertThat(resp, equalTo("{\"name\":\"\",\"price\":10.99,\"id\":\"12345\"}"))
                }
            }
        }
    }
})