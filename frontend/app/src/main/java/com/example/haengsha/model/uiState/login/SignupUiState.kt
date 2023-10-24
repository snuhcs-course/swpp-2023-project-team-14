package com.example.haengsha.model.uiState.login

data class SignupUiState(
    val nickname: String = "",
    val email: String = "",
    val password: String = "",
    val major: String = "",
    val grade: String = "",
    val interest: String = ""
)

data class FindPasswordUiState(
    val email: String = ""
)