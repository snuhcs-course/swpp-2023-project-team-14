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

sealed interface BoardPostUiState {
    object Success : BoardPostUiState
    object HttpError : BoardPostUiState
    object NetworkError : BoardPostUiState
    object Error : BoardPostUiState
    object Loading : BoardPostUiState
}

sealed interface PostLikeFavoriteUiState {
    data class Success(
        val likeCount: Int,
        val favoriteCount: Int,
        val isLiked: Boolean,
        val isFavorite: Boolean
    ) : PostLikeFavoriteUiState

    object HttpError : PostLikeFavoriteUiState
    object NetworkError : PostLikeFavoriteUiState
    object Error : PostLikeFavoriteUiState
    object Loading : PostLikeFavoriteUiState
}