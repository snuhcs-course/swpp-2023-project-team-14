package com.example.haengsha.model.viewModel.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.haengsha.ui.screens.home.EventCardData
import java.time.LocalDate

class HomeViewModel : ViewModel() {
    private val _selectedDate = MutableLiveData(LocalDate.now())
    private val _festivalItems = MutableLiveData<List<EventCardData>?>()
    private val _academicItems = MutableLiveData<List<EventCardData>?>()
    val festivalItems: MutableLiveData<List<EventCardData>?> = _festivalItems
    val academicItems: MutableLiveData<List<EventCardData>?> = _academicItems

    fun updateSelectedDate(newDate: LocalDate) {
        _selectedDate.value = newDate     // Update functions to set LiveData properties
    }

    fun updateEventItems(festivalItems: List<EventCardData>?, academicItems: List<EventCardData>?) {
        _festivalItems.value = festivalItems
        _academicItems.value = academicItems
    }
}
