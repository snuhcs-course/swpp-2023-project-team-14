package com.example.haengsha.ui.screens.signup

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.FieldStrokeBlue
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.CommonBlueButton
import com.example.haengsha.ui.uiComponents.commonTextField
import com.example.haengsha.ui.uiComponents.exposedDropDown
import es.dmoral.toasty.Toasty

@Composable
fun SignupUserInfoScreen(context: Context) {
    var nickname by rememberSaveable { mutableStateOf("") }
    var college by rememberSaveable { mutableStateOf("") }
    var studentId by rememberSaveable { mutableStateOf("") }
    var interest by rememberSaveable { mutableStateOf("") }
    var isNicknameError by rememberSaveable { mutableStateOf(false) }
    var isNicknameChecked by rememberSaveable { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 90.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(1) {
            Text(
                modifier = Modifier.width(270.dp),
                text = "나의 정보와 관심사는?",
                fontFamily = poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(45.dp))
            Text(
                modifier = Modifier.width(270.dp),
                text = buildAnnotatedString {
                    append("닉네임을 입력하세요. (2 ~ 10자) ")
                    withStyle(SpanStyle(color = Color.Red)) { append("*") }
                },
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            nickname = commonTextField(
                isError = isNicknameError,
                placeholder = "닉네임",
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .width(270.dp)
                    .height(20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    modifier = Modifier
                        .clickable {
                            val whitespacePattern = "^(\\s+.*|.*\\s+)$".toRegex()
                            val lengthPattern = "^\\S[\\s\\S]{0,8}\\S$".toRegex()

                            isNicknameChecked = true

                            if (whitespacePattern.matches(nickname)) {
                                isNicknameError = true
                                Toasty
                                    .error(context, "공백은 앞뒤에 올 수 없습니다", Toast.LENGTH_SHORT, true)
                                    .show()
                            } else if (!lengthPattern.matches(nickname)) {
                                isNicknameError = true
                                Toasty
                                    .error(context, "길이 제한을 초과했습니다", Toast.LENGTH_SHORT, true)
                                    .show()
                            } else {
                                isNicknameError = false
                                /* TODO 중복확인
                                if (서버 응답 == 중복) {
                                    isNicknameError = true
                                    Toasty
                                        .error(context, "이미 있는 닉네임입니다!", Toast.LENGTH_SHORT, true)
                                        .show()
                                } else isNicknameError = false
                                */
                            }
                        },
                    text = "중복 확인",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 11.sp,
                    textAlign = TextAlign.End,
                    color = FieldStrokeBlue
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.width(270.dp),
                text = buildAnnotatedString {
                    append("학과를 선택하세요.")
                    withStyle(SpanStyle(color = Color.Red)) { append("*") }
                },
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            college = exposedDropDown("학과")
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                modifier = Modifier.width(270.dp),
                text = buildAnnotatedString {
                    append("학번을 선택하세요.")
                    withStyle(SpanStyle(color = Color.Red)) { append("*") }
                },
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            studentId = exposedDropDown("학번")
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                modifier = Modifier.width(270.dp),
                text = buildAnnotatedString {
                    append("관심사를 선택하세요.")
                    withStyle(SpanStyle(color = Color.Red)) { append("*") }
                },
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            interest = exposedDropDown("나의 관심 분야")
            Spacer(modifier = Modifier.height(50.dp))
            CommonBlueButton(
                text = "다음",
                onClick = {
                    if (nickname == "" || college == "" || studentId == "" || interest == "") {
                        Toasty
                            .error(context, "정보 입력을 완료해주세요!", Toast.LENGTH_SHORT, true)
                            .show()
                    } else {
                        if (!isNicknameChecked || isNicknameError) {
                            Toasty
                                .error(context, "닉네임 중복 확인을 해주세요!", Toast.LENGTH_SHORT, true)
                                .show()
                        } else {/* TODO 정보들 임시 저장 & 다음 페이지 넘어가기 */
                        }
                    }
                })
            Spacer(modifier = Modifier.height(45.dp))
            Box(
                modifier = Modifier
                    .width(270.dp)
                    .height(20.dp)
                    .clickable {
                        /* TODO 이전 화면으로 돌아가기 */
                    }
            ) {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = "이전 화면으로 돌아가기",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignupUserInfoScreenPreview() {
    SignupUserInfoScreen(LocalContext.current)
}