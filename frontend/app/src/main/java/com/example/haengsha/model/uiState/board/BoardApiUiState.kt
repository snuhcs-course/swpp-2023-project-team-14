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

sealed interface BoardFavoriteUiState {
    data class BoardListResult(
        val boardList: List<BoardListResponse>
    ) : BoardFavoriteUiState

    object HttpError : BoardFavoriteUiState
    object NetworkError : BoardFavoriteUiState
    object Error : BoardFavoriteUiState
    object Loading : BoardFavoriteUiState
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

sealed interface BoardPostApiUiState {
    object Success : BoardPostApiUiState
    object HttpError : BoardPostApiUiState
    object NetworkError : BoardPostApiUiState
    object Error : BoardPostApiUiState
    object Loading : BoardPostApiUiState
}

sealed interface PatchLikeFavoriteUiState {
    data class Success(
        val likeCount: Int,
        val favoriteCount: Int,
        val isLiked: Boolean,
        val isFavorite: Boolean
    ) : PatchLikeFavoriteUiState

    object HttpError : PatchLikeFavoriteUiState
    object NetworkError : PatchLikeFavoriteUiState
    object Error : PatchLikeFavoriteUiState
    object Loading : PatchLikeFavoriteUiState
}