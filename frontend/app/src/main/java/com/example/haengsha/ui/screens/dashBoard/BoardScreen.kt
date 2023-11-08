package com.example.haengsha.ui.screens.dashBoard

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.haengsha.model.route.BoardRoute
import com.example.haengsha.model.uiState.board.BoardListUiState
import com.example.haengsha.model.viewModel.board.BoardViewModel
import com.example.haengsha.ui.theme.HaengshaBlue
import com.example.haengsha.ui.theme.PlaceholderGrey
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.ConfirmOnlyDialog
import com.example.haengsha.ui.uiComponents.boardList
import com.example.haengsha.ui.uiComponents.searchBar
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun boardScreen(
    innerPadding: PaddingValues,
    boardViewModel: BoardViewModel,
    boardNavController: NavController
): Int {
    val boardListUiState = boardViewModel.boardListUiState
    val localDate = LocalDate.now().toString()
    var isLoginFailedDialogVisible by remember { mutableStateOf(false) }
    var eventId by rememberSaveable { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        boardViewModel.getBoardList(localDate)
    }

    when (boardListUiState) {
        is BoardListUiState.HttpError -> {
            isLoginFailedDialogVisible = true
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                ConfirmOnlyDialog(
                    onDismissRequest = { isLoginFailedDialogVisible = false },
                    onClick = { isLoginFailedDialogVisible = false },
                    text = "아무 행사도 등록되지 않았어요... :("
                )
            }
        }

        is BoardListUiState.NetworkError -> {
            isLoginFailedDialogVisible = true
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                ConfirmOnlyDialog(
                    onDismissRequest = { isLoginFailedDialogVisible = false },
                    onClick = { isLoginFailedDialogVisible = false },
                    text = "네트워크 연결을 확인해주세요."
                )
            }
        }

        is BoardListUiState.Error -> {
            isLoginFailedDialogVisible = true
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                ConfirmOnlyDialog(
                    onDismissRequest = { isLoginFailedDialogVisible = false },
                    onClick = { isLoginFailedDialogVisible = false },
                    text = "Exception occurs"
                )
            }
        }

        is BoardListUiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                //Toasty.info(boardContext, "로딩 중...", Toasty.LENGTH_SHORT, true).show()
            }
        }

        is BoardListUiState.BoardListResult -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
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
    return if (eventId != 0) eventId
    else 0
}