package com.example.haengsha.model.uiState.login

sealed interface LoginUiState {
    // LoginSuccess는 받는 인자가 달라서 분리함
    data class LoginSuccess(val token: String, val role: String, val message: String) : LoginUiState
    data class Success(val message: String) : LoginUiState
    data class HttpError(val message: String) : LoginUiState
    object NetworkError : LoginUiState
    object Loading : LoginUiState
}