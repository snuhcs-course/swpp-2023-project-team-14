package com.example.haengsha.ui.screens.login.signup

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.haengsha.model.route.LoginRoute
import com.example.haengsha.model.uiState.login.LoginApiUiState
import com.example.haengsha.model.viewModel.login.LoginApiViewModel
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
    loginApiViewModel: LoginApiViewModel,
    loginApiUiState: LoginApiUiState,
    signupEmailUpdate: (String) -> Unit,
    loginNavController: NavController,
    loginNavBack: () -> Unit,
    loginContext: Context,
    isTest: Boolean
) {
    var emailVerifyTrigger by remember { mutableStateOf(false) }
    var codeVerifyTrigger by remember { mutableStateOf(false) }
    var codeSent by remember { mutableIntStateOf(0) }
    var codeExpireTime by remember { mutableIntStateOf(180) }
    val codeExpireMinute = String.format("%02d", codeExpireTime / 60)
    val codeExpireSecond = String.format("%02d", codeExpireTime % 60)
    var emailInput: String by rememberSaveable { mutableStateOf("") }
    var codeInput: String by remember { mutableStateOf("") }
    var isCodeSending by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }
    var isCodeError by remember { mutableStateOf(false) }
    var isEmailAlreadyExistDialogVisible by remember { mutableStateOf(false) }
    var isNextClicked by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    })
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(1) {
            Text(
                modifier = Modifier.width(270.dp),
                text = "SNU Email 인증",
                fontFamily = poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                modifier = Modifier.width(270.dp),
                text = "SNU Email을 입력하세요.",
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(15.dp))
            emailInput = suffixTextField(
                isEmptyError = isEmailError,
                placeholder = "SNU Email",
                keyboardActions = { focusManager.clearFocus() },
                //suffix = "@snu.ac.kr"
            )
            Spacer(modifier = Modifier.height(15.dp))
            Box(
                modifier = Modifier.width(270.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                if (isCodeSending) {
                    Text(
                        text = "인증번호 발송 중...",
                        fontFamily = poppins,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        textAlign = TextAlign.End,
                        color = FieldStrokeBlue
                    )
                } else {
                    Text(
                        modifier = Modifier.clickable {
                            if (emailInput.trimStart() == "") {
                                isEmailError = true
                                Toasty
                                    .warning(loginContext, "이메일을 입력해주세요", Toast.LENGTH_SHORT, true)
                                    .show()
                            } else if (emailInput.trimEnd().endsWith("@snu.ac.kr").not()) {
                                isEmailError = true
                                Toasty
                                    .warning(
                                        loginContext,
                                        "SNU 이메일을 입력해주세요",
                                        Toast.LENGTH_SHORT,
                                        true
                                    )
                                    .show()
                            } else {
                                isEmailError = false
                                emailVerifyTrigger = true
                                isCodeSending = true
                                codeVerifyTrigger = false
                                loginApiViewModel.signupEmailVerify(emailInput)
                            }
                        },
                        text = "인증번호 " + if (codeSent == 0) "" else {
                            "재"
                        } + "발송",
                        fontFamily = poppins,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        textAlign = TextAlign.End,
                        color = FieldStrokeBlue
                    )
                }
            }
            if (isEmailAlreadyExistDialogVisible) {
                ConfirmOnlyDialog(
                    onDismissRequest = { isEmailAlreadyExistDialogVisible = false },
                    onClick = { isEmailAlreadyExistDialogVisible = false },
                    text = "이미 가입된 계정입니다."
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                modifier = Modifier.width(270.dp),
                text = "인증번호를 입력하세요.",
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(15.dp))
            codeInput = codeVerifyField(
                isError = isCodeError,
                placeholder = "인증번호 6자리",
                keyboardActions = { focusManager.clearFocus() }
            )
            Spacer(modifier = Modifier.height(15.dp))
            Box(
                modifier = Modifier
                    .width(270.dp)
                    .height(20.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = if (codeSent == 0) "" else "남은 시간 $codeExpireMinute:$codeExpireSecond",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    textAlign = TextAlign.End,
                    color = FieldStrokeBlue
                )
            }
            Spacer(modifier = Modifier.height(60.dp))
            CommonBlueButton(
                text = "다음",
                onClick = {
                    if (isTest) {
                        loginNavController.navigate(LoginRoute.SignupPassword.route)
                        loginApiViewModel.resetLoginApiUiState()
                    } else if (emailInput.trimStart() == "") {
                        isEmailError = true
                        Toasty.error(
                            loginContext,
                            "이메일을 입력해주세요",
                            Toast.LENGTH_SHORT,
                            true
                        ).show()
                    } else if (codeSent == 0) {
                        isEmailError = false
                        isCodeError = true
                        Toasty.error(
                            loginContext,
                            "인증번호를 확인해주세요",
                            Toast.LENGTH_SHORT,
                            true
                        ).show()
                    } else if (codeExpireTime == 0) {
                        Toasty
                            .warning(
                                loginContext,
                                "인증번호가 만료되었습니다.\n재발송해주세요",
                                Toast.LENGTH_SHORT,
                                true
                            )
                            .show()
                        isCodeError = true
                        codeVerifyTrigger = false
                        loginApiViewModel.resetLoginApiUiState()
                    } else {
                        if (!isNextClicked) {
                            isNextClicked = true
                            codeVerifyTrigger = true
                            loginApiViewModel.loginCodeVerify(emailInput, codeInput)
                        }
                    }
                })
            Spacer(modifier = Modifier.height(60.dp))
            Box(modifier = Modifier.wrapContentSize()) {
                Text(
                    modifier = Modifier.clickable { loginNavBack() },
                    text = "이전 화면으로 돌아가기",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }

    LaunchedEffect(key1 = codeSent) {
        codeExpireTime = 180
        while (codeExpireTime > 0) {
            delay(1000L)
            codeExpireTime--
        }
    }

    if (emailVerifyTrigger) {
        when (loginApiUiState) {
            is LoginApiUiState.Success -> {
                Toasty
                    .success(
                        loginContext,
                        "인증코드가 발송되었습니다.",
                        Toast.LENGTH_SHORT,
                        true
                    )
                    .show()
                isCodeSending = false
                codeSent++
                emailVerifyTrigger = false
                loginApiViewModel.resetLoginApiUiState()
            }

            is LoginApiUiState.HttpError -> {
                if (loginApiUiState.message == "이미 가입된 계정입니다") {
                    isEmailAlreadyExistDialogVisible = true
                } else {
                    Toasty
                        .warning(
                            loginContext,
                            loginApiUiState.message,
                            Toast.LENGTH_SHORT,
                            true
                        )
                        .show()
                }
                isCodeSending = false
                isEmailError = true
                emailVerifyTrigger = false
                loginApiViewModel.resetLoginApiUiState()
            }

            is LoginApiUiState.NetworkError -> {
                Toasty
                    .error(
                        loginContext,
                        "인터넷 연결을 확인해주세요",
                        Toast.LENGTH_SHORT,
                        true
                    )
                    .show()
                isCodeSending = false
                emailVerifyTrigger = false
                loginApiViewModel.resetLoginApiUiState()
            }

            is LoginApiUiState.Loading -> {
                /* Loading State, handled in upper codes  */
            }

            else -> {
                /* Other Success State, do nothing */
            }
        }
    }

    if (codeVerifyTrigger) {
        when (loginApiUiState) {
            is LoginApiUiState.Success -> {
                signupEmailUpdate(emailInput)
                loginNavController.navigate(LoginRoute.SignupPassword.route)
                loginApiViewModel.resetLoginApiUiState()
            }

            is LoginApiUiState.HttpError -> {
                Toasty
                    .warning(
                        loginContext,
                        loginApiUiState.message,
                        Toast.LENGTH_SHORT,
                        true
                    )
                    .show()
                isCodeError = true
                isNextClicked = false
                codeVerifyTrigger = false
                loginApiViewModel.resetLoginApiUiState()
            }

            is LoginApiUiState.NetworkError -> {
                Toasty
                    .error(
                        loginContext,
                        "인터넷 연결을 확인해주세요",
                        Toast.LENGTH_SHORT,
                        true
                    )
                    .show()
                codeVerifyTrigger = false
                isNextClicked = false
                loginApiViewModel.resetLoginApiUiState()
            }

            is LoginApiUiState.Loading -> {
                /* Loading State, handled in upper codes */
            }

            else -> {
                /* Other Success State, do nothing */
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SignupEmailVerificationScreenPreview() {
//    SignupEmailVerificationScreen(rememberNavController(), {}, loginContext = LocalContext.current)
//}