package com.example.haengsha.model.uiState.recommendation

import com.example.haengsha.ui.screens.home.EventCardData

sealed interface RecommendationUiState {
    data class RecommendationListResult(
        val recommendationList: List<EventCardData>?
    ) : RecommendationUiState

    object HttpError : RecommendationUiState
    object NetworkError : RecommendationUiState
    object Error : RecommendationUiState
    object Loading : RecommendationUiState
}