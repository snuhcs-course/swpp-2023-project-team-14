package com.example.haengsha.model.viewModel.event

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.haengsha.HaengshaApplication
import com.example.haengsha.model.dataSource.RecommendationDataRepository
import com.example.haengsha.model.network.apiService.toEventCardData
import com.example.haengsha.model.uiState.recommendation.RecommendationApiUiState
import com.example.haengsha.ui.screens.home.EventCardData
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class RecommendationApiViewModel(private val recommendationDataRepository: RecommendationDataRepository) :
    ViewModel() {

    var recommendationUiState: RecommendationApiUiState by mutableStateOf(RecommendationApiUiState.Loading)
        private set

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as HaengshaApplication
                val recommendationDataRepository =
                    application.container.recommendationDataRepository
                RecommendationApiViewModel(recommendationDataRepository)
            }
        }
    }

    fun getRecommendationList(token: String) {
        viewModelScope.launch {
            recommendationUiState = RecommendationApiUiState.Loading
            recommendationUiState = try {
                val authToken = "Token $token"
                val recommendationList =
                    recommendationDataRepository.getRecommendationList(authToken)
                RecommendationApiUiState.RecommendationListResult(recommendationList)
            } catch (e: HttpException) {
                val errorMessage = "이벤트를 불러오지 못했습니다."
                RecommendationApiUiState.HttpError
            } catch (e: IOException) {
                val errorMessage = "이벤트를 불러오지 못했습니다."
                RecommendationApiUiState.NetworkError
            } catch (e: Exception) {
                e.message?.let { Log.d("recommendation", it) }
                RecommendationApiUiState.Error
            }
        }
    }
}

