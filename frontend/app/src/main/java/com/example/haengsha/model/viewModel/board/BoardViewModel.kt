package com.example.haengsha.model.viewModel.board

import androidx.lifecycle.ViewModel
import com.example.haengsha.model.uiState.board.BoardUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BoardViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BoardUiState())
    val uiState = _uiState.asStateFlow()

    var isError = false

    fun isError() {
        isError = true
    }

    fun resetError() {
        isError = false
    }

    fun saveToken(token: String) {
        val authToken = "Token $token"
        _uiState.update { currentState ->
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
        updateSearchParameter(newKeyword, "keyword")
    }

    fun updateIsFestival(newIsFestival: Int) {
        updateSearchParameter(newIsFestival.toString(), "isFestival")
    }

    fun updateStartDate(newStartDate: String) {
        updateSearchParameter(newStartDate, "startDate")
    }

    fun updateEndDate(newEndDate: String) {
        updateSearchParameter(newEndDate, "endDate")
    }

    fun updateInitialState() {
        updateSearchParameter("foo", "bar")
    }

    fun resetBoardUiState() {
        _uiState.value = BoardUiState()
    }

    private fun updateSearchParameter(newParameter: String, type: String) {
        _uiState.update { currentState ->
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