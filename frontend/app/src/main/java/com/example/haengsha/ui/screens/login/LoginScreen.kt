package com.example.haengsha.ui.screens.login

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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.haengsha.model.route.LoginRoute
import com.example.haengsha.model.route.MainRoute
import com.example.haengsha.model.uiState.login.LoginUiState
import com.example.haengsha.model.viewModel.UserViewModel
import com.example.haengsha.model.viewModel.login.LoginViewModel
import com.example.haengsha.ui.theme.ButtonBlue
import com.example.haengsha.ui.theme.FieldStrokeBlue
import com.example.haengsha.ui.theme.md_theme_light_outline
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.CommonBlueButton
import com.example.haengsha.ui.uiComponents.ConfirmOnlyDialog
import com.example.haengsha.ui.uiComponents.passwordTextField
import com.example.haengsha.ui.uiComponents.suffixTextField
import es.dmoral.toasty.Toasty

@Composable
fun LoginScreen(
    userViewModel: UserViewModel,
    mainNavController: NavHostController,
    loginNavController: NavHostController,
    loginViewModel: LoginViewModel,
    loginUiState: LoginUiState,
    loginContext: Context
) {
    var loginTrigger by remember { mutableIntStateOf(0) }
    var emailInput: String by rememberSaveable { mutableStateOf("") }
    var passwordInput: String by remember { mutableStateOf("") }
    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    var isLoginFailedDialogVisible by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 90.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(1) {
            Text(text = "Welcome to", style = TextStyle(fontSize = 24.sp, fontFamily = poppins))
            Text(
                text = "행샤",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    color = ButtonBlue
                )
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

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.width(270.dp),
                text = "비밀번호를 입력하세요.",
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            passwordInput = passwordTextField(
                isEmptyError = isPasswordError,
                placeholder = "비밀번호"
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .width(270.dp)
                    .height(20.dp)
                    .clickable { loginNavController.navigate(LoginRoute.FindPassword.route) }
            ) {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = "아이디/비밀번호 찾기",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 11.sp,
                    textAlign = TextAlign.End,
                    color = FieldStrokeBlue
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
            CommonBlueButton(text = "로그인하기",
                onClick = {
                    // TODO 로그인 과정 임시 간편화
//                    if (emailInput.trimStart() == "") {
//                        isEmailError = true
//                        Toasty.error(
//                            loginContext,
//                            "이메일을 입력해주세요",
//                            Toast.LENGTH_SHORT,
//                            true
//                        ).show()
//                    } else if (passwordInput.trimStart() == "") {
//                        isPasswordError = true
//                        Toasty.error(
//                            loginContext,
//                            "비밀번호를 입력해주세요",
//                            Toast.LENGTH_SHORT,
//                            true
//                        ).show()
//                    } else {
                    loginTrigger++
                    // loginViewModel.login("$emailInput@snu.ac.kr", passwordInput)
                    loginViewModel.login("user1@snu.ac.kr", "user1")
                    //}
                })
            if (isLoginFailedDialogVisible) {
                ConfirmOnlyDialog(
                    onDismissRequest = { isLoginFailedDialogVisible = false },
                    onClick = { isLoginFailedDialogVisible = false },
                    text = "로그인 정보를 확인해주세요."
                )
            }
            Spacer(modifier = Modifier.height(45.dp))
            Text(
                text = "No Account?",
                fontFamily = poppins,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                color = md_theme_light_outline
            )
            Spacer(modifier = Modifier.height(5.dp))
            Box(
                modifier = Modifier
                    .width(270.dp)
                    .height(20.dp)
                    .clickable { loginNavController.navigate(LoginRoute.SignupType.route) }
            ) {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = AnnotatedString("회원가입하기"),
                    fontFamily = poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    textDecoration = TextDecoration.Underline,
                    color = ButtonBlue
                )
            }
        }
    }

    if (loginTrigger > 0) {
        LaunchedEffect(key1 = loginUiState) {
            when (loginUiState) {
                is LoginUiState.LoginSuccess -> {
                    userViewModel.updateToken(loginUiState.token)
                    userViewModel.updateRole(loginUiState.role)
                    // TODO board 화면 테스트용
//                    mainNavController.navigate(MainRoute.Home.route) {
//                        popUpTo(LoginRoute.Login.route) { inclusive = true }
//                        popUpTo(MainRoute.Login.route) { inclusive = true }
//                    }
                    mainNavController.navigate(MainRoute.Dashboard.route) {
                        popUpTo(LoginRoute.Login.route) { inclusive = true }
                        popUpTo(MainRoute.Login.route) { inclusive = true }
                    }
                }

                is LoginUiState.HttpError -> {
                    isLoginFailedDialogVisible = true
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
//fun LoginScreenPreview() {
//    LoginScreen(rememberNavController(), rememberNavController(), LocalContext.current)
//}