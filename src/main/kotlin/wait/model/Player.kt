package com.csgames.wait.model

import kotlin.time.Duration.Companion.hours
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

enum class Stage {
  STAGE_ONE,
  STAGE_TWO,
  STAGE_THREE,
  STAGE_FOUR,
}

@Serializable
data class Player(val id: String, val stage: Stage, val time: Instant) {
  @OptIn(ExperimentalUuidApi::class)
  constructor() : this(Uuid.random().toString(), Stage.STAGE_ONE, Clock.System.now().plus(1.hours))
}
