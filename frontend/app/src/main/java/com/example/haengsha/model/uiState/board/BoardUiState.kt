package com.example.haengsha.model.uiState.board

import com.example.haengsha.model.network.apiService.Author
import com.example.haengsha.model.network.apiService.EventDurationResponse

sealed interface BoardUiState {
    data class BoardResult(
        val id: Int,
        val title: String,
        val isFestival: Boolean,
        val author: Author, // EventApiService.kt에 정의
        val eventDurations: List<EventDurationResponse>, // EventApiService.kt에 정의
        val place: String,
        val time: String?,
        val content: String,
        val image: String?,
        val likeCount: Int,
        val favoriteCount: Int
    ) : BoardUiState

    data class HttpError(val message: String) : BoardUiState
    object NetworkError : BoardUiState
    object Loading : BoardUiState
}