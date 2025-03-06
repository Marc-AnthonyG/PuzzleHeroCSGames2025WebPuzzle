package com.csgames.common

import kotlinx.html.HTML
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.h1

fun HTML.navPage() = layout {
  h1 { +"Game navigateur" }

  div { a(href = "/wait") { +"Go to wait game" } }
}
