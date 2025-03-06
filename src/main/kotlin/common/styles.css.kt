package com.csgames.common

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.css.CssBuilder

fun Application.configureStyles() = routing {
  get("/styles.css") { call.respondCss { rule(".pl") {} } }
}

suspend inline fun ApplicationCall.respondCss(builder: CssBuilder.() -> Unit) {
  this.respondText(CssBuilder().apply(builder).toString(), ContentType.Text.CSS)
}
