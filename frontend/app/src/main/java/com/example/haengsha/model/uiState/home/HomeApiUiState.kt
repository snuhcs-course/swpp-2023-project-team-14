package com.example.haengsha.model.uiState.home

sealed interface HomeApiUiState {
    object Success : HomeApiUiState
    object HttpError : HomeApiUiState
    object NetworkError : HomeApiUiState
    object Error : HomeApiUiState
    object Loading : HomeApiUiState
}