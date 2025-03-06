package com.csgames.wait.view

import com.csgames.common.layout
import kotlinx.html.*

fun HTML.waitPageDisplay(id: String) = layout {
  div { a(href = "/wait") { +"<- Go to main page / Retour à l'accueil" } }
  h1 { +"Wait game" }

  div(classes = "response-area") {
    attributes["id"] = "response-container"
    waitDescription(
        "Hey there! Call me whenever you can! I have a flag for you! I might be busy tho. / Salut! Appelle-moi quand tu peux! J'ai un drapeau pour toi! Je pourrais être occupé cependant.")
  }

  button(classes = "pl") {
    attributes["hx-post"] = "/wait/$id"
    attributes["hx-target"] = "#response-container"
    attributes["hx-swap"] = "innerHTML"
    attributes["hx-trigger"] = "click"
    type = ButtonType.button
    +"Call / Appeler"
  }
}

fun HTML.waitDescriptionWrapper(
    text: String,
    timeInSecond: Int? = null,
    needReload: Boolean = false,
    doomMode: Boolean = false
) = body { waitDescription(text, timeInSecond, needReload, doomMode) }

fun HtmlBlockTag.waitDescription(
    text: String,
    timeInSecond: Int? = null,
    needReload: Boolean = false,
    doomMode: Boolean = false
) {
  div { +text }

  if (timeInSecond != null) {
    div {
      +"Time left (secondes): "
      div {
        attributes["id"] = "timer"
        +timeInSecond.toString()
      }
    }

    script {
      unsafe {
        +"""
    var time = $timeInSecond;
    var timer = setInterval(function() {
      if (time > 0) {
        time--;
        ${if (doomMode) "if (Math.random() < 0.01) {time++;}" else ""}
        document.getElementById('timer').innerText = time;
      }
    }, 1000);
    """
      }
    }
  }

  if (needReload) {
    script {
      unsafe {
        +"""
        setTimeout(function() {
          window.location.href = '/wait';
        }, 3000);
      """
      }
    }
  }
}
