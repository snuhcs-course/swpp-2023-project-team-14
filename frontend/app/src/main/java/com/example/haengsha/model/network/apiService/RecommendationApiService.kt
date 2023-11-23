package com.example.haengsha.model.network.apiService

import com.example.haengsha.ui.screens.home.EventCardData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import java.time.LocalDate
import java.time.format.DateTimeFormatter

interface RecommendationApiService {
    @GET("/api/post/recommend")
    suspend fun getRecommendationList(
        @Header("Authorization") token: String
    ): List<RecommendResponse>
}

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
