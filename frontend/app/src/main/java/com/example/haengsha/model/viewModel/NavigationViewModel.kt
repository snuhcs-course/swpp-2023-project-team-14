package com.example.haengsha.model.viewModel

import androidx.lifecycle.ViewModel
import com.example.haengsha.model.uiState.NavigationUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NavigationViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NavigationUiState())
    val uiState = _uiState.asStateFlow()

    fun updateRouteUiState(type: String, screen: String) {
        _uiState.update { currentState ->
            currentState.copy(type = type, screen = screen)
        }
    }
}