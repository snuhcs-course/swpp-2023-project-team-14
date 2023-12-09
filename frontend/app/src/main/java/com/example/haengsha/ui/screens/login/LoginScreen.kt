package com.example.haengsha.ui.screens.login

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.haengsha.model.route.LoginRoute
import com.example.haengsha.model.route.MainRoute
import com.example.haengsha.model.uiState.login.LoginApiUiState
import com.example.haengsha.model.viewModel.UserViewModel
import com.example.haengsha.model.viewModel.board.BoardViewModel
import com.example.haengsha.model.viewModel.login.LoginApiViewModel
import com.example.haengsha.ui.theme.ButtonBlue
import com.example.haengsha.ui.theme.FieldStrokeBlue
import com.example.haengsha.ui.theme.md_theme_light_outline
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.CommonBlueButton
import com.example.haengsha.ui.uiComponents.ConfirmOnlyDialog
import com.example.haengsha.ui.uiComponents.LoadingScreen
import com.example.haengsha.ui.uiComponents.passwordTextField
import com.example.haengsha.ui.uiComponents.suffixTextField
import es.dmoral.toasty.Toasty

@Composable
fun LoginScreen(
    userViewModel: UserViewModel,
    boardViewModel: BoardViewModel,
    mainNavController: NavHostController,
    loginNavController: NavHostController,
    loginApiViewModel: LoginApiViewModel,
    loginApiUiState: LoginApiUiState,
    loginContext: Context
) {
    var isLoginLoading by remember { mutableStateOf(false) }
    var emailInput: String by rememberSaveable { mutableStateOf("") }
    var passwordInput: String by remember { mutableStateOf("") }
    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    var isLoginFailedDialogVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val configuration = LocalConfiguration.current
    val deviceHeight = configuration.screenHeightDp.dp

    BackHandler(enabled = isLoginLoading) { isLoginLoading = false }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = deviceHeight / 15)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                })
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to",
            fontSize = 28.sp,
            fontFamily = poppins
        )
        Text(
            text = "행샤",
            fontSize = 36.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Bold,
            color = ButtonBlue
        )
        Spacer(modifier = Modifier.height(deviceHeight / 20))
        Text(
            modifier = Modifier.width(270.dp),
            text = "행샤 계정을 입력하세요.",
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(15.dp))
        emailInput = suffixTextField(
            isEmptyError = isEmailError,
            placeholder = "SNU Email",
            keyboardActions = { focusManager.moveFocus(FocusDirection.Down) },
            //suffix = "@snu.ac.kr"
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            modifier = Modifier.width(270.dp),
            text = "비밀번호를 입력하세요.",
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(15.dp))
        passwordInput = passwordTextField(
            isEmptyError = isPasswordError,
            placeholder = "비밀번호",
            keyboardActions = { focusManager.clearFocus() },
        )
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.width(270.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                modifier = Modifier.clickable { loginNavController.navigate(LoginRoute.FindPassword.route) },
                text = "비밀번호 찾기",
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                textAlign = TextAlign.End,
                color = FieldStrokeBlue
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        CommonBlueButton(text = "로그인하기",
            onClick = {
                if (emailInput.trimStart() == "") {
                    isEmailError = true
                    Toasty.error(
                        loginContext,
                        "이메일을 입력해주세요",
                        Toast.LENGTH_SHORT,
                        true
                    ).show()
                } else if (passwordInput.trimStart() == "") {
                    isPasswordError = true
                    Toasty.error(
                        loginContext,
                        "비밀번호를 입력해주세요",
                        Toast.LENGTH_SHORT,
                        true
                    ).show()
                } else {
                    isLoginLoading = true
                    loginApiViewModel.login(emailInput, passwordInput)
                }
            })
        if (isLoginFailedDialogVisible) {
            ConfirmOnlyDialog(
                onDismissRequest = { isLoginFailedDialogVisible = false },
                onClick = { isLoginFailedDialogVisible = false },
                text = "로그인 정보를 확인해주세요."
            )
        }
        Spacer(modifier = Modifier.height(deviceHeight / 20))
        Text(
            text = "No Account?",
            fontFamily = poppins,
            fontWeight = FontWeight.Light,
            fontSize = 15.sp,
            color = md_theme_light_outline
        )
        Spacer(modifier = Modifier.height(deviceHeight / 100))
        Text(
            modifier = Modifier
                .wrapContentSize()
                .clickable { loginNavController.navigate(LoginRoute.SignupType.route) },
            text = AnnotatedString("회원가입하기"),
            fontFamily = poppins,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            textDecoration = TextDecoration.Underline,
            color = ButtonBlue
        )
    }

    when (loginApiUiState) {
        is LoginApiUiState.LoginSuccess -> {
            if (isLoginLoading) {
                LoadingScreen("행샤에 로그인하는 중...")
            }
            userViewModel.updateToken(loginApiUiState.token)
            userViewModel.updateRole(loginApiUiState.role)
            userViewModel.updateNickname(loginApiUiState.nickname)
            boardViewModel.saveToken(loginApiUiState.token)
            mainNavController.navigate(MainRoute.Home.route) {
                popUpTo(LoginRoute.Login.route) { inclusive = true }
                popUpTo(MainRoute.Login.route) { inclusive = true }
            }
            loginApiViewModel.resetLoginApiUiState()
        }

        is LoginApiUiState.HttpError -> {
            isLoginFailedDialogVisible = true
            isLoginLoading = false
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
            isLoginLoading = false
            loginApiViewModel.resetLoginApiUiState()
        }

        is LoginApiUiState.Loading -> {
            if (isLoginLoading) {
                LoadingScreen("행샤에 로그인하는 중...")
            }
        }

        else -> {
            /* Other Success State, do nothing */
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//    LoginScreen(rememberNavController(), rememberNavController(), LocalContext.current)
//}