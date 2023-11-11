package com.example.haengsha.model.network.dataModel

import com.example.haengsha.model.network.apiService.Author
import com.example.haengsha.model.network.apiService.EventDurationResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Header
import retrofit2.http.Part

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
    val favoriteCount: Int,
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
    @SerialName("is_favorite")
    val isFavorite: Boolean
)

data class BoardPostRequest(
    val token: String,
    val image: MultipartBody.Part?,
    val title: RequestBody,
    val isFestival: RequestBody,
    val eventDurations: List<RequestBody>,
    val place: RequestBody,
    val time: RequestBody,
    val content: RequestBody
)

@Serializable
data class BoardPostResponse(
    val title: String,
    @SerialName("is_festival")
    val isFestival: Boolean?,
    val author: Author?, // EventApiService.kt에 정의
    val place: String?,
    val time: String?,
    val content: String?,
    val image: String?,
)

/* TODO 필터 api 짜야함 */