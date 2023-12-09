package com.example.haengsha.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.haengsha.R
import com.example.haengsha.model.uiState.UserUiState
import com.example.haengsha.model.uiState.home.RecommendationApiUiState
import com.example.haengsha.model.viewModel.home.HomeApiViewModel
import com.example.haengsha.model.viewModel.home.HomeViewModel
import com.example.haengsha.ui.theme.HaengshaBlue
import com.example.haengsha.ui.theme.LikePink
import com.example.haengsha.ui.theme.md_theme_light_surface
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.CustomCircularProgressIndicator
import com.example.haengsha.ui.uiComponents.CustomHorizontalDivider
import kotlinx.coroutines.launch
import java.time.LocalDate

data class TabItem(
    val title: String, val eventCards: List<EventCardData>
)

data class EventCardData(
    val organizer: String,
    val eventTitle: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val likes: Int,
    val favorites: Int,
    val eventType: String,
    val place: String = "",
    val time: String = "",
    val image: String = ""
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabView(
    homeViewModel: HomeViewModel,
    homeApiViewModel: HomeApiViewModel,
    userUiState: UserUiState
) {
    val homeContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val deviceWidth = configuration.screenWidthDp.dp
    val deviceHeight = configuration.screenHeightDp.dp

    val academicItems by homeViewModel.academicItems.observeAsState()
    val festivalItems by homeViewModel.festivalItems.observeAsState()
    val recommendationApiUiState = homeApiViewModel.recommendationApiUiState
    var showDialog by remember { mutableStateOf(false) }
    var selectedEvent: EventCardData? by remember { mutableStateOf(null) }
    var showEventCardPopup by remember { mutableStateOf(false) }
    val tabItems = listOf(academicItems?.let {
        TabItem(
            title = "Academic", eventCards = it
        )
    }, festivalItems?.let {
        TabItem(
            title = "Festival", eventCards = it
        )
    })
    // Remember the selected tab
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    // Pager state
    val pagerState = rememberPagerState(pageCount = { tabItems.size })
    val eventContext = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable {
                        selectedTabIndex = 0
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(selectedTabIndex)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Festival",
                    modifier = Modifier.drawBehind {
                        val strokeWidthPx = 2.dp.toPx()
                        val verticalOffset = size.height + 2.sp.toPx()
                        drawLine(
                            color = if (selectedTabIndex == 0) HaengshaBlue else Color.Transparent,
                            strokeWidth = strokeWidthPx,
                            start = Offset(0f, verticalOffset),
                            end = Offset(size.width, verticalOffset)
                        )
                    },
                    fontSize = 18.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable {
                        selectedTabIndex = 1
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(selectedTabIndex)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Academic",
                    modifier = Modifier.drawBehind {
                        val strokeWidthPx = 2.dp.toPx()
                        val verticalOffset = size.height + 2.sp.toPx()
                        drawLine(
                            color = if (selectedTabIndex == 1) HaengshaBlue else Color.Transparent,
                            strokeWidth = strokeWidthPx,
                            start = Offset(0f, verticalOffset),
                            end = Offset(size.width, verticalOffset)
                        )
                    },
                    fontSize = 18.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                )
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    showDialog = true
                    if (userUiState.role != "Group") {
                        homeApiViewModel.getRecommendationList(token = userUiState.token)
                    }
                },
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(HaengshaBlue),
                modifier = Modifier
                    .padding(16.dp)
                    .shadow(
                        elevation = 10.dp,
                        spotColor = Color(0x1A18274B),
                        ambientColor = Color(0x1A18274B)
                    )
                    .shadow(
                        elevation = 10.dp,
                        spotColor = Color(0x26000000),
                        ambientColor = Color(0x26000000)
                    )
                    .width(200.dp)
                    .height(50.dp)

            ) {
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    text = "행사 추천 받기",
                    fontSize = 18.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                )
            }
        }
        // Pager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            userScrollEnabled = false
        ) { index ->
            selectedTabIndex = if (index == 1) 1 else 0
            val itemsToDisplay = if (index == 0) festivalItems else academicItems
            // App content

            if (itemsToDisplay.isNullOrEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = if (index == 0) "오늘은 예정된 축제가 없어요!" else "오늘은 예정된 학술제가 없어요!",
                        fontSize = 20.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center,
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(itemsToDisplay) { item ->
                        Box(modifier = Modifier.clickable {
                            showEventCardPopup = true
                            selectedEvent = item
                        }) {
                            EventCard(
                                organizer = item.organizer,
                                eventTitle = item.eventTitle,
                                image = item.image,
                                startDate = item.startDate,
                                endDate = item.endDate,
                                likes = item.likes,
                                homeContext = homeContext
                            )
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        var recommendationTitle by remember { mutableStateOf("\n") }

        Dialog(onDismissRequest = { showDialog = false }) {
            Column(
                modifier = Modifier
                    .width(if (userUiState.role == "Group") 300.dp else deviceWidth)
                    .height(if (userUiState.role == "Group") 300.dp else deviceHeight - 130.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(md_theme_light_surface)
            ) {
                if (userUiState.role == "Group") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "단체 계정은 추천 기능을\n\n이용할 수 없어요",
                            fontSize = 18.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                        )
                    }
                } else {
                    Text(
                        text = recommendationTitle,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        fontSize = 16.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    CustomHorizontalDivider(color = Color.Black.copy(0.3f))
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(deviceHeight - 250.dp)
                            .background(Color.White),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        when (recommendationApiUiState) {
                            is RecommendationApiUiState.RecommendationListResult -> {
                                if (recommendationApiUiState.recommendationList.isEmpty()) {
                                    items(1) {
                                        Text(
                                            text = "오늘은 행사를 추천해드릴 수 없어요!\n\n\"${userUiState.nickname}\"님의\n관심사를 바탕으로 추천 행사가\n 매일 새벽에 업데이트됩니다.\n\n내일 다시 와주세요!",
                                            fontSize = 18.sp,
                                            fontFamily = poppins,
                                            fontWeight = FontWeight.Medium,
                                            textAlign = TextAlign.Center,
                                            lineHeight = 30.sp
                                        )
                                    }
                                } else {
                                    recommendationTitle =
                                        "\"${userUiState.nickname}\"님을 위한\n오늘의 맞춤 추천입니다!"

                                    items(recommendationApiUiState.recommendationList) { event ->
                                        val startDate =
                                            stringToDate(event.eventDurations[0].eventDay)
                                        var endDate = startDate
                                        if (event.eventDurations.size > 1) {
                                            endDate =
                                                stringToDate(event.eventDurations[event.eventDurations.size - 1].eventDay)
                                        }

                                        EventCard(
                                            organizer = event.author.nickname,
                                            eventTitle = event.title,
                                            image = event.image,
                                            startDate = startDate,
                                            endDate = endDate,
                                            likes = event.likeCount,
                                            homeContext = homeContext
                                        )
                                    }
                                }
                            }

                            is RecommendationApiUiState.Loading -> {
                                items(1) {
                                    CustomCircularProgressIndicator()
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Text(
                                        text = "추천 행사 불러오는 중...",
                                        fontFamily = poppins,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = HaengshaBlue
                                    )
                                }
                            }

                            is RecommendationApiUiState.HttpError -> {
                                items(1) {
                                    Text(
                                        text = "추천 행사를 불러오는 중\n문제가 발생했어요!\n\n다시 시도해주세요.",
                                        fontFamily = poppins,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Black,
                                        lineHeight = 25.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }

                            is RecommendationApiUiState.NetworkError -> {
                                items(1) {
                                    Text(
                                        text = "인터넷 연결을 확인해주세요.",
                                        fontFamily = poppins,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = HaengshaBlue
                                    )
                                }
                            }

                            is RecommendationApiUiState.Error -> {
                                items(1) {
                                    Text(
                                        text = "알 수 없는 문제가 발생했어요!\n\n메일로 문의해주세요.",
                                        fontFamily = poppins,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = HaengshaBlue
                                    )
                                }
                            }
                        }
                    }
                }
                CustomHorizontalDivider(color = Color.Black.copy(0.3f))
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { showDialog = false },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "닫기",
                        fontSize = 16.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        textDecoration = TextDecoration.Underline,
                    )
                }
            }
        }
    }

    if (showEventCardPopup) {
        AlertDialog(
            onDismissRequest = {
                // Close the popup when clicked outside
                showEventCardPopup = false
            },
            title = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(
                        8.dp, Alignment.CenterVertically
                    ),
                    horizontalAlignment = Alignment.Start,
                ) {

                    Text(
                        text = selectedEvent?.eventTitle ?: "N/A",
                        fontSize = 18.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_bold)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF343A40),
                    )

                    Row {

                        Text(
                            text = selectedEvent?.eventType ?: "N/A",
                            fontSize = 11.sp,
                            lineHeight = 17.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF868E96),
                        )
                        Text(
                            text = " | ",
                            fontSize = 11.sp,
                            lineHeight = 17.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF868E96),
                        )

                        Text(
                            text = selectedEvent?.organizer ?: "N/A",
                            fontSize = 11.sp,
                            lineHeight = 17.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF868E96),
                        )
                    }

                }
            },
            text = {
                // Use a Column to ensure proper spacing of text
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp), // Adjust the padding as needed
                        contentAlignment = Alignment.Center

                    ) {
                        /*Image(
                            painter = painterResource(id = R.drawable.nudge_image),
                            contentDescription = "image description",
                            contentScale = ContentScale.Crop, // Maintain aspect ratio
                            modifier = Modifier.fillMaxWidth()
                        )*/
                        if (selectedEvent?.image?.isNotEmpty() == true) {
                            AsyncImage(
                                model = ImageRequest.Builder(context = eventContext)
                                    .data(selectedEvent?.image)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "poster",
                                modifier = Modifier.size(360.dp)
                            )
                        }
                    }

                    Column {
                        val startDateText = selectedEvent?.startDate?.let { formatDateToMMDD(it) }
                        val endDateText = selectedEvent?.endDate?.let { formatDateToMMDD(it) }

                        Text(
                            text = "주최 | " + selectedEvent?.organizer,
                            fontSize = 12.sp,
                            lineHeight = 19.56.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Center,
                        )

                        Text(
                            text = "일자 | $startDateText - $endDateText",
                            fontSize = 12.sp,
                            lineHeight = 19.56.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Center,
                        )

                        Text(
                            text = "장소 | " + selectedEvent?.place,
                            fontSize = 12.sp,
                            lineHeight = 19.56.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Center,
                        )

                        Text(
                            text = "시간 | " + selectedEvent?.time,
                            fontSize = 12.sp,
                            lineHeight = 19.56.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Center,
                        )

                        Row(modifier = Modifier.padding(top = 10.dp)) {
                            Image(
                                painter = painterResource(id = R.drawable.like_fill_icon),
                                contentDescription = "image description",
                            )

                            Text(
                                text = selectedEvent?.likes.toString(),
                                fontSize = 10.sp,
                                fontFamily = poppins,
                                fontWeight = FontWeight(500),
                                color = LikePink,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp)
                ) {
                    Button(
                        onClick = {
                            showEventCardPopup = false
                        },
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .padding(0.dp), // 패딩 제거

                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color.White),

                        ) {
                        Text(
                            text = "닫기",
                            fontSize = 13.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Center,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier.padding(0.dp) // 텍스트 주위의 패딩 제거
                        )
                    }
                }
            },
            modifier = Modifier
                .shadow(
                    elevation = 10.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000)
                )
                .width(300.dp)
                .height(550.dp)
                .background(color = Color(0xFFFFFFFF)),
            containerColor = Color(0xFFFFFFFF)
        )
    }
}

