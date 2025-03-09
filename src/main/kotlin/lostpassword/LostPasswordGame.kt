package com.csgames.lostpassword

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlin.random.Random

const val FLAG = "CSGAMES-YOU-CRACKED-IT-WELL-PLAYED"

const val TARGET = "UaYw9fiVagWW*mHVk@5h3xE\$&Gy6@^YJ"

enum class FeedbackState {
  CORRECT,
  PRESENT,
  ABSENT
}

fun Application.lostPasswordGameRoute() {
  routing {
    get("/lostpassword") { call.respondHtml { lostPasswordGamePage() } }

    post("/lostpassword/guess") {
      val formParameters = call.receiveParameters()
      val guess =
          TARGET.indices.map { index -> formParameters["char-$index"] ?: "" }.joinToString("")

      val feedback = processFeedbackWithRandomness(guess)

      val isCorrect = guess == TARGET

      call.respondHtml { wrappedGuessFeedback(guess, feedback, isCorrect) }
    }
  }
}

fun processFeedbackWithRandomness(guess: String): List<FeedbackState> {
  val feedback = mutableListOf<FeedbackState>()

  for (i in guess.indices) {
    val charFeedback = checkRightFeedback(guess[i], TARGET[i])

    when (Random.nextInt(0, 100) < 65) {
      true -> feedback.add(charFeedback)
      false -> feedback.add(choiceRandomOtherFeedbackState(charFeedback))
    }
  }

  return feedback
}

fun checkRightFeedback(guessChar: Char, targetChar: Char): FeedbackState {
  return when {
    guessChar == targetChar -> FeedbackState.CORRECT
    TARGET.contains(guessChar) -> FeedbackState.PRESENT
    else -> FeedbackState.ABSENT
  }
}

fun choiceRandomOtherFeedbackState(correctFeedbackState: FeedbackState): FeedbackState {
  val roll = Random.nextBoolean()
  return when (correctFeedbackState) {
    FeedbackState.CORRECT ->
        when (roll) {
          true -> return FeedbackState.PRESENT
          false -> return FeedbackState.ABSENT
        }
    FeedbackState.PRESENT ->
        return when (roll) {
          true -> FeedbackState.CORRECT
          false -> FeedbackState.ABSENT
        }
    FeedbackState.ABSENT ->
        when (roll) {
          true -> FeedbackState.CORRECT
          false -> FeedbackState.PRESENT
        }
  }
}
