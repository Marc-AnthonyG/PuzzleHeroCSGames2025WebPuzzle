package com.csgames.snake

import com.csgames.snake.model.GameState
import kotlinx.datetime.Clock
import java.util.concurrent.ConcurrentHashMap
import kotlin.time.Duration.Companion.hours

class GameRepository {
  private val games = ConcurrentHashMap<String, GameState>()

  fun saveGame(gameState: GameState) {
    games[gameState.id] = gameState
  }

  fun getGame(id: String): GameState? {
    clearExpiredGame()
    val gameState = games[id]
    gameState?.let { it.expiredAt = Clock.System.now().plus(1.hours) }
    return gameState
  }

  private fun clearExpiredGame() {
    games.entries.removeIf { it.value.expiredAt < Clock.System.now() }
  }
}
