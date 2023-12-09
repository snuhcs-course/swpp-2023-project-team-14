package com.example.haengsha.ui.screens.favorite

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.haengsha.R
import com.example.haengsha.model.route.FavoriteRoute
import com.example.haengsha.model.uiState.UserUiState
import com.example.haengsha.model.uiState.board.BoardFavoriteUiState
import com.example.haengsha.model.viewModel.board.BoardApiViewModel
import com.example.haengsha.model.viewModel.board.BoardViewModel
import com.example.haengsha.ui.theme.HaengshaBlue
import com.example.haengsha.ui.theme.PlaceholderGrey
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.CustomCircularProgressIndicator
import com.example.haengsha.ui.uiComponents.CustomHorizontalDivider
import com.example.haengsha.ui.uiComponents.boardListItem
import es.dmoral.toasty.Toasty

@Composable
fun FavoriteScreen(
    innerPadding: PaddingValues,
    boardApiViewModel: BoardApiViewModel,
    boardViewModel: BoardViewModel,
    favoriteNavController: NavController,
    userUiState: UserUiState,
    isTest: Boolean
) {
    val boardContext = LocalContext.current
    val boardFavoriteUiState = boardApiViewModel.boardFavoriteUiState

    if (isTest) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .clickable {
                    favoriteNavController.navigate(FavoriteRoute.FavoriteDetail.route)
                }
        ) { Text("test") }
    } else if (userUiState.role == "Group") {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    modifier = Modifier.size(300.dp),
                    painter = painterResource(R.drawable.nudge_image),
                    contentDescription = "nudge image"
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "단체 계정은 즐겨찾기를 할 수 없어요",
                    fontFamily = poppins,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 35.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {
        LaunchedEffect(Unit) { boardApiViewModel.getFavoriteBoardList(userUiState.token) }

        when (boardFavoriteUiState) {
            is BoardFavoriteUiState.HttpError -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            modifier = Modifier.size(300.dp),
                            painter = painterResource(R.drawable.nudge_image),
                            contentDescription = "nudge image"
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "관심 있는 행사를 즐겨찾기 해보세요!",
                            fontFamily = poppins,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            lineHeight = 35.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            is BoardFavoriteUiState.NetworkError -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Toasty.error(boardContext, "네트워크 연결을 확인해주세요", Toasty.LENGTH_SHORT).show()
                }
            }

            is BoardFavoriteUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Toasty.error(
                        boardContext,
                        "알 수 없는 에러가 발생했어요 :( 메일로 제보해주세요!",
                        Toasty.LENGTH_SHORT
                    )
                        .show()
                }
            }

            is BoardFavoriteUiState.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CustomCircularProgressIndicator()
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "즐겨찾기 목록 불러오는 중...",
                        fontFamily = poppins,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = HaengshaBlue
                    )
                }
            }

            is BoardFavoriteUiState.BoardListResult -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(PlaceholderGrey)
                        )
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(boardFavoriteUiState.boardList) { event ->
                                Box(modifier = Modifier.clickable {
                                    boardViewModel.updateEventId(event.id)
                                    favoriteNavController.navigate(FavoriteRoute.FavoriteDetail.route)
                                }) {
                                    boardListItem(
                                        isFavorite = true,
                                        event = event
                                    )
                                }
                                CustomHorizontalDivider(color = PlaceholderGrey)
                            }
                        }
                    }
                }
            }
        }
    }
}