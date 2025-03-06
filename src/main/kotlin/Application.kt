package com.csgames

import com.csgames.common.configureStyles
import com.csgames.common.navPage
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.server.webjars.*

fun main(args: Array<String>) {
  EngineMain.main(args)
}

fun Application.module() {
  configureHTTP()
  routing { get("/") { call.respondHtml { navPage() } } }
  configureStyles()
  install(ContentNegotiation)
  install(Webjars)
  configureDatabases()
}
