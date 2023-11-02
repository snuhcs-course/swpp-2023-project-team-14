package com.example.haengsha.ui.screens.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.haengsha.model.uiState.UserUiState
import com.example.haengsha.model.viewModel.event.EventViewModel
import com.example.haengsha.ui.theme.HaengshaBlue
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale


class SharedViewModel() : ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    private val _selectedDate = MutableLiveData(LocalDate.now())

    @RequiresApi(Build.VERSION_CODES.O)
    val selectedDate: LiveData<LocalDate> = _selectedDate

    // Initialize _festivalItems and _academicItems with initial data
    @RequiresApi(Build.VERSION_CODES.O)
    private val _festivalItems = MutableLiveData<List<EventCardData>?>()

    @RequiresApi(Build.VERSION_CODES.O)
    private val _academicItems = MutableLiveData<List<EventCardData>?>()


    @RequiresApi(Build.VERSION_CODES.O)
    val festivalItems: MutableLiveData<List<EventCardData>?> = _festivalItems


    @RequiresApi(Build.VERSION_CODES.O)
    val academicItems: MutableLiveData<List<EventCardData>?> = _academicItems


    // Update functions to set LiveData properties
    @RequiresApi(Build.VERSION_CODES.O)
    fun updateSelectedDate(newDate: LocalDate) {
        _selectedDate.value = newDate
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateFestivalItems(newItems: List<EventCardData>?) {
        _festivalItems.value = newItems
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateAcademicItems(newItems: List<EventCardData>?) {
        _academicItems.value = newItems
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDateToYYYYMMDD(date: MutableLiveData<LocalDate>): String {
    val localDate = date.value ?: LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return localDate.format(formatter)
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    userUiState: UserUiState,
    sharedViewModel: SharedViewModel
) {
    val eventViewModel: EventViewModel =
        viewModel(factory = EventViewModel.Factory(sharedViewModel))
    eventViewModel.getEventByDate(eventType = "Academic", LocalDate.now())
    eventViewModel.getEventByDate(eventType = "Festival", LocalDate.now())
    val currentDate = remember { LocalDate.now() }
    val currentMonth = remember { YearMonth.now() }
    val startDate = remember { currentMonth.minusMonths(100).atStartOfMonth() } // Adjust as needed
    val endDate = remember { currentMonth.plusMonths(100).atEndOfMonth() } // Adjust as needed
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library
    var selection by remember { mutableStateOf(currentDate) }
    val state = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstVisibleWeekDate = currentDate,
        firstDayOfWeek = firstDayOfWeek
    )

    var isDatePickerDialogVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        WeekCalendar(
            state = state,
            dayContent = { day ->
                Day(day.date, isSelected = selection == day.date) { clicked ->
                    if (selection != clicked) {
                        selection = clicked
                        //pickDate = clicked
                        eventViewModel.getEventByDate(eventType = "Academic", clicked)
                        eventViewModel.getEventByDate(eventType = "Festival", clicked)
                    }
                }
            },
        )
        TabView(sharedViewModel, selection, 0)
        Button(
            onClick = {
                isDatePickerDialogVisible = true
            }
        ) {
            Text("Pick Date")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private val dateFormatter = DateTimeFormatter.ofPattern("dd")

@RequiresApi(Build.VERSION_CODES.O)
fun DayOfWeek.displayText(uppercase: Boolean = false): String {
    return getDisplayName(TextStyle.SHORT, Locale.ENGLISH).let { value ->
        if (uppercase) value.uppercase(Locale.ENGLISH) else value
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun Day(date: LocalDate, isSelected: Boolean, onClick: (LocalDate) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onClick(date) },
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = date.dayOfWeek.displayText(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
            )
            Text(
                text = dateFormatter.format(date),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(HaengshaBlue)
                    .align(Alignment.BottomCenter),
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Home(
    userUiState: UserUiState,
    mainNavController: NavController
) {
    val sharedViewModel = viewModel<SharedViewModel>()
    Column {
        HomeScreen(userUiState, sharedViewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Home(userUiState = UserUiState("foo", "bar"), rememberNavController())
}