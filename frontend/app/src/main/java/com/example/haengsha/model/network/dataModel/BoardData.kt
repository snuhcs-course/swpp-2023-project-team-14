package com.example.haengsha.model.network.dataModel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okhttp3.MultipartBody
import okhttp3.RequestBody

@Serializable
data class BoardListResponse(
    val id: Int,
    val title: String,
    @SerialName("is_festival")
    val isFestival: Boolean?,
    val author: Author?, // EventApiService.kt에 정의
    @SerialName("event_durations")
    val eventDurations: List<EventDurationResponse>, // EventApiService.kt에 정의
    val place: String?,
    val time: String?,
    val content: String?,
    val image: String?,
    @SerialName("like_count")
    val likeCount: Int,
    @SerialName("favorite_count")
    val favoriteCount: Int
)

@Serializable
data class BoardDetailResponse(
    val id: Int,
    val title: String,
    @SerialName("is_festival")
    val isFestival: Boolean?,
    val author: Author?, // EventApiService.kt에 정의
    @SerialName("event_durations")
    val eventDurations: List<EventDurationResponse>, // EventApiService.kt에 정의
    val place: String?,
    val time: String?,
    val content: String?,
    val image: String?,
    @SerialName("like_count")
    val likeCount: Int,
    @SerialName("favorite_count")
    val favoriteCount: Int,
    @SerialName("is_liked")
    val isLiked: Boolean,
    @SerialName("is_favorited")
    val isFavorite: Boolean
)

@Serializable
data class PatchLikeFavoriteResponse(
    val id: Int,
    val title: String,
    @SerialName("is_festival")
    val isFestival: Boolean?,
    val author: Author?, // EventApiService.kt에 정의
    @SerialName("event_durations")
    val eventDurations: List<EventDurationResponse>, // EventApiService.kt에 정의
    val place: String?,
    val time: String?,
    val content: String?,
    val image: String?,
    @SerialName("like_count")
    val likeCount: Int,
    @SerialName("favorite_count")
    val favoriteCount: Int,
    @SerialName("is_liked")
    val isLiked: Boolean,
    @SerialName("is_favorited")
    val isFavorite: Boolean
)

data class BoardPostRequest(
    val token: String,
    val image: MultipartBody.Part?,
    val title: RequestBody,
    val isFestival: RequestBody,
    val eventDurations: RequestBody,
    val place: RequestBody,
    val time: RequestBody,
    val content: RequestBody
)

@Serializable
data class BoardPostResponse(
    val id: Int?,
    val title: String?,
    @SerialName("is_festival")
    val isFestival: Boolean?,
    val author: Author?, // EventApiService.kt에 정의
    val place: String?,
    val time: String?,
    val content: String?,
    val image: String?,
    @SerialName("event_durations")
    val eventDurations: ArrayList<EventDurationResponse>,
    @SerialName("like_count")
    val likeCount: Int?,
    @SerialName("favorite_count")
    val favoriteCount: Int?,
)

data class SearchRequest(
    val token: String,
    val keyword: String,
    val isFestival: Int,
    val startDate: String,
    val endDate: String
)