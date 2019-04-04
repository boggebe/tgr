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

    given("this is a test") {
        val aut = MainClassApplicationUnderTest(Application::class.java)

        on("fetch of a valid product") {
            val resp = aut.httpClient.get("api/products/13860428").body.text
            it("has a valid response") {
                assertThat(resp, equalTo("{\"id\":\"13860428\",\"name\":\"The Big Lebowski (Blu-ray)\",\"price\":19.98}"))
            }
        }
    }
})