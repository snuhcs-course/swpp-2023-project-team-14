package com.example.haengsha.ui.uiComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.haengsha.R
import com.example.haengsha.model.network.dataModel.SearchRequest
import com.example.haengsha.model.viewModel.board.BoardViewModel
import com.example.haengsha.ui.theme.BackgroundGrey
import com.example.haengsha.ui.theme.ButtonBlue
import com.example.haengsha.ui.theme.HaengshaBlue
import com.example.haengsha.ui.theme.HaengshaGrey
import com.example.haengsha.ui.theme.md_theme_light_onPrimary
import com.example.haengsha.ui.theme.md_theme_light_tertiary
import com.example.haengsha.ui.theme.poppins

@Composable
fun ConfirmOnlyDialog(
    onDismissRequest: () -> Unit,
    onClick: () -> Unit,
    text: String
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .size(width = 320.dp, height = 160.dp)
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(15.dp))
                    .background(color = md_theme_light_onPrimary),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = text,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = md_theme_light_tertiary
                )
                Spacer(modifier = Modifier.height(21.dp))
                Box(
                    modifier = Modifier
                        .size(width = 320.dp, height = 1.dp)
                        .background(color = BackgroundGrey)
                )
                Spacer(modifier = Modifier.height(21.dp))
                ModalConfirmButton(
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
fun ConfirmDialog(
    onDismissRequest: () -> Unit,
    onClick: () -> Unit,
    text: String
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .size(width = 320.dp, height = 160.dp)
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(15.dp))
                    .background(color = md_theme_light_onPrimary),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = text,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = md_theme_light_tertiary
                )
                Spacer(modifier = Modifier.height(21.dp))
                Box(
                    modifier = Modifier
                        .size(width = 320.dp, height = 1.dp)
                        .background(color = BackgroundGrey)
                )
                Spacer(modifier = Modifier.height(21.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ModalCancelButton(
                        onClick = onDismissRequest
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    ModalConfirmButton(
                        onClick = onClick
                    )
                }
            }
        }
    }
}

@Composable
fun FilterDialog(
    boardViewModel: BoardViewModel,
    onSubmit: (SearchRequest) -> Unit,
    onDismissRequest: () -> Unit
) {
    var isFestival by remember { mutableIntStateOf(boardViewModel.uiState.value.isFestival) } // 0이면 학술, 1이면 축제, 2면 둘 다
    var startDatePick by remember { mutableStateOf(false) }
    var endDatePick by remember { mutableStateOf(false) }
    var startDate by remember { mutableStateOf(boardViewModel.uiState.value.startDate) }
    var endDate by remember { mutableStateOf(boardViewModel.uiState.value.endDate) }

    // TODO 필터링 성공하면 onDismissRequest 호출

    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .width(300.dp)
                    .wrapContentHeight()
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(15.dp))
                    .background(color = md_theme_light_onPrimary),
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    text = "필터 설정",
                    fontFamily = poppins,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = HaengshaGrey
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    text = "시작 일자",
                    fontFamily = poppins,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(35.dp)
                            .border(
                                width = 1.dp,
                                color = HaengshaBlue,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .clickable {
                                // TODO 데이트 피커
                            }
                            .padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            // TODO 필터 텍스트
                            text = "시작일을 선택해주세요",
                            fontFamily = poppins,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = HaengshaBlue
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.calendar_icon),
                            contentDescription = "Calendar icon",
                            modifier = Modifier.size(width = 22.dp, height = 20.dp),
                            tint = HaengshaBlue
                        )
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    text = "종료 일자",
                    fontFamily = poppins,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(35.dp)
                            .border(
                                width = 1.dp,
                                color = HaengshaBlue,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .clickable {
                                // TODO 데이트 피커
                            }
                            .padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            // TODO 필터 텍스트
                            text = "종료일을 선택해주세요",
                            fontFamily = poppins,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = HaengshaBlue
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.calendar_icon),
                            contentDescription = "Calendar icon",
                            modifier = Modifier.size(width = 22.dp, height = 20.dp),
                            tint = HaengshaBlue
                        )
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    text = "행사 종류",
                    fontFamily = poppins,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.clickable {
                            // TODO 행사 종류 선택, 박스 색깔 변경
                        }) {
                            CheckBox(color = ButtonBlue, size = 22)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "공연",
                            fontFamily = poppins,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.clickable {
                            // TODO 행사 종류 선택, 박스 색깔 변경
                        }) {
                            CheckBox(color = Color.Transparent, size = 22)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "학술",
                            fontFamily = poppins,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = HaengshaGrey
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ModalCancelButton(
                        onClick = onDismissRequest
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    ModalConfirmButton(
                        onClick = {
                            boardViewModel.updateIsFestival(isFestival)
                            boardViewModel.updateStartDate(startDate)
                            boardViewModel.updateEndDate(endDate)

                            onSubmit(
                                SearchRequest(
                                    boardViewModel.uiState.value.token,
                                    boardViewModel.uiState.value.keyword,
                                    isFestival,
                                    startDate,
                                    endDate
                                )
                            )
                        }
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessageDialogPreview() {
    FilterDialog(
        boardViewModel = viewModel(),
        onSubmit = { (_) -> },
        onDismissRequest = { },
    )
}