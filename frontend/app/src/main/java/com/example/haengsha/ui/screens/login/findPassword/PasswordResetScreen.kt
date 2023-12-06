package com.example.haengsha.ui.screens.login.findPassword

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.haengsha.model.uiState.login.FindPasswordUiState
import com.example.haengsha.model.uiState.login.LoginApiUiState
import com.example.haengsha.model.viewModel.login.FindPasswordViewModel
import com.example.haengsha.model.viewModel.login.LoginApiViewModel
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.CommonBlueButton
import com.example.haengsha.ui.uiComponents.LoadingScreen
import com.example.haengsha.ui.uiComponents.passwordCheckTextField
import com.example.haengsha.ui.uiComponents.passwordSetField
import es.dmoral.toasty.Toasty

@Composable
fun PasswordResetScreen(
    loginApiViewModel: LoginApiViewModel,
    loginApiUiState: LoginApiUiState,
    findPasswordViewModel: FindPasswordViewModel,
    findPasswordUiState: FindPasswordUiState,
    loginNavController: NavController,
    loginNavBack: () -> Unit,
    loginContext: Context,
    isTest: Boolean
) {
    var passwordInput: String by remember { mutableStateOf("") }
    var passwordCheckInput: String by remember { mutableStateOf("") }
    var isPasswordError by remember { mutableStateOf(false) }
    var isPasswordCheckError by remember { mutableStateOf(false) }
    var isPasswordReset by remember { mutableStateOf(false) }
    val pattern = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{4,10}$".toRegex()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                })
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(1) {
            Text(
                modifier = Modifier.width(270.dp),
                text = "비밀번호 재설정",
                fontFamily = poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.height(60.dp))
            Row {
                Spacer(modifier = Modifier.width(50.dp))
                Text(
                    modifier = Modifier.width(320.dp),
                    text = "새 비밀번호를 입력하세요.\n(영문+숫자 혼합 4~10자)",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            passwordInput = passwordSetField(
                isEmptyError = isPasswordError,
                placeholder = "Password",
                keyboardActions = { focusManager.clearFocus() },
                context = loginContext,
            )
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                modifier = Modifier.width(280.dp),
                text = "비밀번호를 다시 한 번 입력하세요.",
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(15.dp))
            passwordCheckInput = passwordCheckTextField(
                isError = isPasswordCheckError,
                placeholder = "Password",
                keyboardActions = { focusManager.clearFocus() }
            )
            Spacer(modifier = Modifier.height(80.dp))
            CommonBlueButton(
                text = "변경 완료하기",
                onClick = {
                    if (isTest) {
                        loginNavController.navigate(LoginRoute.FindPasswordComplete.route) {
                            popUpTo(LoginRoute.Login.route) { inclusive = false }
                        }
                        loginApiViewModel.resetLoginApiUiState()
                    } else if (passwordInput.trimStart() == "") {
                        isPasswordError = true
                        Toasty.error(
                            loginContext,
                            "비밀번호를 입력해주세요",
                            Toast.LENGTH_SHORT,
                            true
                        ).show()
                    } else if (!pattern.matches(passwordInput)) {
                        isPasswordError = true
                        Toasty.error(
                            loginContext,
                            "비밀번호 형식을 지켜주세요",
                            Toast.LENGTH_SHORT,
                            true
                        ).show()
                    } else if (passwordCheckInput != passwordInput) {
                        isPasswordCheckError = true
                        Toasty.error(
                            loginContext,
                            "비밀번호가 서로 달라요",
                            Toast.LENGTH_SHORT,
                            true
                        ).show()
                    } else if (!isPasswordReset) {
                        isPasswordReset = true
                        loginApiViewModel.findChangePassword(
                            email = findPasswordUiState.email,
                            passwordInput,
                            passwordCheckInput
                        )
                    }
                })
            Spacer(modifier = Modifier.height(50.dp))
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

    when (loginApiUiState) {
        is LoginApiUiState.Success -> {
            if (isPasswordReset) {
                LoadingScreen("비밀번호 변경 중...")
            }
            findPasswordViewModel.resetFindPasswordData()
            loginNavController.navigate(LoginRoute.FindPasswordComplete.route) {
                popUpTo(LoginRoute.Login.route) { inclusive = false }
            }
            loginApiViewModel.resetLoginApiUiState()
        }

        is LoginApiUiState.HttpError -> {
            Toasty
                .error(
                    loginContext,
                    loginApiUiState.message,
                    Toast.LENGTH_SHORT,
                    true
                )
                .show()
            isPasswordReset = false
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
            isPasswordReset = false
            loginApiViewModel.resetLoginApiUiState()
        }

        is LoginApiUiState.Loading -> {
            if (isPasswordReset) {
                LoadingScreen("비밀번호 변경 중...")
            }
        }

        else -> {
            /* Other Success State, do nothing */
        }
    }

}

//@Preview(showBackground = true)
//@Composable
//fun PasswordResetScreenPreview() {
//    PasswordResetScreen(rememberNavController(), {}, loginContext = LocalContext.current)
//}