package com.example.haengsha.model.viewModel.event

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.haengsha.HaengshaApplication
import com.example.haengsha.model.dataSource.EventDataRepository
import com.example.haengsha.model.network.apiService.EventResponse
import com.example.haengsha.model.network.apiService.toEventCardData
import com.example.haengsha.ui.screens.home.EventCardData
import com.example.haengsha.ui.screens.home.SharedViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.Closeable
import java.io.IOException
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
open class EventViewModel(
    private val eventDataRepository: EventDataRepository,
    private val sharedViewModel: SharedViewModel
) : ViewModel() {

    companion object {
        private lateinit var sharedViewModelInstance: SharedViewModel

        fun Factory(sharedViewModel: SharedViewModel): ViewModelProvider.Factory {
            sharedViewModelInstance = sharedViewModel
            return viewModelFactory {
                initializer {
                    val application =
                        this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as HaengshaApplication
                    val eventDataRepository = application.container.eventDataRepository
                    EventViewModel(eventDataRepository, sharedViewModel)
                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getEventByDate(eventType: String, date: LocalDate) {
        viewModelScope.launch {
            try {
                var eventTypeConverted = if (eventType == "Festival") 1 else 0

                val eventGetResponse: List<EventResponse> =
                    eventDataRepository.getEventByDate(eventTypeConverted, date.toString())

                val eventCardDataList: List<EventCardData> =
                    eventGetResponse.map { it.toEventCardData() }

                if (eventType == "Festival") {
                    sharedViewModel.updateFestivalItems(eventCardDataList)
                } else {
                    sharedViewModel.updateAcademicItems(eventCardDataList)
                }

                sharedViewModel.updateSelectedDate(date)

            } catch (e: HttpException) {
                val errorMessage = e.response()?.errorBody()?.string() ?: "이벤트를 불러오지 못했습니다."
            } catch (e: IOException) {
                val errorMessage = "이벤트를 불러오지 못했습니다."
            } catch (e: Exception) {
                val errorMessage = "이벤트를 불러오지 못했습니다."
                e.printStackTrace()
            }
        }
    }
}