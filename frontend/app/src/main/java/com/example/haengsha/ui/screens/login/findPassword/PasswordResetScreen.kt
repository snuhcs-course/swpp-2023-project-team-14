package com.example.haengsha.ui.screens.login.findPassword

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.haengsha.model.uiState.login.FindPasswordUiState
import com.example.haengsha.model.uiState.login.LoginUiState
import com.example.haengsha.model.viewModel.login.FindPasswordViewModel
import com.example.haengsha.model.viewModel.login.LoginApiViewModel
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.CommonBlueButton
import com.example.haengsha.ui.uiComponents.passwordCheckTextField
import com.example.haengsha.ui.uiComponents.passwordSetField
import es.dmoral.toasty.Toasty

@Composable
fun PasswordResetScreen(
    loginApiViewModel: LoginApiViewModel,
    loginUiState: LoginUiState,
    findPasswordViewModel: FindPasswordViewModel,
    findPasswordUiState: FindPasswordUiState,
    loginNavController: NavController,
    loginNavBack: () -> Unit,
    loginContext: Context
) {
    var resetPasswordTrigger by remember { mutableIntStateOf(0) }
    var passwordInput: String by remember { mutableStateOf("") }
    var passwordCheckInput: String by remember { mutableStateOf("") }
    var isPasswordError by remember { mutableStateOf(false) }
    var isPasswordCheckError by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 90.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(1) {
            Text(
                modifier = Modifier.width(270.dp),
                text = "비밀번호 재설정",
                fontFamily = poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(45.dp))
            Row {
                Spacer(modifier = Modifier.width(50.dp))
                Text(
                    modifier = Modifier.width(320.dp),
                    text = "새 비밀번호를 입력하세요. (영문+숫자 4~10자)",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            passwordInput = passwordSetField(
                isEmptyError = isPasswordError,
                placeholder = "Password",
                context = loginContext
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
            Spacer(modifier = Modifier.height(50.dp))
            CommonBlueButton(
                text = "변경 완료하기",
                onClick = {
                    if (passwordInput.trimStart() == "") {
                        isPasswordError = true
                        Toasty.error(
                            loginContext,
                            "비밀번호를 입력해주세요",
                            Toast.LENGTH_SHORT,
                            true
                        ).show()
                    } else {
                        if (passwordCheckInput != passwordInput) {
                            isPasswordCheckError = true
                            Toasty.error(
                                loginContext,
                                "비밀번호를 확인해주세요",
                                Toast.LENGTH_SHORT,
                                true
                            ).show()
                        } else {
                            resetPasswordTrigger++
                            loginApiViewModel.findChangePassword(
                                email = findPasswordUiState.email,
                                passwordInput,
                                passwordCheckInput
                            )
                        }
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

    if (resetPasswordTrigger > 0) {
        LaunchedEffect(key1 = loginUiState) {
            when (loginUiState) {
                is LoginUiState.Success -> {
                    findPasswordViewModel.resetFindPasswordData()
                    loginNavController.navigate(LoginRoute.FindPasswordComplete.route) {
                        popUpTo(LoginRoute.Login.route) { inclusive = false }
                    }
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
//fun PasswordResetScreenPreview() {
//    PasswordResetScreen(rememberNavController(), {}, loginContext = LocalContext.current)
//}