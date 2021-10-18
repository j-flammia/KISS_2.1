package com.example.kiss_21

import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.kiss_21.DateClass

class ProjectClass(name: String) {

    var done = false

    var focus = if (done) BorderStroke(2.dp, Color.Gray)
                else BorderStroke(2.dp, Color.Yellow)

    var projectName: String = name
    var projectDate: DateClass = DateClass()
    var address: String = "123 Somewhere St,\n Acity, Astate 23001"
    var customer: String = "Bob Dylan"
    var phone: String = "804-123-4567"
    var notes: String = "There are no notes for this project."
    var materials: List<String> = listOf("No materials.")
}