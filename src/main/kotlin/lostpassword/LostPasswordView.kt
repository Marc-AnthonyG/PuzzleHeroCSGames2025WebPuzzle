package com.csgames.lostpassword

import com.csgames.common.layout
import kotlinx.css.*
import kotlinx.css.properties.LineHeight
import kotlinx.html.*

fun getBoxCss(): String =
    CssBuilder()
        .apply {
          width = 40.px
          height = 40.px
          textAlign = TextAlign.center
          fontSize = 20.px
          border = Border(2.px, BorderStyle.solid, Color("#333"))
          borderRadius = 5.px
          margin = Margin(0.px, 3.px)
          display = Display.inlineBlock
          lineHeight = LineHeight("40px")
        }
        .toString()

fun HTML.lostPasswordGamePage() = layout {
  div { a(href = "/") { +"<- Go to main page / Retour à l'accueil" } }
  h1 { +"Lost password game - Jeu du mot de passe perdu" }

  div(classes = "game-container") {
    attributes["style"] =
        "display: flex; flex-direction: column; align-items: center; margin-top: 20px;"

    div(classes = "feedback-area") {
      id = "feedback-container"
      attributes["style"] =
          "margin-bottom: 20px; min-height: 60px; text-align: center; font-size: 18px;"
      div(classes = "intro-message") {
        +"Enter a guess to start playing / Entrez un essai pour commencer à jouer"
      }
    }

    form {
      id = "guess-form"
      attributes["hx-post"] = "/lostpassword/guess"
      attributes["hx-target"] = "#feedback-container"
      attributes["hx-swap"] = "innerHTML"
      attributes["style"] = "display: flex; flex-direction: column; align-items: center;"

      div(classes = "char-inputs-container") {
        attributes["style"] = "display: flex; gap: 8px; margin-bottom: 15px;"

        for (i in TARGET.indices) {
          input(type = InputType.text) {
            name = "char-$i"
            classes = setOf("char-input")
            attributes["maxlength"] = "1"
            required = true
            style = getBoxCss()
            attributes["autocomplete"] = "off"
            attributes["data-index"] = "$i"
          }
        }
      }

      button(type = ButtonType.submit) { +"Submit / Envoyer" }
    }
  }

  script {
    unsafe {
      +"""
      document.addEventListener('DOMContentLoaded', function() {
        // Set up input behavior
        const charInputs = document.querySelectorAll('.char-input');
        
        charInputs.forEach(function(input) {
          // Move to next input when a character is entered
          input.addEventListener('input', function() {
            if (this.value.length === 1) {
              const nextIndex = parseInt(this.getAttribute('data-index')) + 1;
              const nextInput = document.querySelector('.char-input[data-index="' + nextIndex + '"]');
              if (nextInput) {
                nextInput.focus();
              }
            }
          });
          
          // Move to previous input on backspace when empty
          input.addEventListener('keydown', function(e) {
            if (e.key === 'Backspace' && this.value.length === 0) {
              const prevIndex = parseInt(this.getAttribute('data-index')) - 1;
              const prevInput = document.querySelector('.char-input[data-index="' + prevIndex + '"]');
              if (prevInput) {
                prevInput.focus();
              }
            }
          });
        });
        
        // Focus the first input on page load
        if (charInputs.length > 0) {
          charInputs[0].focus();
        }
      });
      """
    }
  }
}

fun HTML.wrappedGuessFeedback(guess: String, feedback: List<FeedbackState>, isWon: Boolean) {
  layout { guessFeedback(guess, feedback, isWon) }
}

fun HtmlBlockTag.guessFeedback(guess: String, feedback: List<FeedbackState>, isWon: Boolean) {
  div {
    style =
        CssBuilder()
            .apply {
              display = Display.flex
              flexDirection = FlexDirection.row
              gap = 8.px
            }
            .toString()

    val normalizedGuess = guess.padEnd(TARGET.length, ' ').substring(0, TARGET.length)

    for (i in TARGET.indices) {
      val char = normalizedGuess[i]
      val feedbackState = feedback[i]

      val cellClass =
          when (isWon) {
            true -> "correct"
            false ->
                when (feedbackState) {
                  FeedbackState.CORRECT -> "correct"
                  FeedbackState.PRESENT -> "present"
                  FeedbackState.ABSENT -> "absent"
                }
          }
      span(classes = cellClass) {
        style = getBoxCss()
        +char.toString()
      }
      style {
        unsafe {
          +"""
            .correct {
                background-color: #4caf50;
            }
            .present {
                background-color: #ffeb3b;
            }
            .absent {
                background-color: #9e9e9e;
            }
            """
        }
      }
    }
  }

  if (isWon) {
    div(classes = "win-message") { +"Congratulations! You've guessed the UUID correctly!" }
    div(classes = "flag") { +"Here's your flag: $FLAG" }
  }
}
