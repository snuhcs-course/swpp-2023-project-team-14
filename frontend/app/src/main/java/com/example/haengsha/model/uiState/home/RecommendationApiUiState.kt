package com.example.haengsha.model.uiState.home

import com.example.haengsha.model.network.dataModel.RecommendResponse

sealed interface RecommendationApiUiState {
    data class RecommendationListResult(
        val recommendationList: List<RecommendResponse>
    ) : RecommendationApiUiState

    object HttpError : RecommendationApiUiState
    object NetworkError : RecommendationApiUiState
    object Error : RecommendationApiUiState
    object Loading : RecommendationApiUiState
}