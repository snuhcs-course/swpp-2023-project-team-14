package com.example.haengsha.model.network.dataModel

import com.example.haengsha.model.network.apiService.Author
import com.example.haengsha.model.network.apiService.EventDurationResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoardResponse(
    val id: Int,
    val title: String,
    @SerialName("is_festival")
    val isFestival: Boolean,
    val author: Author, // EventApiService.kt에 정의
    val eventDurations: List<EventDurationResponse>, // EventApiService.kt에 정의
    val place: String,
    val time: String?,
    val content: String,
    val image: String?,
    @SerialName("like_count")
    val likeCount: Int,
    @SerialName("favorite_count")
    val favoriteCount: Int
)

/* TODO 필터 api 짜야함 */