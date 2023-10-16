package com.example.haengsha.ui.screens.signup

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.haengsha.ui.theme.FieldStrokeBlue
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.CommonBlueButton
import com.example.haengsha.ui.uiComponents.commonTextField
import com.example.haengsha.ui.uiComponents.suffixTextField
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.delay

@Composable
fun SignupEmailVerificationScreen(context: Context) {
    var isCodeSent by rememberSaveable { mutableStateOf(0) }
    var codeExpireTime by rememberSaveable { mutableStateOf(180) }
    val codeExpireMinute = String.format("%02d", codeExpireTime / 60)
    val codeExpireSecond = String.format("%02d", codeExpireTime % 60)
    var emailInput: String by rememberSaveable { mutableStateOf("") }
    var codeInput: String by rememberSaveable { mutableStateOf("") }
    var isEmailError by rememberSaveable { mutableStateOf(false) }
    var isCodeError by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = isCodeSent) {
        codeExpireTime = 180
        while (codeExpireTime > 0) {
            delay(1000L)
            codeExpireTime--
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 90.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(1) {
            Text(
                modifier = Modifier.width(270.dp),
                text = "SNU Email 인증",
                fontFamily = poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(45.dp))
            Text(
                modifier = Modifier.width(270.dp),
                text = "SNU Email을 입력하세요.",
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            emailInput = suffixTextField(
                isError = isEmailError,
                placeholder = "SNU Email",
                suffix = "@snu.ac.kr"
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .width(270.dp)
                    .height(20.dp)
                    .clickable {
                        if (emailInput.trimStart() == "") {
                            isEmailError = true
                            Toasty
                                .error(context, "이메일을 입력해주세요", Toast.LENGTH_SHORT, true)
                                .show()
                        } else isEmailError = false
                        /* TODO 인증번호 발송 */
                        isCodeSent++
                    },
            ) {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = "인증번호 " + if (isCodeSent == 0) "" else {
                        "재"
                    } + "발송",
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
                text = "인증번호를 입력하세요.",
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            codeInput = commonTextField(
                isError = isCodeError,
                placeholder = "인증번호 6자리"
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .width(270.dp)
                    .height(20.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = if (isCodeSent == 0) "" else "남은 시간 $codeExpireMinute:$codeExpireSecond",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 11.sp,
                    textAlign = TextAlign.End,
                    color = FieldStrokeBlue
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
            CommonBlueButton(text = "다음",
                onClick = {
                    if (emailInput.trimStart() == "") {
                        isEmailError = true
                        Toasty.error(
                            context,
                            "이메일을 입력해주세요",
                            Toast.LENGTH_SHORT,
                            true
                        ).show()
                    } else if (isCodeSent == 0) {
                        isEmailError = false
                        isCodeError = true
                        Toasty.error(
                            context,
                            "인증번호를 확인해주세요",
                            Toast.LENGTH_SHORT,
                            true
                        ).show()
                    } else {
                        /* TODO 인증번호 확인
                        *   if 맞으면 다음 화면
                        *   else 다르면 isCodeError = true */
                    }
                })
            Spacer(modifier = Modifier.height(45.dp))
            Box(
                modifier = Modifier
                    .width(270.dp)
                    .height(20.dp)
                    .clickable { /* TODO 이전 화면으로 돌아가기 */ }
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
fun SignupEmailVerificationScreenPreview() {
    SignupEmailVerificationScreen(context = LocalContext.current)
}