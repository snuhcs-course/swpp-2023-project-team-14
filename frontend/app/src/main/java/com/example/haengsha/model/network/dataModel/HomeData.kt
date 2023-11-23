package com.example.haengsha.model.network.dataModel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventResponse(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("is_festival") val isFestival: Boolean,
    @SerialName("author") val author: Author,
    @SerialName("event_durations") val eventDurations: List<EventDurationResponse>,
    @SerialName("place") val place: String,
    @SerialName("time") val time: String?,
    @SerialName("content") val content: String,
    @SerialName("image") val image: String?,
    @SerialName("like_count") val likeCount: Int,
    @SerialName("favorite_count") val favoriteCount: Int,
)

@Serializable
data class Author(
    @SerialName("nickname") val nickname: String
)

@Serializable
data class EventDurationResponse(
    @SerialName("event_day") val eventDay: String
)

@Serializable
data class RecommendResponse(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("is_festival") val isFestival: Boolean,
    @SerialName("author") val author: Author,
    @SerialName("event_durations") val eventDurations: List<EventDurationResponse>,
    @SerialName("place") val place: String,
    @SerialName("time") val time: String?,
    @SerialName("content") val content: String,
    @SerialName("image") val image: String?,
    @SerialName("like_count") val likeCount: Int,
    @SerialName("favorite_count") val favoriteCount: Int,
    @SerialName("score") val score: Int,
)
