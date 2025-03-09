package com.csgames.snake

import com.csgames.snake.model.Direction
import com.csgames.snake.model.GameState
import com.csgames.snake.model.Point
import com.csgames.snake.view.snakeGamePage
import com.csgames.snake.view.wrappedGameBoard
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import java.util.*

const val GRID_SIZE = 50
const val FLAG = "CSGAMES-IMPOSSIBLE-YOU-MUST-BE-CHEATING"

fun Application.snakeGameRoute(gameRepository: GameRepository) {
  routing {
    get("/snake") {
      val gameId = UUID.randomUUID().toString()
      val initialGame =
          GameState(
              id = gameId,
              snake = listOf(Point(GRID_SIZE / 2, GRID_SIZE / 2)),
              direction = Direction.RIGHT,
              foods =
                  listOf(generateFood(listOf(Point(GRID_SIZE / 2, GRID_SIZE / 2)), emptyList())),
              gameOver = false)
      gameRepository.saveGame(initialGame)

      call.respondHtml { snakeGamePage(initialGame) }
    }

    post("/snake/{gameId}/move") {
      val gameId = call.parameters["gameId"] ?: throw BadRequestException("Game ID missing")
      val direction =
          try {
            Direction.valueOf(call.receiveParameters()["direction"] ?: "RIGHT")
          } catch (e: IllegalArgumentException) {
            throw BadRequestException("Invalid direction")
          }

      val currentGame = gameRepository.getGame(gameId) ?: throw NotFoundException("Game not found")

      if (currentGame.gameOver) {
        call.respondHtml { wrappedGameBoard(currentGame) }
        return@post
      }

      val newDirection =
          if (isOppositeDirection(currentGame.direction, direction)) {
            currentGame.direction
          } else {
            direction
          }

      val head = currentGame.snake.first()
      val newHead =
          when (newDirection) {
            Direction.UP -> Point(head.x, (head.y - 1 + GRID_SIZE) % GRID_SIZE)
            Direction.DOWN -> Point(head.x, (head.y + 1) % GRID_SIZE)
            Direction.LEFT -> Point((head.x - 1 + GRID_SIZE) % GRID_SIZE, head.y)
            Direction.RIGHT -> Point((head.x + 1) % GRID_SIZE, head.y)
          }

      val selfCollision = currentGame.snake.contains(newHead)

      val newSnake =
          if (selfCollision) {
            currentGame.snake
          } else {
            val snake = mutableListOf(newHead)
            snake.addAll(currentGame.snake)

            if (!currentGame.foods.contains(newHead)) {
              snake.removeAt(snake.lastIndex)
            }

            snake
          }

      val newFoods = currentGame.foods.filter { it != newHead }.toMutableList()

      if (currentGame.foods.isEmpty() ||
          currentGame.numberOfTurns % 25 == 0 && currentGame.foods.size < 5) {
        newFoods += generateFood(newSnake, currentGame.foods)
      }

      val gameWon = newSnake.size == GRID_SIZE * GRID_SIZE

      val newGameState =
          currentGame.copy(
              snake = newSnake,
              direction = newDirection,
              foods = newFoods,
              gameOver = selfCollision || gameWon,
              won = gameWon,
              numberOfTurns = currentGame.numberOfTurns + 1)

      gameRepository.saveGame(newGameState)

      call.respondHtml { wrappedGameBoard(newGameState) }
    }
  }
}

fun generateFood(snake: List<Point>, foods: List<Point>): Point {
  val allPositions = mutableListOf<Point>()
  for (x in 0 until GRID_SIZE) {
    for (y in 0 until GRID_SIZE) {
      val point = Point(x, y)
      if (!snake.contains(point) && !foods.contains(point)) {
        allPositions.add(point)
      }
    }
  }

  return if (allPositions.isEmpty()) Point(0, 0) else allPositions.random()
}

fun isOppositeDirection(current: Direction, new: Direction): Boolean {
  return (current == Direction.UP && new == Direction.DOWN) ||
      (current == Direction.DOWN && new == Direction.UP) ||
      (current == Direction.LEFT && new == Direction.RIGHT) ||
      (current == Direction.RIGHT && new == Direction.LEFT)
}
