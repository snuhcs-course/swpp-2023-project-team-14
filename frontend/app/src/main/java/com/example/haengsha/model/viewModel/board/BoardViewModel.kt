package com.example.haengsha.model.viewModel.board

import androidx.lifecycle.ViewModel
import com.example.haengsha.model.uiState.board.BoardPostUiState
import com.example.haengsha.model.uiState.board.BoardUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BoardViewModel : ViewModel() {
    private val _boardUiState = MutableStateFlow(BoardUiState())
    val boardUiState = _boardUiState.asStateFlow()

    private val _boardPostUiState = MutableStateFlow(BoardPostUiState())
    val boardPostUiState = _boardPostUiState.asStateFlow()

    private val _eventId = MutableStateFlow(0)
    val eventId = _eventId.asStateFlow()

    fun updateEventId(newEventId: Int) {
        _eventId.value = newEventId
    }

    var isError = false

    fun isError() {
        isError = true
    }

    fun resetError() {
        isError = false
    }

    fun saveToken(token: String) {
        val authToken = "Token $token"
        _boardUiState.update { currentState ->
            currentState.copy(
                token = authToken,
                keyword = currentState.keyword,
                isFestival = currentState.isFestival,
                startDate = currentState.startDate,
                endDate = currentState.endDate,
                initialState = currentState.initialState
            )
        }
    }

    fun updateKeyword(newKeyword: String) {
        updateSearchParameter(newKeyword, "keyword", "filter")
    }

    fun updateIsFestival(newIsFestival: Int) {
        updateSearchParameter(newIsFestival.toString(), "isFestival", "filter")
    }

    fun updateFilterStartDate(newStartDate: String) {
        updateSearchParameter(newStartDate, "startDate", "filter")
    }

    fun updateFilterEndDate(newEndDate: String) {
        updateSearchParameter(newEndDate, "endDate", "filter")
    }

    fun updateFilterInitialState() {
        updateSearchParameter("foo", "bar", "filter")
    }

    fun resetFilterInitialState() {
        _boardUiState.update { currentState ->
            currentState.copy(initialState = true)
        }
    }

    fun resetBoardUiState() {
        _boardUiState.value = BoardUiState()
    }

    fun updatePostStartDate(newStartDate: String) {
        updateSearchParameter(newStartDate, "startDate", "post")
    }

    fun updatePostEndDate(newEndDate: String) {
        updateSearchParameter(newEndDate, "endDate", "post")
    }

    fun resetBoardPostUiState() {
        _boardPostUiState.value = BoardPostUiState()
    }

    private fun updateSearchParameter(newParameter: String, type: String, usage: String) {
        if (usage == "post") {
            _boardPostUiState.update { currentState ->
                currentState.copy(
                    startDate = if (type == "startDate") newParameter else currentState.startDate,
                    endDate = if (type == "endDate") newParameter else currentState.endDate
                )
            }
        } else { //usage == "filter"
            _boardUiState.update { currentState ->
                currentState.copy(
                    keyword = if (type == "keyword") newParameter else currentState.keyword,
                    isFestival = if (type == "isFestival") newParameter.toInt() else currentState.isFestival,
                    startDate = if (type == "startDate") newParameter else currentState.startDate,
                    endDate = if (type == "endDate") newParameter else currentState.endDate,
                    initialState = false
                )
            }
        }
    }
}