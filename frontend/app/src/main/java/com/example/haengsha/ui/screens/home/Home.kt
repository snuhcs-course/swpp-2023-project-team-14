package com.example.haengsha.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.haengsha.R
import com.example.haengsha.model.uiState.UserUiState
import com.example.haengsha.model.viewModel.event.EventApiViewModel
import com.example.haengsha.model.viewModel.event.RecommendationApiViewModel
import com.example.haengsha.ui.theme.HaengshaBlue
import com.example.haengsha.ui.theme.poppins
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.core.yearMonth
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale


class SharedViewModel : ViewModel() {
    private val _selectedDate = MutableLiveData(LocalDate.now())
    val selectedDate: LiveData<LocalDate> = _selectedDate
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


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    recommendationApiViewModel: RecommendationApiViewModel,
    innerPadding: PaddingValues,
    userUiState: UserUiState,
    sharedViewModel: SharedViewModel
) {
    val eventApiViewModel: EventApiViewModel =
        viewModel(factory = EventApiViewModel.Factory(sharedViewModel))
    var selection by remember { mutableStateOf(LocalDate.now()) }
    val currentMonth = selection.yearMonth
    val startDate = remember { currentMonth.minusMonths(100).atStartOfMonth() } // Adjust as needed
    val endDate = remember { currentMonth.plusMonths(100).atEndOfMonth() } // Adjust as needed
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library

    recommendationApiViewModel.getRecommendationList(token = userUiState.token)

    eventApiViewModel.getEventByDate(selection)
    val state = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstVisibleWeekDate = selection,
        firstDayOfWeek = firstDayOfWeek,
    )


    var date by remember {
        mutableStateOf("Open date picker dialog")
    }

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    if (showDatePicker) {
        MyDatePickerDialog(
            onDateSelected = { selectedDate ->
                selection = parseStringToDate(selectedDate)
                showDatePicker = false // Close the date picker dialog
            },
            onDismiss = { showDatePicker = false }
        )
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            // Center-aligned Text
            Box(
                modifier = Modifier
                    .padding(start = 16.dp) // Add padding to the left of the Text
                    .align(Alignment.Center) // Align the Text to the center-left
            ) {
                Text(
                    text = monthFormatter.format(selection.month),
                    fontFamily = poppins,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // Right-aligned Image
            Box(
                modifier = Modifier
                    .padding(end = 16.dp) // Add padding to the right of the Image
                    .align(Alignment.CenterEnd) // Align the Image to the center-right
                    .clickable {
                        showDatePicker = true
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.calendar_icon),
                    contentDescription = "calendar icon",
                    modifier = Modifier.size(24.dp)

                )
            }
        }

        LaunchedEffect(selection) {
            state.animateScrollToWeek(selection)
        }

        WeekCalendar(
            state = state,
            userScrollEnabled = true,
            calendarScrollPaged = true,
            dayContent = { day ->
                Day(day.date, isSelected = selection == day.date) { clicked ->
                    if (selection != clicked) {
                        selection = clicked
                        eventApiViewModel.getEventByDate(selection)
                    }
                }
            },
        )

        TabView(sharedViewModel, recommendationApiViewModel, selection)


    }
}


private val dateFormatter = DateTimeFormatter.ofPattern("dd")

private val monthFormatter = DateTimeFormatter.ofPattern("MMMM")

fun parseStringToDate(dateString: String): LocalDate? {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return try {
        LocalDate.parse(dateString, formatter)
    } catch (e: Exception) {
        null // Handle the case when the input string is not in the expected format
    }
}

fun DayOfWeek.displayText(uppercase: Boolean = false): String {
    return getDisplayName(TextStyle.SHORT, Locale.ENGLISH).let { value ->
        if (uppercase) value.uppercase(Locale.ENGLISH) else value
    }
}

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                if (selectedDate != null) {
                    onDateSelected(selectedDate)
                }
                onDismiss()
            }

            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    return formatter.format(Date(millis))
}

@Composable
fun Home(
    recommendationApiViewModel: RecommendationApiViewModel,
    innerPadding: PaddingValues,
    userUiState: UserUiState
) {
    val sharedViewModel = viewModel<SharedViewModel>()

    HomeScreen(recommendationApiViewModel, innerPadding, userUiState, sharedViewModel)
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val recommendationApiViewModel: RecommendationApiViewModel =
        viewModel(factory = RecommendationApiViewModel.Factory)

    Home(
        recommendationApiViewModel = recommendationApiViewModel,
        innerPadding = PaddingValues(0.dp),
        userUiState = UserUiState("foo", "bar")
    )
}