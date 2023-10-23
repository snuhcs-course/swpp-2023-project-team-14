package com.example.haengsha.ui.screens.login.signup

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.haengsha.model.route.LoginRoute
import com.example.haengsha.model.uiState.login.LoginUiState
import com.example.haengsha.model.viewModel.login.LoginViewModel
import com.example.haengsha.ui.theme.FieldStrokeBlue
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.CommonBlueButton
import com.example.haengsha.ui.uiComponents.ConfirmOnlyDialog
import com.example.haengsha.ui.uiComponents.codeVerifyField
import com.example.haengsha.ui.uiComponents.suffixTextField
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.delay

@Composable
fun SignupEmailVerificationScreen(
    loginViewModel: LoginViewModel,
    loginUiState: LoginUiState,
    loginNavController: NavController,
    loginNavBack: () -> Unit,
    loginContext: Context
) {
    var emailVerifyTrigger by remember { mutableIntStateOf(0) }
    var codeVerifyTrigger by remember { mutableIntStateOf(0) }
    var isCodeSent by remember { mutableIntStateOf(0) }
    var codeExpireTime by remember { mutableIntStateOf(180) }
    val codeExpireMinute = String.format("%02d", codeExpireTime / 60)
    val codeExpireSecond = String.format("%02d", codeExpireTime % 60)
    var emailInput: String by rememberSaveable { mutableStateOf("") }
    var codeInput: String by remember { mutableStateOf("") }
    var isEmailError by remember { mutableStateOf(false) }
    var isCodeError by remember { mutableStateOf(false) }
    var isEmailAlreadyExistDialogVisible by remember { mutableStateOf(false) }

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
                isEmptyError = isEmailError,
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
                                .error(loginContext, "이메일을 입력해주세요", Toast.LENGTH_SHORT, true)
                                .show()
                        } else {
                            isEmailError = false
                            emailVerifyTrigger++
                            loginViewModel.signupEmailVerify(emailInput)
                        }
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
            if (isEmailAlreadyExistDialogVisible) {
                ConfirmOnlyDialog(
                    onDismissRequest = { isEmailAlreadyExistDialogVisible = false },
                    onClick = { isEmailAlreadyExistDialogVisible = false },
                    text = "이미 가입한 계정입니다."
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
            codeInput = codeVerifyField(
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
                            loginContext,
                            "이메일을 입력해주세요",
                            Toast.LENGTH_SHORT,
                            true
                        ).show()
                    } else if (isCodeSent == 0) {
                        isEmailError = false
                        isCodeError = true
                        Toasty.error(
                            loginContext,
                            "인증번호를 확인해주세요",
                            Toast.LENGTH_SHORT,
                            true
                        ).show()
                    } else {
                        codeVerifyTrigger++
                        loginViewModel.loginCodeVerify(emailInput, codeInput)
                    }
                })
            Spacer(modifier = Modifier.height(45.dp))
            Box(
                modifier = Modifier
                    .width(270.dp)
                    .height(20.dp)
                    .clickable { loginNavBack() }
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

    LaunchedEffect(key1 = isCodeSent) {
        codeExpireTime = 180
        while (codeExpireTime > 0) {
            delay(1000L)
            codeExpireTime--
        }
    }

    if (emailVerifyTrigger > 0) {
        LaunchedEffect(key1 = loginUiState) {
            when (loginUiState) {
                is LoginUiState.Success -> {
                    Toasty
                        .success(
                            loginContext,
                            "인증코드가 발송되었습니다.",
                            Toast.LENGTH_SHORT,
                            true
                        )
                        .show()
                    isCodeSent++
                    emailVerifyTrigger = 0
                }

                is LoginUiState.HttpError -> {
                    if (loginUiState.message.contains("exist")) {
                        isEmailAlreadyExistDialogVisible = true
                    } else {
                        Toasty
                            .error(
                                loginContext,
                                loginUiState.message,
                                Toast.LENGTH_SHORT,
                                true
                            )
                            .show()
                    }
                    isEmailError = true
                    emailVerifyTrigger = 0
                }

                is LoginUiState.NetworkError -> {
                    Toasty
                        .error(
                            loginContext,
                            "인터넷 연결을 확인해주세요",
                            Toast.LENGTH_SHORT,
                            true
                        )
                        .show()
                    emailVerifyTrigger = 0
                }

                is LoginUiState.Loading -> {
                    /* Loading State, may add some loading UI or throw error after long time */
                }

                else -> {
                    /* Other Success State, do nothing */
                }
            }
        }
    }

    if (codeVerifyTrigger > 0) {
        LaunchedEffect(key1 = loginUiState) {
            when (loginUiState) {
                is LoginUiState.Success -> {
                    loginNavController.navigate(LoginRoute.SignupPassword.route)
                }

                is LoginUiState.HttpError -> {
                    Toasty
                        .error(
                            loginContext,
                            loginUiState.message,
                            Toast.LENGTH_SHORT,
                            true
                        )
                        .show()
                    isCodeError = true
                    codeVerifyTrigger = 0
                }

                is LoginUiState.NetworkError -> {
                    Toasty
                        .error(
                            loginContext,
                            "인터넷 연결을 확인해주세요",
                            Toast.LENGTH_SHORT,
                            true
                        )
                        .show()
                    codeVerifyTrigger = 0
                }

                is LoginUiState.Loading -> {
                    /* Loading State, may add some loading UI or throw error after long time */
                }

                else -> {
                    /* Other Success State, do nothing */
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SignupEmailVerificationScreenPreview() {
//    SignupEmailVerificationScreen(rememberNavController(), {}, loginContext = LocalContext.current)
//}