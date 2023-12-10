package com.example.haengsha.ui.screens.board

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
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
import com.example.haengsha.model.viewModel.board.BoardViewModel
import com.example.haengsha.ui.theme.ButtonBlue
import com.example.haengsha.ui.theme.HaengshaBlue
import com.example.haengsha.ui.theme.PlaceholderGrey
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.BoardDatePickerDialog
import com.example.haengsha.ui.uiComponents.CustomCircularProgressIndicator
import com.example.haengsha.ui.uiComponents.CustomHorizontalDivider
import com.example.haengsha.ui.uiComponents.FilterDialog
import com.example.haengsha.ui.uiComponents.SearchBar
import com.example.haengsha.ui.uiComponents.boardListItem
import es.dmoral.toasty.Toasty

@Composable
fun BoardScreen(
    innerPadding: PaddingValues,
    boardViewModel: BoardViewModel,
    boardApiViewModel: BoardApiViewModel,
    boardNavController: NavController,
    userUiState: UserUiState,
    isTest: Boolean
) {
    val boardUiState = boardViewModel.boardUiState.collectAsState()
    val boardListUiState = boardApiViewModel.boardListUiState
    val boardContext = LocalContext.current

    val isFestival = boardUiState.value.isFestival
    val startDate = boardUiState.value.startDate
    val endDate = boardUiState.value.endDate

    var filterModal by remember { mutableStateOf(false) }
    var startDatePick by rememberSaveable { mutableStateOf(false) }
    var endDatePick by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val configuration = LocalConfiguration.current
    val deviceWidth = configuration.screenWidthDp.dp
    val deviceHeight = configuration.screenHeightDp.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                })
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            SearchBar(
                boardViewModel = boardViewModel,
                keyword = boardUiState.value.keyword,
                keyboardActions = { focusManager.clearFocus() },
                context = boardContext
            ) { boardApiViewModel.searchEvent(it) }
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(25.dp)
                        .border(
                            width = 1.dp,
                            color = HaengshaBlue,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable { filterModal = true }
                        .padding(horizontal = 8.dp)
                        .testTag("filter"),
                    contentAlignment = Alignment.Center
                ) {
                    val filterDateText = if (startDate.isEmpty() && endDate.isEmpty()) {
                        ""
                    } else "$startDate ~ $endDate"
                    val filterCategoryText = when (isFestival) {
                        0 -> "학술"
                        1 -> "공연"
                        else -> "공연, 학술"
                    }
                    val filterText =
                        if (filterDateText.isEmpty()) filterCategoryText else "$filterDateText, $filterCategoryText"
                    Text(
                        modifier = Modifier.padding(top = 2.dp),
                        text = "필터 : $filterText",
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                when (boardListUiState) {
                    is BoardListUiState.HttpError -> {
                        Toasty.error(
                            boardContext,
                            "행사 검색에 문제가 발생했어요!\n다시 시도해주세요",
                            Toasty.LENGTH_SHORT
                        )
                            .show()
                        boardApiViewModel.resetBoardListUiStateToDefault()
                    }

                    is BoardListUiState.NetworkError -> {
                        Toasty.error(boardContext, "네트워크 연결을 확인해주세요", Toasty.LENGTH_SHORT)
                            .show()
                        boardApiViewModel.resetBoardListUiStateToDefault()
                    }

                    is BoardListUiState.Error -> {
                        Toasty.error(
                            boardContext,
                            "알 수 없는 에러가 발생했어요 :( 메일로 제보해주세요!",
                            Toasty.LENGTH_SHORT
                        ).show()
                        boardApiViewModel.resetBoardListUiStateToDefault()
                    }

                    is BoardListUiState.Default -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        )
                    }

                    is BoardListUiState.Loading -> {
                        if (boardUiState.value.initialState) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "찾고 싶은 행사를 검색해보세요!",
                                    fontFamily = poppins,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                Text(
                                    text = "(단체 계정은 행사를 등록할 수도 있습니다)",
                                    fontFamily = poppins,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center
                                )
                                if (isTest) {
                                    Column(modifier = Modifier.clickable {
                                        boardNavController.navigate(BoardRoute.BoardDetail.route)
                                    }) { Text("test") }
                                }
                            }
                        } else {
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
                                    text = "행사 찾아보는 중...",
                                    fontFamily = poppins,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = HaengshaBlue
                                )
                            }
                        }
                    }

                    is BoardListUiState.BoardListResult -> {
                        if (boardListUiState.boardList.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "찾는 행사가 없어요 :(",
                                    fontFamily = poppins,
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        } else {
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                items(boardListUiState.boardList) { event ->
                                    Row(modifier = Modifier.clickable {
                                        boardViewModel.updateEventId(event.id)
                                        boardNavController.navigate(BoardRoute.BoardDetail.route)
                                    }) {
                                        boardListItem(
                                            isFavorite = false,
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

        if (userUiState.role == "Group" || isTest) {
            Box(modifier = Modifier.offset(deviceWidth - 80.dp, deviceHeight - 190.dp)) {
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

        if (filterModal) {
            FilterDialog(
                boardViewModel = boardViewModel,
                boardUiState = boardUiState.value,
                context = boardContext,
                onSubmit = { boardApiViewModel.searchEvent(it) },
                onDismissRequest = { filterModal = false },
                onStartDatePick = { startDatePick = true },
                onEndDatePick = { endDatePick = true }
            )
        }

        if (startDatePick) {
            BoardDatePickerDialog(
                onDismissRequest = { startDatePick = false },
                boardViewModel = boardViewModel,
                type = "startDate",
                usage = "filter"
            )
        }

        if (endDatePick) {
            BoardDatePickerDialog(
                onDismissRequest = { endDatePick = false },
                boardViewModel = boardViewModel,
                type = "endDate",
                usage = "filter"
            )
        }
    }
}