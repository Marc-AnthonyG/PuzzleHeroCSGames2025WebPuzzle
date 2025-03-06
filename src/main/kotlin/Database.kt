package com.csgames

import com.csgames.buttons.buttonGameRoute
import com.csgames.wait.infra.PlayerRepository
import com.csgames.wait.waitGameRoute
import io.ktor.server.application.*

fun Application.configureDatabases() {
  val playerRepository = PlayerRepository()

  waitGameRoute(playerRepository)
  buttonGameRoute()
}
