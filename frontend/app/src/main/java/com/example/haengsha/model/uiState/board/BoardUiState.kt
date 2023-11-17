package com.example.haengsha.model.uiState.board

import com.example.haengsha.model.network.dataModel.BoardListResponse

data class BoardUiState(
    val token: String = "",
    val boardList: List<BoardListResponse> = listOf(),
    val keyword: String = "",
    val isFestival: Int = 2,
    val startDate: String = "",
    val endDate: String = "",
    val initialState: Boolean = true
)