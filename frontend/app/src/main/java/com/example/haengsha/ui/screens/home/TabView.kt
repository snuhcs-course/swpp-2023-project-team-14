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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.haengsha.R
import com.example.haengsha.model.uiState.UserUiState
import com.example.haengsha.model.uiState.home.RecommendationApiUiState
import com.example.haengsha.model.viewModel.home.HomeApiViewModel
import com.example.haengsha.model.viewModel.home.HomeViewModel
import com.example.haengsha.ui.theme.HaengshaBlue
import com.example.haengsha.ui.theme.LikePink
import com.example.haengsha.ui.theme.md_theme_light_onSurfaceVariant
import com.example.haengsha.ui.theme.poppins
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

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TabView(
    homeViewModel: HomeViewModel,
    homeApiViewModel: HomeApiViewModel,
    userUiState: UserUiState
) {
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

    // Pager state
    val pagerState = rememberPagerState(pageCount = { tabItems.size })

    // Coroutine scope
    val coroutineScope = rememberCoroutineScope()

    val eventContext = LocalContext.current


    Column(modifier = Modifier.fillMaxSize()) {
        // Tab row
        PrimaryTabRow(
            selectedTabIndex = selectedTabIndex,
            contentColor = md_theme_light_onSurfaceVariant,
            indicator = { tabPositions ->
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = HaengshaBlue
                )
            }) {
            // Tab items
            tabItems.forEachIndexed { index, _ ->
                Tab(
                    selected = (index == selectedTabIndex),
                    onClick = {
                        selectedTabIndex = index
                        // Change the page when the tab is changed
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        if (index == 0) Text(text = "Academic")
                        else Text(text = "Festival")
                    },
                )
            }
        }
// Button at the top of the HorizontalPager
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
        ) {
            Button(
                onClick = {
                    showDialog = true
                    homeApiViewModel.getRecommendationList(token = userUiState.token)
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
                Text(text = "맞춤 추천 받기")
            }
        }
        // Pager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),

            ) { index ->
            selectedTabIndex = if (index == 1) {
                1
            } else {
                0
            }
            val itemsToDisplay = if (index == 1) festivalItems else academicItems
            // App content

            if (itemsToDisplay.isNullOrEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "오늘은 예정된 이벤트가 없어요!",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            } else {
                val listScrollState = rememberScrollState()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(listScrollState),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    for (i in itemsToDisplay.indices) {
                        val eventCardData = itemsToDisplay[i]
                        Box(modifier = Modifier.clickable {
                            showEventCardPopup = true
                            selectedEvent = eventCardData
                        }) {
                            EventCard(
                                organizer = eventCardData.organizer,
                                eventTitle = eventCardData.eventTitle,
                                startDate = eventCardData.startDate,
                                endDate = eventCardData.endDate,
                                likes = eventCardData.likes,
                            )
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        val recommendScrollState = rememberScrollState()

        // Display the AlertDialog with "Here is popup"
        AlertDialog(
            onDismissRequest = {
                // Close the dialog when clicked outside
                showDialog = false
            },
            modifier = Modifier
                .shadow(
                    elevation = 10.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000)
                )
                .width(500.dp)
                .height(550.dp)
                .background(color = Color(0xFFFFFFFF)),
            containerColor = Color(0xFFFFFFFF),
            title = {
                Text(
                    text = "당신을 위한 오늘의 맞춤 추천입니다!",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center,
                    )
                )
            }, text = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(recommendScrollState),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    when (recommendationApiUiState) {
                        is RecommendationApiUiState.RecommendationListResult -> {
                            for (i in recommendationApiUiState.recommendationList.indices) {
                                val recommendationItem =
                                    recommendationApiUiState.recommendationList[i]
                                val startDate =
                                    stringToDate(recommendationItem.eventDurations[0].eventDay)
                                var endDate = startDate
                                if (recommendationItem.eventDurations.size > 1) {
                                    endDate =
                                        stringToDate(recommendationItem.eventDurations[recommendationItem.eventDurations.size - 1].eventDay)
                                }

                                EventCard(
                                    organizer = recommendationItem.author.nickname,
                                    eventTitle = recommendationItem.title,
                                    startDate = startDate,
                                    endDate = endDate,
                                    likes = recommendationItem.likeCount,
                                )
                            }
                        }

                        is RecommendationApiUiState.Loading -> {
                            CircularProgressIndicator(
                                color = HaengshaBlue,
                                strokeWidth = 3.dp
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = "추천 행사 불러오는 중...",
                                fontFamily = poppins,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = HaengshaBlue
                            )
                        }

                        is RecommendationApiUiState.HttpError -> {
                            Text(
                                text = "이벤트를 불러오는 중 문제가 발생했어요!\n\n다시 시도해주세요.",
                                fontFamily = poppins,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = HaengshaBlue
                            )
                        }

                        is RecommendationApiUiState.NetworkError -> {
                            Text(
                                text = "인터넷 연결을 확인해주세요.",
                                fontFamily = poppins,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = HaengshaBlue
                            )
                        }

                        is RecommendationApiUiState.Error -> {
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
                            showDialog = false
                        },
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .padding(0.dp), // 패딩 제거

                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(Color.White),

                        ) {
                        Text(
                            text = "닫기",
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = poppins,
                                fontWeight = FontWeight(500),
                                color = Color(0xFF000000),
                                textAlign = TextAlign.Center,
                                textDecoration = TextDecoration.Underline,
                            ), modifier = Modifier.padding(0.dp) // 텍스트 주위의 패딩 제거
                        )
                    }
                }
            }
        )
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
                        text = selectedEvent?.eventTitle ?: "N/A", style = TextStyle(
                            fontSize = 18.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_bold)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF343A40),
                        )
                    )

                    Row {

                        Text(
                            text = selectedEvent?.eventType ?: "N/A", style = TextStyle(
                                fontSize = 11.sp,
                                lineHeight = 17.sp,
                                fontFamily = poppins,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF868E96),
                            )
                        )
                        Text(
                            text = " | ", style = TextStyle(
                                fontSize = 11.sp,
                                lineHeight = 17.sp,
                                fontFamily = poppins,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF868E96),
                            )
                        )

                        Text(
                            text = selectedEvent?.organizer ?: "N/A", style = TextStyle(
                                fontSize = 11.sp,
                                lineHeight = 17.sp,
                                fontFamily = poppins,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF868E96),
                            )
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
                            text = "주최 | " + selectedEvent?.organizer, style = TextStyle(
                                fontSize = 12.sp,
                                lineHeight = 19.56.sp,
                                fontFamily = poppins,
                                fontWeight = FontWeight(500),
                                color = Color(0xFF000000),
                                textAlign = TextAlign.Center,
                            )
                        )

                        Text(
                            text = "일자 | $startDateText - $endDateText", style = TextStyle(
                                fontSize = 12.sp,
                                lineHeight = 19.56.sp,
                                fontFamily = poppins,
                                fontWeight = FontWeight(500),
                                color = Color(0xFF000000),
                                textAlign = TextAlign.Center,
                            )
                        )

                        Text(
                            text = "장소 | " + selectedEvent?.place, style = TextStyle(
                                fontSize = 12.sp,
                                lineHeight = 19.56.sp,
                                fontFamily = poppins,
                                fontWeight = FontWeight(500),
                                color = Color(0xFF000000),
                                textAlign = TextAlign.Center,
                            )
                        )

                        Text(
                            text = "시간 | " + selectedEvent?.time, style = TextStyle(
                                fontSize = 12.sp,
                                lineHeight = 19.56.sp,
                                fontFamily = poppins,
                                fontWeight = FontWeight(500),
                                color = Color(0xFF000000),
                                textAlign = TextAlign.Center,
                            )
                        )

                        Row(modifier = Modifier.padding(top = 10.dp)) {
                            Image(
                                painter = painterResource(id = R.drawable.like_fill_icon),
                                contentDescription = "image description",
                            )

                            Text(
                                text = selectedEvent?.likes.toString(), style = TextStyle(
                                    fontSize = 10.sp,
                                    fontFamily = poppins,
                                    fontWeight = FontWeight(500),
                                    color = LikePink,
                                    textAlign = TextAlign.Center,
                                )
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
                            text = "닫기", style = TextStyle(
                                fontSize = 13.sp,
                                fontFamily = poppins,
                                fontWeight = FontWeight(500),
                                color = Color(0xFF000000),
                                textAlign = TextAlign.Center,
                                textDecoration = TextDecoration.Underline,
                            ), modifier = Modifier.padding(0.dp) // 텍스트 주위의 패딩 제거
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

