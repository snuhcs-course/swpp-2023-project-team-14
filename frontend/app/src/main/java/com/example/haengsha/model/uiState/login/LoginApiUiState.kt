package com.example.haengsha.model.uiState.login

// TODO 쓸모없는 response 받지 말기
sealed interface LoginApiUiState {
    // LoginSuccess는 받는 인자가 달라서 분리함
    data class LoginSuccess(
        val token: String,
        val nickname: String,
        val role: String,
        val message: String
    ) : LoginApiUiState

    data class Success(val message: String) : LoginApiUiState
    data class HttpError(val message: String) : LoginApiUiState
    data object NetworkError : LoginApiUiState
    data object Loading : LoginApiUiState
}