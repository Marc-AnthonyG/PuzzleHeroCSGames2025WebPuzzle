package com.csgames.buttons

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File
import java.util.*

fun Application.buttonGameRoute() {
  routing {
    get("/buttons") { call.respondFile(File("src/main/resources/static/buttons.html")) }

    post("/buttons/{buttonsId}") {
      val buttonId = call.parameters["buttonsId"]?.toIntOrNull()

      if (buttonId == null) {
        call.respondText("Invalid button ID")
        return@post
      }

      if (buttonId == 68132) {
        call.respondText("CSGAMES-YOU-ARE-THE-LUCKIEST")
        return@post
      }

      val random = Random(buttonId.toLong())

      val responses =
          listOf(
              { "Here is your flag: ${UUID.randomUUID()}" },
              { "You really think the flag would be here? ${UUID.randomUUID()}" },
              { "The answer lies within the depths of randomness: ${UUID.randomUUID()}" },
              { "This button reminds me of CSGAMES, but not in a useful way." },
              { "Maybe you should try pressing button ${random.nextInt(500000)} instead." },
              { "Hahaha, you really thought it was that easy?" },
              { "Decrypt this: ${random.nextInt()}-${random.nextInt()}-${random.nextInt()}" },
              { "404: Flag Not Found." },
              {
                "Did you know? CSGAMES is an acronym for 'Can’t Stop Guessing A Million Empty Spots'."
              },
              { "This button is now cursed. Try another one." },
              { "A secret agent took the flag. Try again later." },
              { "The flag was here, but I accidentally deleted it. My bad." },
              { "Error 418: I'm a teapot. Try again?" },
              { "Congratulations! You won absolutely nothing." },
              { "I think the flag is under your keyboard. Check?" },
              { "This is just a button. You expected a flag? LOL." },
              { "If you press 42 buttons in a row, something interesting might happen. Or not." },
              { "You just wasted a click. How does that feel?" },
              { "Try pressing all 250,000 buttons. I'm sure you'll find something." },
              { "Knock knock. Who’s there? Not the flag." },
              { "If you enter the Konami code, maybe something will happen." },
              { "Try shaking your screen. It won’t help, but it’ll be funny to watch." },
              { "This button is reserved for VIPs only. Are you VIP? No? Then move along." },
              { "The flag is not in another castle. It’s just... not here." },
              { "The NSA intercepted your request. Please try again in 10 years." },
              { "Would you like a hint? Just kidding, no hints for you." },
              { "Processing... Processing... Oh wait, no flag here." },
              { "I hid the flag in a place where you'll never find it." },
              { "This button has a 0.0004% chance of being the right one. Try again!" },
              { "Pressing this button did nothing. Try another one." },
              { "If you read this message backward, you will still be lost." },
              { "You've unlocked a secret achievement: 'Clicked the Wrong Button'." },
              { "Try again, but this time, with FEELING!" },
              { "A quantum fluctuation moved the flag to another button. Sorry!" },
              { "Button pressed. Hope restored. Flag not found." },
              { "I'm not saying the flag is at (0,0), but I'm not NOT saying that either." },
              { "This button self-destructs in 3... 2... 1... Just kidding." },
              { "You are currently ranked #1 in 'Most Wrong Button Presses'!" },
              { "If you close your eyes and wish really hard, the flag might appear. Or not." },
              { "If you whisper 'CSGAMES' three times, maybe the flag will show up." },
              { "What if the real flag was the friends we made along the way?" },
              { "You found... a completely useless response! Congrats!" },
              { "The flag is on vacation. Try again next week." },
              { "Try pressing the button with your nose. Just for fun." },
              { "If you were a cat, you would have nine more tries. Keep going!" },
              { "What if I told you... the flag was never real?" },
              { "Imagine the flag. Now press another button." },
              { "You have unlocked a hidden feature: disappointment." },
              { "This is not the button you're looking for." },
              { "The flag is stored in /dev/null. Try retrieving it from there." },
              { "Every time you press a button, a computer science student cries." },
              { "Did you try turning your computer off and on again?" },
              { "If you guess a random number between 1 and 500000, you might win. Maybe." },
              { "They say the flag was last seen on button #${random.nextInt(500000)}." },
              { "You have been granted an honorary PhD in Button Pressing!" },
              { "Pressing this button has increased global warming by 0.0001°C. Congrats!" },
              { "Your click has been recorded for future disappointment analysis." },
              { "To continue, please insert 25 cents." },
              { "The flag was stolen by pirates. Arrr, matey!" },
              { "A cat walked over the keyboard and deleted the flag. Sorry." },
              { "No flag. But here’s a virtual cookie: 🍪" },
              { "Your internet speed is too slow. The flag has timed out." },
              { "Loading flag... Just kidding, still nothing." },
              { "Did you know? Clicking wrong buttons is good for your reflexes!" },
              { "If you press 10 more buttons, something magical might happen! (It won't.)" },
              { "This button redirects you to... disappointment." },
              { "One of these buttons has the flag. But not this one." },
              { "Your button karma is too low. Try again later." },
              { "CSGAMES is an anagram for 'Games CS'. That doesn’t help, but it’s fun!" },
              { "Wait... I think I see something... Nope, false alarm." },
              { "Pressing this button grants you 1 experience point in 'Random Clicking'." },
              { "Guess what? You clicked the wrong button again!" },
              { "A wizard has hidden the flag. Wizards are tricky like that." },
              { "You are now part of an exclusive club: 'People Who Clicked This Button'." },
              { "Have you tried asking the button politely?" },
              { "The flag was right here, but then I blinked and it was gone." },
              { "If this button had a flag, it would be great. But it doesn’t." },
              { "Somewhere, in a parallel universe, this was the right button." },
              { "This button contains a flag... of another country." },
              { "If you press enough buttons, you might trigger a secret boss fight." },
              { "The flag was replaced with a potato. Hope you like potatoes." },
              { "A butterfly flapped its wings, and the flag disappeared. Chaos theory." },
              {
                "This button has been pressed ${random.nextInt(1000)} times before. Still no flag."
              },
              { "If I told you the next button was the correct one, would you believe me?" },
              { "The flag is not in this reality. Try accessing a parallel dimension." },
              { "This button is only for decoration." },
              { "Remember: The real flag was inside you all along." },
              { "Searching for flag... 0% complete... error." },
              { "No flag, but hey, at least your clicking skills are improving!" },
              { "You pressed a button. That’s something, at least." },
              { "Isn't it fun clicking buttons? No? Well, too bad." })

      val response = responses[random.nextInt(responses.size)]()
      call.respondText(response)
    }
  }
}
