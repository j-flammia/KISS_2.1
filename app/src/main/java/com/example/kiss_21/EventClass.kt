package com.example.kiss_21

import androidx.compose.ui.graphics.Color
import java.time.LocalDateTime

// this is our base class for all events to be displayed.
// It hold all of the class attributes for events:
data class EventClass(
    val name: String,
    val color: Color,
    val start: LocalDateTime,
    val end: LocalDateTime,
    val description: String? = null
)