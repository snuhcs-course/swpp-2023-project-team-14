package com.example.haengsha.ui.screens.home

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.haengsha.R
import com.example.haengsha.model.dataSource.EventCardData
import com.example.haengsha.model.network.dataModel.EventResponse
import com.example.haengsha.model.uiState.UserUiState
import com.example.haengsha.model.uiState.home.HomeApiUiState
import com.example.haengsha.model.viewModel.board.BoardViewModel
import com.example.haengsha.model.viewModel.home.HomeApiViewModel
import com.example.haengsha.model.viewModel.home.HomeViewModel
import com.example.haengsha.ui.theme.HaengshaBlue
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.CustomCircularProgressIndicator
import com.example.haengsha.ui.uiComponents.HomeDatePickerDialog
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.core.yearMonth
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun HomeScreen(
    homeApiViewModel: HomeApiViewModel,
    innerPadding: PaddingValues,
    userUiState: UserUiState,
    homeViewModel: HomeViewModel,
    boardViewModel: BoardViewModel,
    homeNavController: NavController
) {
    val dateFormatter = DateTimeFormatter.ofPattern("dd")
    val monthFormatter = DateTimeFormatter.ofPattern("MM")
    val fullFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val homeApiUiState = homeApiViewModel.homeApiUiState
    val selection = homeViewModel.selectedDate.collectAsState().value
    val currentMonth = selection.yearMonth
    val startDate = remember { currentMonth.minusMonths(100).atStartOfMonth() }
    val endDate = remember { currentMonth.plusMonths(100).atEndOfMonth() }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }

    if (homeViewModel.initialEnter) {
        LaunchedEffect(Unit) { homeApiViewModel.getEventByDate(selection) }
        homeViewModel.initialEnter = false
    } else {
        if (homeViewModel.selectionChanged) {
            LaunchedEffect(selection) { homeApiViewModel.getEventByDate(selection) }
        }
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
        HomeDatePickerDialog(
            onDateSelected = { selectedDate ->
                homeViewModel.updateSelectedDate(LocalDate.parse(selectedDate, fullFormatter))
                showDatePicker = false
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
            Box(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.Center)
            ) {
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    text = monthFormatter.format(selection) + "월" + " "
                            + dateFormatter.format(selection) + "일",
                    fontFamily = poppins,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Box(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(Alignment.CenterEnd)
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
                    homeViewModel.updateSelectedDate(clicked)
                }
            },
        )

        when (homeApiUiState) {
            is HomeApiUiState.Success -> {
                val academicCardDataList: List<EventCardData>? =
                    homeApiUiState.academicResponse?.map { it.toEventCardData() }

                val festivalCardDataList: List<EventCardData>? =
                    homeApiUiState.festivalResponse?.map { it.toEventCardData() }

                homeViewModel.updateEventItems(festivalCardDataList, academicCardDataList)
                homeViewModel.selectionChanged = false
                HomeScreenList(
                    homeViewModel = homeViewModel,
                    homeApiViewModel = homeApiViewModel,
                    homeNavController = homeNavController,
                    boardViewModel = boardViewModel,
                    userUiState = userUiState,
                )
            }

            is HomeApiUiState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    CustomCircularProgressIndicator()
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
                        text = "이벤트를 불러오는 중 문제가 발생했어요.\n\n다시 시도해주세요.",
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
                        text = "알 수 없는 문제가 발생했어요.\n\n메일로 문의해주세요.",
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
    val dateFormatter = DateTimeFormatter.ofPattern("dd")

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
                text = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN),
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

private fun EventResponse.toEventCardData(): EventCardData {
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
        id = id,
        organizer = author.nickname,
        eventTitle = title,
        startDate = startDate,
        endDate = endDate,
        likes = likeCount,
        favorites = favoriteCount,
        eventType = eventType,
        place = place,
        time = time ?: "wow",
        image = image ?: "image.jpg",
    )
}