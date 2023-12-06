package com.example.haengsha.model.viewModel.board

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.haengsha.HaengshaApplication
import com.example.haengsha.model.dataSource.BoardDataRepository
import com.example.haengsha.model.network.dataModel.BoardPostRequest
import com.example.haengsha.model.network.dataModel.SearchRequest
import com.example.haengsha.model.uiState.board.BoardDetailUiState
import com.example.haengsha.model.uiState.board.BoardFavoriteUiState
import com.example.haengsha.model.uiState.board.BoardListUiState
import com.example.haengsha.model.uiState.board.BoardPostApiUiState
import com.example.haengsha.model.uiState.board.PatchLikeFavoriteUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class BoardApiViewModel(private val boardDataRepository: BoardDataRepository) : ViewModel() {
    var boardListUiState: BoardListUiState by mutableStateOf(BoardListUiState.Loading)
        private set

    var boardFavoriteUiState: BoardFavoriteUiState by mutableStateOf(BoardFavoriteUiState.Loading)
        private set

    var boardDetailUiState: BoardDetailUiState by mutableStateOf(BoardDetailUiState.Loading)
        private set

    var boardPostApiUiState: BoardPostApiUiState by mutableStateOf(BoardPostApiUiState.Loading)
        private set

    var patchLikeFavoriteUiState: PatchLikeFavoriteUiState by mutableStateOf(
        PatchLikeFavoriteUiState.Loading
    )
        private set

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as HaengshaApplication
                val boardDataRepository = application.container.boardDataRepository
                BoardApiViewModel(boardDataRepository)
            }
        }
    }

    fun getBoardList(startDate: String) {
        viewModelScope.launch {
            boardListUiState = BoardListUiState.Loading
            boardListUiState = try {
                val boardListResult = boardDataRepository.getBoardList(startDate)
                BoardListUiState.BoardListResult(boardListResult)
            } catch (e: HttpException) {
                BoardListUiState.HttpError
            } catch (e: IOException) {
                BoardListUiState.NetworkError
            } catch (e: Exception) {
                BoardListUiState.Error
            }
        }
    }

    fun getBoardDetail(token: String, postId: Int) {
        viewModelScope.launch {
            boardDetailUiState = BoardDetailUiState.Loading
            boardDetailUiState = try {
                val authToken = "Token $token"
                val boardDetailResult = boardDataRepository.getBoardDetail(authToken, postId)
                BoardDetailUiState.BoardDetailResult(boardDetailResult)
            } catch (e: HttpException) {
                BoardDetailUiState.HttpError
            } catch (e: IOException) {
                BoardDetailUiState.NetworkError
            } catch (e: Exception) {
                BoardDetailUiState.Error
            }
        }
    }

    fun getFavoriteBoardList(token: String) {
        viewModelScope.launch {
            boardFavoriteUiState = BoardFavoriteUiState.Loading
            boardFavoriteUiState = try {
                val authToken = "Token $token"
                val boardFavoriteResult = boardDataRepository.getFavoriteList(authToken)
                BoardFavoriteUiState.BoardListResult(boardFavoriteResult)
            } catch (e: HttpException) {
                BoardFavoriteUiState.HttpError
            } catch (e: IOException) {
                BoardFavoriteUiState.NetworkError
            } catch (e: Exception) {
                BoardFavoriteUiState.Error
            }
        }
    }

    fun postEvent(boardPostRequest: BoardPostRequest) {
        viewModelScope.launch {
            boardPostApiUiState = BoardPostApiUiState.Loading
            boardPostApiUiState = try {
                boardDataRepository.postEvent(boardPostRequest)
                BoardPostApiUiState.Success
            } catch (e: HttpException) {
                BoardPostApiUiState.HttpError
            } catch (e: IOException) {
                BoardPostApiUiState.NetworkError
            } catch (e: Exception) {
                BoardPostApiUiState.Error
            }
        }
    }

    fun patchLike(token: String, postId: Int) {
        viewModelScope.launch {
            patchLikeFavoriteUiState = PatchLikeFavoriteUiState.Loading
            patchLikeFavoriteUiState = try {
                val authToken = "Token $token"
                val boardDetailResult = boardDataRepository.patchLike(authToken, postId)
                PatchLikeFavoriteUiState.Success(
                    boardDetailResult.likeCount,
                    boardDetailResult.favoriteCount,
                    boardDetailResult.isLiked,
                    boardDetailResult.isFavorite
                )
            } catch (e: HttpException) {
                PatchLikeFavoriteUiState.HttpError
            } catch (e: IOException) {
                PatchLikeFavoriteUiState.NetworkError
            } catch (e: Exception) {
                PatchLikeFavoriteUiState.Error
            }
        }
    }

    fun patchFavorite(token: String, postId: Int) {
        viewModelScope.launch {
            patchLikeFavoriteUiState = PatchLikeFavoriteUiState.Loading
            patchLikeFavoriteUiState = try {
                val authToken = "Token $token"
                val boardDetailResult = boardDataRepository.patchFavorite(authToken, postId)
                PatchLikeFavoriteUiState.Success(
                    boardDetailResult.likeCount,
                    boardDetailResult.favoriteCount,
                    boardDetailResult.isLiked,
                    boardDetailResult.isFavorite
                )
            } catch (e: HttpException) {
                PatchLikeFavoriteUiState.HttpError
            } catch (e: IOException) {
                PatchLikeFavoriteUiState.NetworkError
            } catch (e: Exception) {
                PatchLikeFavoriteUiState.Error
            }
        }
    }

    fun resetPatchLikeFavoriteUiState() {
        patchLikeFavoriteUiState = PatchLikeFavoriteUiState.Loading
    }

    fun resetBoardListUiStateToLoading() {
        boardListUiState = BoardListUiState.Loading
    }

    fun resetBoardListUiStateToDefault() {
        boardListUiState = BoardListUiState.Default
    }

    fun resetBoardPostApiUiState() {
        boardPostApiUiState = BoardPostApiUiState.Loading
    }

    fun searchEvent(searchRequest: SearchRequest) {
        viewModelScope.launch {
            boardListUiState = BoardListUiState.Loading
            boardListUiState = try {
                val boardSearchResult = boardDataRepository.searchEvent(searchRequest)
                BoardListUiState.BoardListResult(boardSearchResult)
            } catch (e: HttpException) {
                BoardListUiState.HttpError
            } catch (e: IOException) {
                BoardListUiState.NetworkError
            } catch (e: Exception) {
                BoardListUiState.Error
            }
        }
    }
}