package com.csgames

import com.csgames.buttons.buttonGameRoute
import com.csgames.common.configureStyles
import com.csgames.common.navPage
import com.csgames.lostpassword.lostPasswordGameRoute
import com.csgames.snake.GameRepository
import com.csgames.snake.snakeGameRoute
import com.csgames.wait.infra.PlayerRepository
import com.csgames.wait.waitGameRoute
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.ratelimit.*
import io.ktor.server.routing.*
import io.ktor.server.webjars.*
import kotlin.time.Duration.Companion.seconds

fun main(args: Array<String>) {
  EngineMain.main(args)
}

fun Application.module() {
  configureHTTP()
  configureStyles()
  install(ContentNegotiation)
  install(Webjars)
  install(RateLimit) { global { rateLimiter(limit = 8, refillPeriod = 1.seconds) } }
  routing { get("/") { call.respondHtml { navPage() } } }
  waitGameRoute(PlayerRepository())
  buttonGameRoute()
  snakeGameRoute(GameRepository())
  lostPasswordGameRoute()
}
