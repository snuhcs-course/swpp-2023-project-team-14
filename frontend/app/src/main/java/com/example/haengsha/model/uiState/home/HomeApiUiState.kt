package com.example.haengsha.model.uiState.home

import com.example.haengsha.model.network.dataModel.EventResponse

sealed interface HomeApiUiState {
    data class Success(
        val festivalResponse: List<EventResponse>?,
        val academicResponse: List<EventResponse>?
    ) : HomeApiUiState

    data object HttpError : HomeApiUiState
    data object NetworkError : HomeApiUiState
    data object Error : HomeApiUiState
    data object Loading : HomeApiUiState
}