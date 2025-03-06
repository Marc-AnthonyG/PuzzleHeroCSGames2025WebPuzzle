package com.csgames.wait.infra

import com.csgames.wait.model.Player
import kotlin.time.Duration.Companion.minutes
import kotlinx.datetime.Clock

class PlayerRepository {
  private val players = mutableListOf<Player>()

  fun addPlayer(player: Player) {
    players.add(player)
  }

  fun updatePlayer(player: Player) {
    val index = players.indexOfFirst { it.id == player.id }
    if (index != -1) {
      players[index] = player
    }
  }

  fun removePlayer(id: String): Boolean {
    return players.removeIf { it.id == id }
  }

  fun findById(playerId: String): Player? {
    players.removeIf { it.time < Clock.System.now().minus(1.minutes) }
    return players.find { it.id == playerId }
  }
}
