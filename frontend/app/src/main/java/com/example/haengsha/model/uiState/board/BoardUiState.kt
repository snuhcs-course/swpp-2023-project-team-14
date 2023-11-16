package com.example.haengsha.model.uiState.board

import com.example.haengsha.model.network.dataModel.BoardListResponse

data class BoardUiState(
    val boardList: List<BoardListResponse> = listOf(),
    val keyword: String = "",
    val isFestival: Int = 1,
    val startDate: String = "",
    val endDate: String = ""
)