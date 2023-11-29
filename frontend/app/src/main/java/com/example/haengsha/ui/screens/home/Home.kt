package com.example.haengsha.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.haengsha.R
import com.example.haengsha.model.network.dataModel.EventResponse
import com.example.haengsha.model.uiState.UserUiState
import com.example.haengsha.model.uiState.home.HomeApiUiState
import com.example.haengsha.model.viewModel.home.HomeApiViewModel
import com.example.haengsha.model.viewModel.home.HomeViewModel
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
import java.util.Calendar
import java.util.Date
import java.util.Locale

private val dateFormatter = DateTimeFormatter.ofPattern("dd")
private val monthFormatter = DateTimeFormatter.ofPattern("MMMM")

@Composable
fun Home(
    homeViewModel: HomeViewModel,
    homeApiViewModel: HomeApiViewModel,
    innerPadding: PaddingValues,
    userUiState: UserUiState
) {
    HomeScreen(homeApiViewModel, innerPadding, userUiState, homeViewModel)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    homeApiViewModel: HomeApiViewModel,
    innerPadding: PaddingValues,
    userUiState: UserUiState,
    homeViewModel: HomeViewModel
) {
    val homeApiUiState = homeApiViewModel.homeApiUiState
    var selection by remember { mutableStateOf(LocalDate.now()) }
    val currentMonth = selection.yearMonth
    val startDate = remember { currentMonth.minusMonths(100).atStartOfMonth() } // Adjust as needed
    val endDate = remember { currentMonth.plusMonths(100).atEndOfMonth() } // Adjust as needed
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library

    LaunchedEffect(Unit) {
        homeApiViewModel.getEventByDate(selection)
    }

    val state = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstVisibleWeekDate = selection,
        firstDayOfWeek = firstDayOfWeek,
    )

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
                        homeApiViewModel.getEventByDate(selection)
                    }
                }
            },
        )

        when (homeApiUiState) {
            is HomeApiUiState.Success -> {
                TabView(homeViewModel, homeApiViewModel, userUiState)
            }

            is HomeApiUiState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    CircularProgressIndicator(
                        color = HaengshaBlue,
                        strokeWidth = 3.dp
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "행사 불러오는 중...",
                        fontFamily = poppins,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = HaengshaBlue
                    )
                }
            }

            is HomeApiUiState.HttpError -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "이벤트를 불러오는 중 문제가 발생했어요!\n\n다시 시도해주세요.",
                        fontFamily = poppins,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = HaengshaBlue
                    )
                }
            }

            is HomeApiUiState.NetworkError -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "인터넷 연결을 확인해주세요.",
                        fontFamily = poppins,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = HaengshaBlue
                    )
                }
            }

            is HomeApiUiState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "알 수 없는 문제가 발생했어요!\n\n메일로 문의해주세요.",
                        fontFamily = poppins,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = HaengshaBlue
                    )
                }
            }
        }

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
    val currentDate = LocalDate.now()
    val initialDate = Calendar.getInstance()
    initialDate.set(currentDate.year, currentDate.monthValue - 1, currentDate.dayOfMonth)
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate.timeInMillis,
        yearRange = currentDate.year - 1..currentDate.year + 1
    )

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onDateSelected(selectedDate)
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
            state = datePickerState,
            showModeToggle = false
        )
    }
}

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

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
    return formatter.format(Date(millis))
}

fun EventResponse.toEventCardData(): EventCardData {
    val startDate = stringToDate(eventDurations[0].eventDay)
    var endDate = startDate
    if (eventDurations.size > 1) {
        endDate = stringToDate(eventDurations[eventDurations.size - 1].eventDay)
    }

    var eventType = "Festival"
    if (!isFestival) {
        eventType = "Academic"
    }
    return EventCardData(
        organizer = author.nickname,
        eventTitle = title,
        startDate = startDate,
        endDate = endDate,
        likes = likeCount,
        favorites = favoriteCount,
        eventType = eventType,
        place = place,
        time = time ?: "wow"
    )
}

fun stringToDate(dateString: String): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    try {
        return LocalDate.parse(dateString, formatter)
        //val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        //date = format.parse(dateString)!!
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return LocalDate.now()
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val homeViewModel = viewModel<HomeViewModel>()
    val homeApiViewModel: HomeApiViewModel =
        viewModel(factory = HomeApiViewModel.Factory(homeViewModel))

    Home(
        homeViewModel = homeViewModel,
        homeApiViewModel = homeApiViewModel,
        innerPadding = PaddingValues(0.dp),
        userUiState = UserUiState("foo", "bar")
    )
}