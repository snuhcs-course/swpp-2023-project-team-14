package com.example.haengsha.model.viewModel.event

import com.example.haengsha.model.network.apiService.RecommendResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RecommendationViewModel {
    private val _uiState = MutableStateFlow(RecommendResponse)
    val uiState = _uiState.asStateFlow()

}