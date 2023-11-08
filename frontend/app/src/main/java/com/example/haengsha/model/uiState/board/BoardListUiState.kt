package com.example.haengsha.model.uiState.board

import com.example.haengsha.model.network.dataModel.BoardDetailResponse
import com.example.haengsha.model.network.dataModel.BoardListResponse

sealed interface BoardListUiState {
    data class BoardListResult(
        val boardList: List<BoardListResponse>
    ) : BoardListUiState

    object HttpError : BoardListUiState
    object NetworkError : BoardListUiState
    object Error : BoardListUiState
    object Loading : BoardListUiState
}

sealed interface BoardDetailUiState {
    data class BoardDetailResult(
        val boardDetail: BoardDetailResponse
    ) : BoardDetailUiState

    object HttpError : BoardDetailUiState
    object NetworkError : BoardDetailUiState
    object Error : BoardDetailUiState
    object Loading : BoardDetailUiState
}