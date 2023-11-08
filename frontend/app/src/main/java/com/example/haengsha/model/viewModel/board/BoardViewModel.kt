package com.example.haengsha.model.viewModel.board

import android.util.Log
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
import com.example.haengsha.model.uiState.board.BoardDetailUiState
import com.example.haengsha.model.uiState.board.BoardListUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class BoardViewModel(private val boardDataRepository: BoardDataRepository) : ViewModel() {
    var boardListUiState: BoardListUiState by mutableStateOf(BoardListUiState.Loading)
        private set

    var boardDetailUiState: BoardDetailUiState by mutableStateOf(BoardDetailUiState.Loading)
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
                e.message?.let { Log.d("board", it) }
                BoardListUiState.Error
            }
        }
    }

    fun getBoardDetail(token: String, postId: Int) {
        viewModelScope.launch {
            boardDetailUiState = try {
                val authToken = "Token: $token"
                val boardDetailResult = boardDataRepository.getBoardDetail(authToken, postId)
                BoardDetailUiState.BoardDetailResult(boardDetailResult)
            } catch (e: HttpException) {
                BoardDetailUiState.HttpError
            } catch (e: IOException) {
                BoardDetailUiState.NetworkError
            } catch (e: Exception) {
                e.message?.let { Log.d("detail", it) }
                BoardDetailUiState.Error
            }
        }
    }
}