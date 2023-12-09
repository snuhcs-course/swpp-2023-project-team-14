package com.example.haengsha.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.haengsha.model.dataSource.TabItem
import com.example.haengsha.model.route.HomeRoute
import com.example.haengsha.model.uiState.UserUiState
import com.example.haengsha.model.uiState.home.RecommendationApiUiState
import com.example.haengsha.model.viewModel.board.BoardViewModel
import com.example.haengsha.model.viewModel.home.HomeApiViewModel
import com.example.haengsha.model.viewModel.home.HomeViewModel
import com.example.haengsha.ui.theme.HaengshaBlue
import com.example.haengsha.ui.theme.md_theme_light_surface
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.CustomCircularProgressIndicator
import com.example.haengsha.ui.uiComponents.CustomHorizontalDivider
import com.example.haengsha.ui.uiComponents.HomeListItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenList(
    homeViewModel: HomeViewModel,
    homeApiViewModel: HomeApiViewModel,
    homeNavController: NavController,
    boardViewModel: BoardViewModel,
    userUiState: UserUiState
) {
    val homeContext = LocalContext.current
    val configuration = LocalConfiguration.current
    val deviceWidth = configuration.screenWidthDp.dp
    val deviceHeight = configuration.screenHeightDp.dp

    val academicItems by homeViewModel.academicItems.observeAsState()
    val festivalItems by homeViewModel.festivalItems.observeAsState()
    val recommendationApiUiState = homeApiViewModel.recommendationApiUiState
    var showRecommendDialog by remember { mutableStateOf(false) }
    val tabItems = listOf(
        academicItems?.let { TabItem(title = "Academic", eventCards = it) },
        festivalItems?.let { TabItem(title = "Festival", eventCards = it) }
    )
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { tabItems.size })

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
                    showRecommendDialog = true
                    if (userUiState.role != "Group" && homeViewModel.initialRecommendationState) {
                        homeApiViewModel.getRecommendationList(userUiState.token)
                        homeViewModel.initialRecommendationState = false
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
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            userScrollEnabled = false
        ) { index ->
            selectedTabIndex = if (index == 1) 1 else 0
            val itemsToDisplay = if (index == 0) festivalItems else academicItems

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
                            boardViewModel.updateEventId(item.id)
                            homeNavController.navigate(HomeRoute.HomeDetail.route)
                        }) {
                            HomeListItem(
                                organizer = item.organizer,
                                eventTitle = item.eventTitle,
                                image = item.image,
                                startDate = item.startDate,
                                endDate = item.endDate,
                                likes = item.likes,
                                eventType = item.eventType,
                                homeContext = homeContext
                            )
                        }
                    }
                }
            }
        }
    }

    if (showRecommendDialog) {
        var recommendationTitle by remember { mutableStateOf("\n") }

        Dialog(onDismissRequest = { showRecommendDialog = false }) {
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

                                        Box(modifier = Modifier.clickable {
                                            boardViewModel.updateEventId(event.id)
                                            showRecommendDialog = false
                                            homeNavController.navigate(HomeRoute.HomeDetail.route)
                                        }) {
                                            HomeListItem(
                                                organizer = event.author.nickname,
                                                eventTitle = event.title,
                                                image = event.image,
                                                startDate = startDate,
                                                endDate = endDate,
                                                likes = event.likeCount,
                                                eventType = if (event.isFestival) "Festival" else "Academic",
                                                homeContext = homeContext
                                            )
                                        }

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
                        .clickable { showRecommendDialog = false },
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
}

