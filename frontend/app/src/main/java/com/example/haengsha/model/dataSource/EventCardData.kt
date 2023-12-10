package com.example.haengsha.model.dataSource

import java.time.LocalDate

data class EventCardData(
    val id: Int,
    val organizer: String,
    val eventTitle: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val likes: Int,
    val favorites: Int,
    val eventType: String,
    val place: String = "",
    val time: String = "",
    val image: String = ""
)

data class TabItem(
    val title: String,
    val eventCards: List<EventCardData>
)