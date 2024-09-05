package com.example.presentation.model

import java.time.OffsetDateTime

data class Dates(
    val takeoff: OffsetDateTime? = null,
    val comeback: OffsetDateTime? = null
)
