package com.example.haengsha.ui.screens.dashBoard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.haengsha.R
import com.example.haengsha.model.uiState.UserUiState
import com.example.haengsha.model.viewModel.board.BoardViewModel
import com.example.haengsha.ui.theme.ButtonBlue
import com.example.haengsha.ui.theme.HaengshaBlue
import com.example.haengsha.ui.theme.PlaceholderGrey
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.CheckBox
import com.example.haengsha.ui.uiComponents.customTextField

@Composable
fun BoardPostScreen(
    innerPadding: PaddingValues,
    boardViewModel: BoardViewModel,
    userUiState: UserUiState
) {
    var eventTitle by remember { mutableStateOf("") }
    var eventDuration by remember { mutableStateOf("") }
    var eventPlace by remember { mutableStateOf("") }
    var eventTime by remember { mutableStateOf("") }
    var eventContent by remember { mutableStateOf("") }
    var eventCategory by remember { mutableStateOf(true) } // 행사면 true

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Column {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(color = Color(0x00F8F8F8)),
                value = eventTitle,
                onValueChange = { eventTitle = it },
                placeholder = {
                    Text(
                        text = "행사명을 입력해주세요.",
                        fontFamily = poppins,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = PlaceholderGrey,
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0x00F8F8F8),
                    focusedContainerColor = Color(0x00F8F8F8)
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "필수 정보를 입력해주세요",
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                    Row(
                        modifier = Modifier.clickable { /*TODO 사진 선택 모달*/ },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "사진 첨부 (선택)",
                            fontFamily = poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Icon(
                            modifier = Modifier.size(14.dp),
                            imageVector = ImageVector.vectorResource(id = R.drawable.attach_icon),
                            contentDescription = "attach icon",
                            tint = Color.Black
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "주최",
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    VerticalDivider(
                        modifier = Modifier.height(16.dp),
                        thickness = 1.dp
                    )
                    customTextField(
                        placeholder = userUiState.nickname,
                        enabled = false
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "일자",
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    VerticalDivider(
                        modifier = Modifier.height(16.dp),
                        thickness = 1.dp
                    )
                    eventDuration = customTextField(
                        placeholder = "2023.11.11 ~ 2023.11.13",
                        enabled = true
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "장소",
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    VerticalDivider(
                        modifier = Modifier.height(16.dp),
                        thickness = 1.dp
                    )
                    eventPlace = customTextField(
                        placeholder = "자하연 앞",
                        enabled = true
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "시간",
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    VerticalDivider(
                        modifier = Modifier.height(16.dp),
                        thickness = 1.dp
                    )
                    eventTime = customTextField(
                        placeholder = "오후 1시 ~ 오후 6시",
                        enabled = true
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "분류",
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    VerticalDivider(
                        modifier = Modifier.height(16.dp),
                        thickness = 1.dp
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier.clickable { eventCategory = true }
                            ) {
                                CheckBox(
                                    color = if (eventCategory) ButtonBlue else Color.Transparent,
                                    size = 18
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "공연",
                                fontFamily = poppins,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.clickable { eventCategory = false }) {
                                CheckBox(
                                    color = if (eventCategory) Color.Transparent else ButtonBlue,
                                    size = 18
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "학술",
                                fontFamily = poppins,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    eventTime = customTextField(
                        placeholder = "오후 1시 ~ 오후 6시",
                        enabled = true
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = PlaceholderGrey
            )
            TextField(
                modifier = Modifier
                    .fillMaxSize(),
                value = eventContent,
                onValueChange = { eventContent = it },
                placeholder = {
                    Text(
                        text = "행사 정보를 입력해주세요",
                        fontFamily = poppins,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = PlaceholderGrey
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0x00F8F8F8),
                    focusedContainerColor = Color(0x00F8F8F8)
                )
            )
        }
        Box(modifier = Modifier.offset(330.dp, 600.dp)) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(ButtonBlue, RoundedCornerShape(30.dp))
                    .clickable(onClick = {/*TODO 이벤트 등록 확인 모달*/ }),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(25.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.write_confirm_icon),
                    contentDescription = "event upload Button",
                    tint = Color.White
                )
            }
        }
    }
}
