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
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.CommonBlueButton
import com.example.haengsha.ui.uiComponents.passwordCheckTextField
import com.example.haengsha.ui.uiComponents.passwordTextField
import es.dmoral.toasty.Toasty

@Composable
fun SignupPasswordSetScreen(context: Context) {
    var passwordInput: String by rememberSaveable { mutableStateOf("") }
    var passwordCheckInput: String by rememberSaveable { mutableStateOf("") }
    var isPasswordError by rememberSaveable { mutableStateOf(false) }
    var isPasswordCheckError by rememberSaveable { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 90.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(1) {
            Text(
                modifier = Modifier.width(270.dp),
                text = "비밀번호 설정",
                fontFamily = poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(45.dp))
            Text(
                modifier = Modifier.width(270.dp),
                text = "비밀번호를 입력하세요. (영문 4 ~ 10자)",
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            passwordInput = passwordTextField(
                isEmptyError = isPasswordError,
                placeholder = "Password",
                context = context
            )
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                modifier = Modifier.width(270.dp),
                text = "비밀번호를 다시 한 번 입력하세요.",
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            passwordCheckInput = passwordCheckTextField(
                isError = isPasswordCheckError,
                placeholder = "Password"
            )
            Spacer(modifier = Modifier.height(80.dp))
            CommonBlueButton(text = "다음",
                onClick = {
                    if (passwordInput.trimStart() == "") {
                        isPasswordError = true
                        Toasty.error(
                            context,
                            "비밀번호를 입력해주세요",
                            Toast.LENGTH_SHORT,
                            true
                        ).show()
                    } else {
                        if (passwordCheckInput != passwordInput) {
                            isPasswordCheckError = true
                            Toasty.error(
                                context,
                                "비밀번호를 확인해주세요",
                                Toast.LENGTH_SHORT,
                                true
                            ).show()
                        } else {
                            /* TODO 비밀번호 임시 저장 & 다음 페이지 넘어가기 */
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
fun SignupPasswordSetScreenPreview() {
    SignupPasswordSetScreen(context = LocalContext.current)
}