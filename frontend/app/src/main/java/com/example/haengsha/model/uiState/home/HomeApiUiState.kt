package com.example.haengsha.model.uiState.home

sealed interface HomeApiUiState {
    data object Success : HomeApiUiState
    data object HttpError : HomeApiUiState
    data object NetworkError : HomeApiUiState
    data object Error : HomeApiUiState
    data object Loading : HomeApiUiState
}