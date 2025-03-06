package com.csgames.wait

import com.csgames.wait.infra.PlayerRepository
import com.csgames.wait.model.Player
import com.csgames.wait.model.Stage
import com.csgames.wait.view.waitDescriptionWrapper
import com.csgames.wait.view.waitPageDisplay
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.plugins.*
import io.ktor.server.routing.*
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit
import kotlinx.datetime.Clock

fun Application.waitGameRoute(playerRepository: PlayerRepository) {
  routing {
    get("/wait") {
      val player = Player()
      playerRepository.addPlayer(player)
      call.respondHtml { waitPageDisplay(player.id) }
    }

    post("/wait/{playerId}") {
      try {

        val playerId = call.parameters["playerId"] ?: throw BadRequestException("")
        val player = playerRepository.findById(playerId) ?: throw NotFoundException("")

        val currentTime = Clock.System.now()

        when (player.stage) {
          Stage.STAGE_ONE -> {
            val updatedPlayer =
                player.copy(stage = Stage.STAGE_TWO, time = currentTime.plus(5.minutes))
            playerRepository.updatePlayer(updatedPlayer)

            call.respondHtml {
              waitDescriptionWrapper(
                  "Yo, I am busy can you call me in 5 minutes / Yo, je suis occupé peux-tu m'appeler dans 5 minutes",
                  updatedPlayer.time.minus(currentTime).toInt(DurationUnit.SECONDS))
            }
          }
          else -> {
            val targetTime = player.time

            if (currentTime < targetTime) {
              playerRepository.removePlayer(player.id)
              throw BadRequestException("")
            } else if (currentTime > targetTime.plus(10.seconds)) {
              playerRepository.removePlayer(player.id)
              throw BadRequestException("")
            } else {
              when (player.stage) {
                Stage.STAGE_TWO -> {
                  val updatedPlayer =
                      player.copy(stage = Stage.STAGE_THREE, time = currentTime.plus(30.minutes))
                  playerRepository.updatePlayer(updatedPlayer)

                  call.respondHtml {
                    waitDescriptionWrapper(
                        "Good timing! However, I am still busy ): Can you call me back in 30 minutes? / Bon timing! Cependant, je suis toujours occupé ): Peux-tu me rappeler dans 30 minutes?",
                        updatedPlayer.time.minus(currentTime).toInt(DurationUnit.SECONDS))
                  }
                }
                Stage.STAGE_THREE -> {
                  val updatedPlayer =
                      player.copy(stage = Stage.STAGE_FOUR, time = currentTime.plus(1.hours))
                  playerRepository.updatePlayer(updatedPlayer)

                  call.respondHtml {
                    waitDescriptionWrapper(
                        "Damm, mb I am still busy! Call me back in 1 hour for the flag. / Zut, je suis toujours occupé! Rappelle-moi dans 1 heure pour le drapeau.",
                        updatedPlayer.time.minus(currentTime).toInt(DurationUnit.SECONDS),
                        doomMode = true)
                  }
                }
                Stage.STAGE_FOUR -> {
                  playerRepository.removePlayer(player.id)
                  call.respondHtml {
                    waitDescriptionWrapper(
                        "Congratulations! Here's your flag: CSGAMES-22ea953f-d05e-4ec4-818e-a869a2cccda2 / Félicitations! Voici ton drapeau: CSGAMES-22ea953f-d05e-4ec4-818e-a869a2cccda2")
                  }
                }
                Stage.STAGE_ONE -> TODO()
              }
            }
          }
        }
      } catch (e: Exception) {
        call.respondHtml {
          waitDescriptionWrapper(
              "You didn't called at a good time! I don't want to give you the flag. / Tu n'as pas appelé au bon moment! Je ne veux pas te donner le drapeau.",
              needReload = true)
        }
      }
    }
  }
}
