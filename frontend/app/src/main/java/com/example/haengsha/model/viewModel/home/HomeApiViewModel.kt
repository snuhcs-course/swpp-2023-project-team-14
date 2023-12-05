package com.example.haengsha.model.viewModel.home

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
import com.example.haengsha.model.dataSource.HomeDataRepository
import com.example.haengsha.model.network.dataModel.EventResponse
import com.example.haengsha.model.uiState.home.HomeApiUiState
import com.example.haengsha.model.uiState.home.RecommendationApiUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate

class HomeApiViewModel(private val homeDataRepository: HomeDataRepository) : ViewModel() {
    var homeApiUiState: HomeApiUiState by mutableStateOf(HomeApiUiState.Loading)
        private set

    var recommendationApiUiState: RecommendationApiUiState by mutableStateOf(
        RecommendationApiUiState.Loading
    )
        private set

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as HaengshaApplication
                val homeDataRepository = application.container.homeDataRepository
                HomeApiViewModel(homeDataRepository)
            }
        }
    }


    fun getEventByDate(date: LocalDate) {
        viewModelScope.launch {
            homeApiUiState = HomeApiUiState.Loading
            homeApiUiState = try {
                val festivalResponse: List<EventResponse>? =
                    homeDataRepository.getEventByDate(1, date.toString())
                val academicResponse: List<EventResponse>? =
                    homeDataRepository.getEventByDate(0, date.toString())
                HomeApiUiState.Success(festivalResponse, academicResponse)
            } catch (e: HttpException) {
                HomeApiUiState.HttpError
            } catch (e: IOException) {
                HomeApiUiState.NetworkError
            } catch (e: Exception) {
                HomeApiUiState.Error
            }
        }
    }

    fun getRecommendationList(token: String) {
        viewModelScope.launch {
            recommendationApiUiState = RecommendationApiUiState.Loading
            recommendationApiUiState = try {
                val authToken = "Token $token"
                val recommendationList =
                    homeDataRepository.getRecommendationList(authToken)
                RecommendationApiUiState.RecommendationListResult(recommendationList)
            } catch (e: HttpException) {
                RecommendationApiUiState.HttpError
            } catch (e: IOException) {
                RecommendationApiUiState.NetworkError
            } catch (e: Exception) {
                RecommendationApiUiState.Error
            }
        }
    }
}
