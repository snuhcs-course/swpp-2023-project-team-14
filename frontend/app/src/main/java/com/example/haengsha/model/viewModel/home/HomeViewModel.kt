package com.example.haengsha.model.viewModel.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haengsha.ui.screens.home.EventCardData
import java.time.LocalDate

class HomeViewModel : ViewModel() {
    private val selectedDate = MutableLiveData(LocalDate.now())
    val festivalItems = MutableLiveData<List<EventCardData>?>()
    val academicItems = MutableLiveData<List<EventCardData>?>()

    fun updateSelectedDate(newDate: LocalDate) {
        selectedDate.value = newDate     // Update functions to set LiveData properties
    }

    fun updateEventItems(
        newFestivalItems: List<EventCardData>?,
        newAcademicItems: List<EventCardData>?
    ) {
        festivalItems.value = newFestivalItems
        academicItems.value = newAcademicItems
    }
}
