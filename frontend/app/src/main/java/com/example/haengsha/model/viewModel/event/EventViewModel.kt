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
    fun getEventByDate(date: LocalDate) {
        viewModelScope.launch {
            try {
                val festivalResponse: List<EventResponse>? =
                    eventDataRepository.getEventByDate(1, date.toString())

                val academicResponse: List<EventResponse>? =
                    eventDataRepository.getEventByDate(0, date.toString())

                val academicCardDataList: List<EventCardData>? =
                    academicResponse?.map { it.toEventCardData() }

                val festivalCardDataList: List<EventCardData>? =
                    festivalResponse?.map { it.toEventCardData() }

                sharedViewModel.updateEventItems(festivalCardDataList,academicCardDataList)

                sharedViewModel.updateSelectedDate(date)

            } catch (e: HttpException) {
                val errorMessage = e.response()?.errorBody()?.string() ?: "이벤트를 불러오지 못했습니다."
                sharedViewModel.updateEventItems(listOf(),listOf())
                sharedViewModel.updateSelectedDate(date)
                e.printStackTrace()
            } catch (e: IOException) {
                val errorMessage = "이벤트를 불러오지 못했습니다."
            } catch (e: Exception) {
                val errorMessage = "이벤트를 불러오지 못했습니다."
                e.printStackTrace()
            }
        }
    }
}