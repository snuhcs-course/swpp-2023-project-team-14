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
import com.example.haengsha.model.uiState.board.BoardUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class BoardViewModel(private val boardDataRepository: BoardDataRepository) : ViewModel() {
    var boardUiState: BoardUiState by mutableStateOf(BoardUiState.Loading)
        private set

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as HaengshaApplication
                val boardDataRepository = application.container.boardDataRepository
                BoardViewModel(boardDataRepository)
            }
        }
    }

    fun getBoardList(token: String, startDate: String) {
        viewModelScope.launch {
            boardUiState = BoardUiState.Loading
            boardUiState = try {
                val boardResult = boardDataRepository.getBoardList(token, startDate)
                BoardUiState.BoardResult(
                    boardResult.id,
                    boardResult.title,
                    boardResult.isFestival,
                    boardResult.author,
                    boardResult.eventDurations,
                    boardResult.place,
                    boardResult.time,
                    boardResult.content,
                    boardResult.image,
                    boardResult.likeCount,
                    boardResult.favoriteCount
                )
            } catch (e: HttpException) {
                BoardUiState.HttpError(e.message())
            } catch (e: IOException) {
                BoardUiState.NetworkError
            }
        }
    }

    fun getBoardDetail(postId: Int) {
        viewModelScope.launch {
            boardUiState = try {
                val boardResult = boardDataRepository.getBoardDetail(postId)
                BoardUiState.BoardResult(
                    boardResult.id,
                    boardResult.title,
                    boardResult.isFestival,
                    boardResult.author,
                    boardResult.eventDurations,
                    boardResult.place,
                    boardResult.time,
                    boardResult.content,
                    boardResult.image,
                    boardResult.likeCount,
                    boardResult.favoriteCount
                )
            } catch (e: HttpException) {
                BoardUiState.HttpError(e.message())
            } catch (e: IOException) {
                BoardUiState.NetworkError
            }
        }
    }
}