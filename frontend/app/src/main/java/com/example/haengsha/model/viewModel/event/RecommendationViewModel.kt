package com.example.haengsha.model.viewModel.event

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.haengsha.HaengshaApplication
import com.example.haengsha.model.dataSource.RecommendationDataRepository
import com.example.haengsha.model.network.apiService.toEventCardData
import com.example.haengsha.model.uiState.recommendation.RecommendationUiState
import com.example.haengsha.ui.screens.home.EventCardData
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class RecommendationViewModel(private val recommendationDataRepository: RecommendationDataRepository) :
    ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as HaengshaApplication
                val recommendationDataRepository =
                    application.container.recommendationDataRepository
                RecommendationViewModel(recommendationDataRepository)
            }
        }
    }

    fun getRecommendationList(token: String){
        viewModelScope.launch {

            try {
                val authToken = "Token: $token"
                val recommendationList = recommendationDataRepository.getRecommendationList(authToken)
                val recommendationCardDataList: List<EventCardData>? =
                    recommendationList?.map { it.toEventCardData() }
                RecommendationUiState.RecommendationListResult(recommendationCardDataList)
            } catch (e: HttpException) {
                val errorMessage = "이벤트를 불러오지 못했습니다."
                RecommendationUiState.HttpError
            } catch (e: IOException) {
                val errorMessage = "이벤트를 불러오지 못했습니다."
                RecommendationUiState.NetworkError
            } catch (e: Exception) {
                e.message?.let { Log.d("recommendation", it) }
                RecommendationUiState.Error
            }
        }
    }
}
