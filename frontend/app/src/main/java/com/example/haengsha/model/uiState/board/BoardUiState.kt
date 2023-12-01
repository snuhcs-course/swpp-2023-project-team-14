package com.example.haengsha.model.uiState.board

data class BoardUiState(
    val token: String = "",
    val keyword: String = "",
    val isFestival: Int = 2,
    val startDate: String = "",
    val endDate: String = "",
    val initialState: Boolean = true
)

data class BoardPostUiState(
    val startDate: String = "",
    val endDate: String = ""
)