package com.example.haengsha.model.viewModel.login

import androidx.lifecycle.ViewModel
import com.example.haengsha.model.uiState.login.FindPasswordUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FindPasswordViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(FindPasswordUiState())
    val uiState: StateFlow<FindPasswordUiState> = _uiState.asStateFlow()

    fun updateEmail(email: String = "") {
        updateFindPasswordData(email)
    }

    fun resetFindPasswordData() {
        _uiState.value = FindPasswordUiState()
    }

    private fun updateFindPasswordData(newFindPasswordData: String) {
        _uiState.update { currentState ->
            currentState.copy(
                email = newFindPasswordData
            )
        }
    }
}