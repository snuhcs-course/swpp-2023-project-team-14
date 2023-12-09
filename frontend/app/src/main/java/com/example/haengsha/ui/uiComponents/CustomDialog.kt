package com.example.haengsha.ui.uiComponents

import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.haengsha.R
import com.example.haengsha.model.network.dataModel.SearchRequest
import com.example.haengsha.model.uiState.board.BoardUiState
import com.example.haengsha.model.viewModel.board.BoardViewModel
import com.example.haengsha.ui.theme.BackgroundGrey
import com.example.haengsha.ui.theme.ButtonBlue
import com.example.haengsha.ui.theme.FieldStrokeRed
import com.example.haengsha.ui.theme.HaengshaBlue
import com.example.haengsha.ui.theme.HaengshaGrey
import com.example.haengsha.ui.theme.md_theme_light_onPrimary
import com.example.haengsha.ui.theme.md_theme_light_tertiary
import com.example.haengsha.ui.theme.poppins
import es.dmoral.toasty.Toasty
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.Locale

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
                    .width(320.dp)
                    .heightIn(min = 160.dp)
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
                        .padding(start = 20.dp, end = 20.dp, bottom = 20.dp),
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
    boardUiState: BoardUiState,
    context: Context,
    onSubmit: (SearchRequest) -> Unit,
    onDismissRequest: () -> Unit,
    onStartDatePick: () -> Unit,
    onEndDatePick: () -> Unit
) {
    val isFestival = boardUiState.isFestival

    if (boardViewModel.input.value != boardUiState.keyword) {
        boardViewModel.setIsSearchedFalse()
    }

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
                Spacer(modifier = Modifier.height(25.dp))
                Row(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "필터 설정",
                        fontFamily = poppins,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .border(1.dp, FieldStrokeRed.copy(0.7f), RoundedCornerShape(6.dp))
                            .clickable { boardViewModel.resetFilter() }
                            .padding(start = 5.dp, end = 5.dp, top = 2.dp)
                    ) {
                        Text(
                            text = "필터 초기화",
                            fontFamily = poppins,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = FieldStrokeRed.copy(0.7f)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                CustomHorizontalDivider(color = HaengshaGrey)
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
                                onStartDatePick()
                            }
                            .padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = boardUiState.startDate.ifEmpty { "시작일을 선택해주세요" },
                            modifier = Modifier.padding(top = 3.dp),
                            fontFamily = poppins,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = HaengshaBlue
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.calendar_icon),
                            contentDescription = "start date Calendar icon",
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
                                onEndDatePick()
                            }
                            .padding(horizontal = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = boardUiState.endDate.ifEmpty { "종료일을 선택해주세요" },
                            modifier = Modifier.padding(top = 3.dp),
                            fontFamily = poppins,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = HaengshaBlue
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.calendar_icon),
                            contentDescription = "end date Calendar icon",
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
                        Box(modifier = Modifier
                            .clickable {
                                if (isFestival == 0) {
                                    boardViewModel.updateIsFestival(2)
                                } else {
                                    if (isFestival == 1) {
                                        Toasty
                                            .info(
                                                context,
                                                "최소 하나는 선택해주세요",
                                                Toasty.LENGTH_SHORT,
                                                true
                                            )
                                            .show()
                                    }
                                    boardViewModel.updateIsFestival(0)
                                }
                            }
                            .testTag("festival checkBox")
                        ) {
                            CheckBox(
                                color = if (boardUiState.isFestival == 0) Color.Transparent else ButtonBlue,
                                size = 22
                            )
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
                        Box(modifier = Modifier
                            .clickable {
                                if (isFestival == 1) {
                                    boardViewModel.updateIsFestival(2)
                                } else {
                                    if (isFestival == 0) {
                                        Toasty
                                            .info(
                                                context,
                                                "최소 하나는 선택해주세요",
                                                Toasty.LENGTH_SHORT,
                                                true
                                            )
                                            .show()
                                    }
                                    boardViewModel.updateIsFestival(1)
                                }
                            }
                            .testTag("academic checkBox")
                        ) {
                            CheckBox(
                                color = if (boardUiState.isFestival == 1) Color.Transparent else ButtonBlue,
                                size = 22
                            )
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
                CustomHorizontalDivider(color = HaengshaGrey)
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ModalCancelButton(
                        onClick = {
                            onDismissRequest()
                            boardViewModel.cancelFilter()
                        }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    ModalConfirmButton(
                        type = "적용",
                        onClick = {
                            if (boardUiState.keyword.isEmpty()) {
                                Toasty.warning(context, "검색 후 필터를 적용해주세요", Toast.LENGTH_SHORT, true)
                                    .show()
                            } else if (!boardViewModel.isSearched.value) {
                                Toasty.warning(
                                    context,
                                    "검색 완료 후 필터를 적용해주세요",
                                    Toast.LENGTH_SHORT,
                                    true
                                )
                                    .show()
                            } else {
                                onSubmit(
                                    SearchRequest(
                                        boardUiState.token,
                                        boardUiState.keyword,
                                        boardUiState.isFestival,
                                        boardUiState.startDate,
                                        boardUiState.endDate
                                    )
                                )
                                boardViewModel.savePreviousFilter()
                            }
                            onDismissRequest()
                        }
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoardDatePickerDialog(
    onDismissRequest: () -> Unit,
    boardViewModel: BoardViewModel,
    type: String,
    usage: String
) {
    val currentDate = LocalDate.now()
    val initialDate = Calendar.getInstance()
    initialDate.set(currentDate.year, currentDate.monthValue - 1, currentDate.dayOfMonth)
    var selectedDate by remember { mutableLongStateOf(initialDate.timeInMillis) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate.timeInMillis,
        yearRange = currentDate.year - 1..currentDate.year + 1
    )
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)

    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = {
                    selectedDate = datePickerState.selectedDateMillis ?: initialDate.timeInMillis
                    if (type == "startDate") {
                        if (usage == "post") {
                            boardViewModel.updatePostStartDate(
                                dateFormatter.format(
                                    Date(
                                        selectedDate
                                    )
                                )
                            )
                        } else { // usage == "filter"
                            boardViewModel.updateFilterStartDate(
                                dateFormatter.format(
                                    Date(
                                        selectedDate
                                    )
                                )
                            )
                        }
                    } else {
                        if (usage == "post") {
                            boardViewModel.updatePostEndDate(
                                dateFormatter.format(
                                    Date(
                                        selectedDate
                                    )
                                )
                            )
                        } else { // usage == "filter"
                            boardViewModel.updateFilterEndDate(
                                dateFormatter.format(
                                    Date(
                                        selectedDate
                                    )
                                )
                            )
                        }
                    }
                    onDismissRequest()
                }) {
                Text(text = "확인")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text(text = "취소")
            }
        },
    ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = false
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val currentDate = LocalDate.now()
    val initialDate = Calendar.getInstance()
    initialDate.set(currentDate.year, currentDate.monthValue - 1, currentDate.dayOfMonth)
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate.timeInMillis,
        yearRange = currentDate.year - 1..currentDate.year + 1
    )
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
    val selectedDate = datePickerState.selectedDateMillis?.let {
        formatter.format(Date(it))
    } ?: ""

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onDateSelected(selectedDate)
                onDismiss()
            }) { Text(text = "확인") }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) { Text(text = "취소") }
        }
    ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = false
        )
    }
}