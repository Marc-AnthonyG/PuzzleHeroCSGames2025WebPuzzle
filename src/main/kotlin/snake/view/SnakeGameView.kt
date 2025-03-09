package com.csgames.snake.view

import com.csgames.common.layout
import com.csgames.snake.FLAG
import com.csgames.snake.GRID_SIZE
import com.csgames.snake.model.GameState
import com.csgames.snake.model.Point
import kotlinx.html.*

fun HTML.snakeGamePage(gameState: GameState) = layout {
  div { a(href = "/") { +"<- Go to main page / Retour à l'accueil" } }
  h1 { +"Snake Game" }

  div(classes = "game-container") {
    div {
      id = "game-board"
      gameBoard(gameState)
    }

    div(classes = "controls") {
      button(classes = "control-btn") {
        id = "up-btn"
        attributes["hx-post"] = "/snake/${gameState.id}/move"
        attributes["hx-target"] = "#game-board"
        attributes["hx-swap"] = "innerHTML"
        attributes["hx-vals"] = """{"direction": "UP"}"""
        +"↑"
      }
      div {
        button(classes = "control-btn") {
          id = "left-btn"
          attributes["hx-post"] = "/snake/${gameState.id}/move"
          attributes["hx-target"] = "#game-board"
          attributes["hx-swap"] = "innerHTML"
          attributes["hx-vals"] = """{"direction": "LEFT"}"""
          +"←"
        }
        button(classes = "control-btn") {
          id = "down-btn"
          attributes["hx-post"] = "/snake/${gameState.id}/move"
          attributes["hx-target"] = "#game-board"
          attributes["hx-swap"] = "innerHTML"
          attributes["hx-vals"] = """{"direction": "DOWN"}"""
          +"↓"
        }
        button(classes = "control-btn") {
          id = "right-btn"
          attributes["hx-post"] = "/snake/${gameState.id}/move"
          attributes["hx-target"] = "#game-board"
          attributes["hx-swap"] = "innerHTML"
          attributes["hx-vals"] = """{"direction": "RIGHT"}"""
          +"→"
        }
      }
    }
  }

  script {
    unsafe {
      +"""
            document.addEventListener('keydown', function(event) {
                switch(event.key) {
                    case 'ArrowUp':
                        document.getElementById('up-btn').click();
                        break;
                    case 'ArrowDown':
                        document.getElementById('down-btn').click();
                        break;
                    case 'ArrowLeft':
                        document.getElementById('left-btn').click();
                        break;
                    case 'ArrowRight':
                        document.getElementById('right-btn').click();
                        break;
                }
            });
            """
    }
  }

  style {
    unsafe {
      +"""
            .game-container {
                display: flex;
                flex-direction: column;
                align-items: center;
                margin: 20px 0;
            }
            .game-board {
                display: grid;
                grid-template-columns: repeat(${GRID_SIZE}, 20px);
                grid-template-rows: repeat(${GRID_SIZE}, 20px);
                gap: 1px;
                background-color: #333;
                padding: 10px;
                border-radius: 5px;
            }
            .cell {
                width: 20px;
                height: 20px;
                background-color: #222;
                border-radius: 2px;
            }
            .snake {
                background-color: #4CAF50;
            }
            .food {
                background-color: #F44336;
                border-radius: 50%;
            }
            .controls {
                margin-top: 20px;
                display: flex;
                flex-direction: column;
                align-items: center;
            }
            .control-btn {
                width: 40px;
                height: 40px;
                margin: 5px;
                font-size: 20px;
                cursor: pointer;
                background-color: #4CAF50;
                color: white;
                border: none;
                border-radius: 5px;
            }
            """
    }
  }
}

fun HTML.wrappedGameBoard(gameState: GameState) = layout { gameBoard(gameState) }

fun HtmlBlockTag.gameBoard(gameState: GameState) {
  if (gameState.gameOver) {
    if (gameState.won) {
      div { +"Congratulations! You've won!" }
      div { +"Here's your flag: $FLAG" }
    } else {
      div { +"Game Over! Try again." }
      button {
        attributes["onclick"] = "window.location.href='/snake'"
        +"Restart"
      }
    }
  }

  div(classes = "game-board") {
    for (y in 0 until GRID_SIZE) {
      for (x in 0 until GRID_SIZE) {
        val point = Point(x, y)
        val cellClass =
            when {
              gameState.snake.contains(point) -> "cell snake"
              gameState.foods.contains(point) -> "cell food"
              else -> "cell"
            }
        div(classes = cellClass) {}
      }
    }
  }
}
