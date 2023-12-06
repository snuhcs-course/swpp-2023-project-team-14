package com.example.haengsha.model.uiState.home

import com.example.haengsha.model.network.dataModel.RecommendResponse

sealed interface RecommendationApiUiState {
    data class RecommendationListResult(
        val recommendationList: List<RecommendResponse>
    ) : RecommendationApiUiState

    data object HttpError : RecommendationApiUiState
    data object NetworkError : RecommendationApiUiState
    data object Error : RecommendationApiUiState
    data object Loading : RecommendationApiUiState
}