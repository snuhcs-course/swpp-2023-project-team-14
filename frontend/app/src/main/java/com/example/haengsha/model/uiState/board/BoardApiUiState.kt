package com.example.haengsha.model.uiState.board

import com.example.haengsha.model.network.dataModel.BoardDetailResponse
import com.example.haengsha.model.network.dataModel.BoardListResponse

sealed interface BoardListUiState {
    data class BoardListResult(
        val boardList: List<BoardListResponse>
    ) : BoardListUiState

    data object HttpError : BoardListUiState
    data object NetworkError : BoardListUiState
    data object Error : BoardListUiState
    data object Loading : BoardListUiState
    data object Default : BoardListUiState
}

sealed interface BoardFavoriteUiState {
    data class BoardListResult(
        val boardList: List<BoardListResponse>
    ) : BoardFavoriteUiState

    data object HttpError : BoardFavoriteUiState
    data object NetworkError : BoardFavoriteUiState
    data object Error : BoardFavoriteUiState
    data object Loading : BoardFavoriteUiState
}

sealed interface BoardDetailUiState {
    data class BoardDetailResult(
        val boardDetail: BoardDetailResponse
    ) : BoardDetailUiState

    data object HttpError : BoardDetailUiState
    data object NetworkError : BoardDetailUiState
    data object Error : BoardDetailUiState
    data object Loading : BoardDetailUiState
}

sealed interface BoardPostApiUiState {
    data object Success : BoardPostApiUiState
    data object HttpError : BoardPostApiUiState
    data object NetworkError : BoardPostApiUiState
    data object Error : BoardPostApiUiState
    data object Loading : BoardPostApiUiState
}

sealed interface PatchLikeFavoriteUiState {
    data class Success(
        val likeCount: Int,
        val favoriteCount: Int,
        val isLiked: Boolean,
        val isFavorite: Boolean
    ) : PatchLikeFavoriteUiState

    data object HttpError : PatchLikeFavoriteUiState
    data object NetworkError : PatchLikeFavoriteUiState
    data object Error : PatchLikeFavoriteUiState
    data object Loading : PatchLikeFavoriteUiState
}