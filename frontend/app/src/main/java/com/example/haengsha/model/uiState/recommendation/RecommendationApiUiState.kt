package com.example.haengsha.model.uiState.recommendation

import com.example.haengsha.model.network.apiService.RecommendResponse

sealed interface RecommendationApiUiState {
    data class RecommendationListResult(
        val recommendationList: List<RecommendResponse>
    ) : RecommendationApiUiState

    object HttpError : RecommendationApiUiState
    object NetworkError : RecommendationApiUiState
    object Error : RecommendationApiUiState
    object Loading : RecommendationApiUiState
}