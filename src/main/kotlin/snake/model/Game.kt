package com.csgames.snake.model

import kotlin.time.Duration.Companion.hours
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class Point(val x: Int, val y: Int)

enum class Direction {
  UP,
  DOWN,
  LEFT,
  RIGHT
}

data class GameState(
    val id: String,
    val snake: List<Point>,
    val direction: Direction,
    val foods: List<Point>,
    val gameOver: Boolean = false,
    val won: Boolean = false,
    var expiredAt: Instant = Clock.System.now().plus(1.hours),
    val numberOfTurns: Int = 0
)
