package com.example.haengsha.ui.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.layout.ContentScale
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
    val image: String = ""  // Image URL 변경 필요 (임시로 nudge_image 사용함)
)


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabView(sharedViewModel: SharedViewModel, selectedDate: LocalDate, selectedTabIndex: Int) {
    var itemsToDisplay: List<EventCardData>?
    val academicItems by sharedViewModel.academicItems.observeAsState()
    val festivalItems by sharedViewModel.festivalItems.observeAsState()
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
    var pagerState = rememberPagerState {
        tabItems.size
    }

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
            tabItems.forEachIndexed { index, item ->
                Tab(
                    selected = (index == selectedTabIndex),
                    onClick = {
                        selectedTabIndex = index
                        // Change the page when the tab is changed
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(selectedTabIndex)
                        }
                    },
                    text = {
                        if (index == 0) Text(text = "Academic")
                        else Text(text = "Festival")
                    },
                )
            }
        }

        // Pager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),

            ) { index ->

            // App content
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    // Button at the top of the HorizontalPager
                    Button(
                        onClick = {
                            showDialog = true
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
                if (index == 1) {
                    itemsToDisplay = festivalItems
                    selectedTabIndex = 1
                } else {
                    itemsToDisplay = academicItems
                    selectedTabIndex = 0
                }
                //val itemsToDisplay = if (index == 1) festivalItems else academicItems
                items(itemsToDisplay.orEmpty()) { eventCardData ->
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

    if (showDialog) {
        // Display the AlertDialog with "Here is popup"
        AlertDialog(
            onDismissRequest = {
                // Close the dialog when clicked outside
                showDialog = false
            },
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
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(1) {
                        EventCard( // Demo 용으로 필요하면 추가
                            organizer = "수리과학부",
                            eventTitle = "수리과학부 강연",
                            startDate = LocalDate.now().plusDays(5),
                            endDate = LocalDate.now().plusDays(5),
                            likes = 28
                        )
                        EventCard(
                            organizer = "데이터사이언스 대학원",
                            eventTitle = "인공지능의 투명성: 소셜 봇 대응의 최선의 방법",
                            startDate = LocalDate.now().plusDays(1),
                            endDate = LocalDate.now().plusDays(1),
                            likes = 52
                        )
                        EventCard(
                            organizer = "대학생문화원",
                            eventTitle = "대학생문화원 자살예방교육",
                            startDate = LocalDate.now().plusDays(3),
                            endDate = LocalDate.now().plusDays(15),
                            likes = 11
                        )
                        EventCard(
                            organizer = "통일평화연구원",
                            eventTitle = "통일평화연구원 통일학포럼",
                            startDate = LocalDate.now(),
                            endDate = LocalDate.now().plusDays(1),
                            likes = 173
                        )
                        EventCard(
                            organizer = "경영학과",
                            eventTitle = "삼성 파운드리의 현재와 미래",
                            startDate = LocalDate.now().plusDays(2),
                            endDate = LocalDate.now().plusDays(2),
                            likes = 81
                        )
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
                .width(500.dp)
                .height(550.dp)
                .background(color = Color(0xFFFFFFFF)),
            containerColor = Color(0xFFFFFFFF)
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
                        if(selectedEvent?.image?.isNotEmpty() == true) {
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

