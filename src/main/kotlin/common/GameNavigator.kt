package com.csgames.common

import kotlinx.html.HTML
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.h1

fun HTML.navPage() = layout {
  h1 { +"Game navigator / Navigateur de jeux" }

  div { a(href = "/wait") { +"Go to 'Wait' game / Aller au jeu 'Wait'" } }
  div { a(href = "/buttons") { +"Go to buttons game / Aller au jeu 'Buttons'" } }
  div { a(href = "/snake") { +"Go to snake game / Aller au jeu du serpent" } }
}
