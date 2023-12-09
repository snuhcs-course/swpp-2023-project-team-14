package com.example.haengsha.model.viewModel.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haengsha.model.dataSource.EventCardData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class HomeViewModel : ViewModel() {
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate = _selectedDate.asStateFlow()
    var initialEnter = true
    var selectionChanged = false
    var initialRecommendationState = true
    val festivalItems = MutableLiveData<List<EventCardData>?>()
    val academicItems = MutableLiveData<List<EventCardData>?>()

    fun updateSelectedDate(newDate: LocalDate) {
        if (_selectedDate.value != newDate) {
            _selectedDate.value = newDate
            selectionChanged = true
        }
    }

    fun updateEventItems(
        newFestivalItems: List<EventCardData>?,
        newAcademicItems: List<EventCardData>?
    ) {
        festivalItems.value = newFestivalItems
        academicItems.value = newAcademicItems
    }
}
