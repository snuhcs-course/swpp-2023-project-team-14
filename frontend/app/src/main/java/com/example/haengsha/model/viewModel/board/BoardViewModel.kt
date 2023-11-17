package com.example.haengsha.model.viewModel.board

import androidx.lifecycle.ViewModel
import com.example.haengsha.model.network.dataModel.BoardListResponse
import com.example.haengsha.model.uiState.board.BoardUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BoardViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BoardUiState())
    val uiState = _uiState.asStateFlow()

    fun saveToken(token: String) {
        val authToken = "Token $token"
        _uiState.update { currentState ->
            currentState.copy(
                token = authToken
            )
        }
    }

    fun updateBoardList(newBoardList: List<BoardListResponse>) {
        updateSearchParameter("", newBoardList, "boardList")
    }

    fun updateKeyword(newKeyword: String) {
        updateSearchParameter(newKeyword, listOf(), "keyword")
    }

    fun updateIsFestival(newIsFestival: Int) {
        updateSearchParameter(newIsFestival.toString(), listOf(), "isFestival")
    }

    fun updateStartDate(newStartDate: String) {
        updateSearchParameter(newStartDate, listOf(), "startDate")
    }

    fun updateEndDate(newEndDate: String) {
        updateSearchParameter(newEndDate, listOf(), "endDate")
    }

    fun setInitial() {
        _uiState.update { currentState ->
            currentState.copy(
                initialState = true
            )
        }
    }


    fun resetUiState() {
        _uiState.update { currentState ->
            currentState.copy(
                boardList = listOf(),
                keyword = "",
                isFestival = 0,
                startDate = "",
                endDate = "",
                initialState = false
            )
        }
    }

    private fun updateSearchParameter(
        newParameter: String,
        newBoardList: List<BoardListResponse>,
        type: String
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                boardList = if (type == "boardList") newBoardList else currentState.boardList,
                keyword = if (type == "keyword") newParameter else currentState.keyword,
                isFestival = if (type == "isFestival") newParameter.toInt() else currentState.isFestival,
                startDate = if (type == "startDate") newParameter else currentState.startDate,
                endDate = if (type == "endDate") newParameter else currentState.endDate,
                initialState = false
            )
        }
    }
}