package com.example.haengsha.ui.screens.dashBoard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.haengsha.R
import com.example.haengsha.model.route.BoardRoute
import com.example.haengsha.model.uiState.UserUiState
import com.example.haengsha.model.uiState.board.BoardListUiState
import com.example.haengsha.model.viewModel.board.BoardApiViewModel
import com.example.haengsha.ui.theme.ButtonBlue
import com.example.haengsha.ui.theme.HaengshaBlue
import com.example.haengsha.ui.theme.PlaceholderGrey
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.boardList
import com.example.haengsha.ui.uiComponents.searchBar
import es.dmoral.toasty.Toasty

@Composable
fun boardScreen(
    innerPadding: PaddingValues,
    boardApiViewModel: BoardApiViewModel,
    boardNavController: NavController,
    userUiState: UserUiState
): Int {
    val boardContext = LocalContext.current
    val boardListUiState = boardApiViewModel.boardListUiState
    var eventId by rememberSaveable { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            searchBar()
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .size(width = 110.dp, height = 25.dp)
                        .border(
                            width = 1.dp,
                            color = HaengshaBlue,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable { /*TODO 필터 모달*/ },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "필터 : 선택 안 함",
                        fontFamily = poppins,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = HaengshaBlue
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
            }
            Spacer(modifier = Modifier.height(15.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(PlaceholderGrey)
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                when (boardListUiState) {
                    is BoardListUiState.HttpError -> {
                        items(1) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "등록된 행사가 없어요 :(",
                                    fontFamily = poppins,
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    is BoardListUiState.NetworkError -> {
                        items(1) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                            ) {
                                Toasty.error(boardContext, "네트워크 연결을 확인해주세요", Toasty.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }

                    is BoardListUiState.Error -> {
                        items(1) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                            ) {
                                Toasty.error(
                                    boardContext,
                                    "알 수 없는 에러가 발생했어요 :( 메일로 제보해주세요!",
                                    Toasty.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    is BoardListUiState.Loading -> {
                        // Do nothing
                    }

                    is BoardListUiState.BoardListResult -> {
                        items(boardListUiState.boardList) { event ->
                            Box(modifier = Modifier.clickable {
                                eventId = event.id
                                boardNavController.navigate(BoardRoute.BoardDetail.route)
                            }) {
                                boardList(
                                    isFavorite = false,
                                    event = event
                                )
                            }
                            HorizontalDivider(
                                modifier = Modifier.fillMaxWidth(),
                                thickness = 1.dp,
                                color = PlaceholderGrey
                            )
                        }
                    }
                }
            }
        }
        if (userUiState.role == "Group") {
            Box(modifier = Modifier.offset(330.dp, 600.dp)) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(ButtonBlue, RoundedCornerShape(30.dp))
                        .clickable(onClick = { boardNavController.navigate(BoardRoute.BoardPost.route) }),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.write_festival_icon),
                        contentDescription = "event post Button",
                        tint = Color.White
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit /* TODO key 변경 */) {
        // TODO 검색 함수 호출
    }

    return if (eventId != 0) eventId
    else 0
}