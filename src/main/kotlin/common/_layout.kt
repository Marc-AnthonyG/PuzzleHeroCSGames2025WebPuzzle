package com.csgames.common

import kotlinx.html.*

fun HTML.layout(e: BODY.() -> Unit) {
  head {
    link(rel = "stylesheet", href = "https://cdn.simplecss.org/simple.min.css")

    val htmx = { e: String -> "webjars/htmx.org/2.0.4/$e" }
    script(src = htmx("dist/htmx.min.js")) {}
  }

  body { e() }
}
