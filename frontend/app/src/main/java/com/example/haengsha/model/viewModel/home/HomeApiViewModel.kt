package com.example.haengsha.model.viewModel.home

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
import com.example.haengsha.model.dataSource.HomeDataRepository
import com.example.haengsha.model.network.dataModel.EventResponse
import com.example.haengsha.model.uiState.home.RecommendationApiUiState
import com.example.haengsha.ui.screens.home.EventCardData
import com.example.haengsha.ui.screens.home.toEventCardData
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate

class HomeApiViewModel(
    private val homeDataRepository: HomeDataRepository,
    private val homeViewModel: HomeViewModel
) : ViewModel() {
    var recommendationUiState: RecommendationApiUiState by mutableStateOf(RecommendationApiUiState.Loading)
        private set

    companion object {
        private lateinit var homeViewModelInstance: HomeViewModel

        fun Factory(homeViewModel: HomeViewModel): ViewModelProvider.Factory {
            homeViewModelInstance = homeViewModel
            return viewModelFactory {
                initializer {
                    val application =
                        this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as HaengshaApplication
                    val homeDataRepository = application.container.homeDataRepository
                    HomeApiViewModel(homeDataRepository, homeViewModel)
                }
            }
        }
    }


    fun getEventByDate(date: LocalDate) {
        viewModelScope.launch {
            try {
                val festivalResponse: List<EventResponse>? =
                    homeDataRepository.getEventByDate(1, date.toString())

                val academicResponse: List<EventResponse>? =
                    homeDataRepository.getEventByDate(0, date.toString())

                val academicCardDataList: List<EventCardData>? =
                    academicResponse?.map { it.toEventCardData() }

                val festivalCardDataList: List<EventCardData>? =
                    festivalResponse?.map { it.toEventCardData() }

                homeViewModel.updateEventItems(festivalCardDataList, academicCardDataList)

                homeViewModel.updateSelectedDate(date)

            } catch (e: HttpException) {
                homeViewModel.updateEventItems(listOf(), listOf())
                homeViewModel.updateSelectedDate(date)
            } catch (e: IOException) {
                // Error 핸들링
            } catch (e: Exception) {
                // Error 핸들링
            }
        }
    }

    fun getRecommendationList(token: String) {
        viewModelScope.launch {
            recommendationUiState = RecommendationApiUiState.Loading
            recommendationUiState = try {
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