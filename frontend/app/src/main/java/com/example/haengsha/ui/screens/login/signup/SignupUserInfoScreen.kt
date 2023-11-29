package com.example.haengsha.ui.screens.login.signup

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.haengsha.model.route.LoginRoute
import com.example.haengsha.model.uiState.login.LoginApiUiState
import com.example.haengsha.model.viewModel.login.LoginApiViewModel
import com.example.haengsha.model.viewModel.login.SignupViewModel
import com.example.haengsha.ui.theme.FieldStrokeBlue
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.CommonBlueButton
import com.example.haengsha.ui.uiComponents.commonTextField
import com.example.haengsha.ui.uiComponents.dropDown
import com.example.haengsha.ui.uiComponents.multiSelectDropDown
import es.dmoral.toasty.Toasty

@Composable
fun SignupUserInfoScreen(
    checkNickname: (String) -> Unit,
    loginApiViewModel: LoginApiViewModel,
    loginApiUiState: LoginApiUiState,
    signupViewModel: SignupViewModel,
    signupNickname: String,
    loginNavController: NavController,
    loginNavBack: () -> Unit,
    loginContext: Context
) {
    var nickname by rememberSaveable { mutableStateOf("") }
    var college by rememberSaveable { mutableStateOf("") }
    var studentId by rememberSaveable { mutableStateOf("") }
    var interest by rememberSaveable { mutableStateOf(listOf("")) }
    var isNicknameError by rememberSaveable { mutableStateOf(false) }
    var isNicknameChecked by rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 60.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(1) {
            Text(
                modifier = Modifier.width(305.dp),
                text = "나의 정보와 관심사는?",
                fontFamily = poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                modifier = Modifier.width(270.dp),
                text = buildAnnotatedString {
                    append("닉네임을 입력하세요. (2~10자) ")
                    withStyle(SpanStyle(color = Color.Red)) { append("*") }
                },
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(15.dp))
            nickname = commonTextField(
                isError = isNicknameError,
                placeholder = "닉네임",
                keyboardActions = { focusManager.clearFocus() },
            )
            Spacer(modifier = Modifier.height(15.dp))
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
                                    .error(
                                        loginContext,
                                        "공백은 앞뒤에 올 수 없습니다",
                                        Toast.LENGTH_SHORT,
                                        true
                                    )
                                    .show()
                            } else if (!lengthPattern.matches(nickname)) {
                                isNicknameError = true
                                Toasty
                                    .error(loginContext, "길이 제한을 지켜주세요", Toast.LENGTH_SHORT, true)
                                    .show()
                            } else {
                                checkNickname(nickname)
                            }
                        },
                    text = "중복 확인",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    textAlign = TextAlign.End,
                    color = FieldStrokeBlue
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                modifier = Modifier.width(270.dp),
                text = buildAnnotatedString {
                    append("학과를 선택하세요. ")
                    withStyle(SpanStyle(color = Color.Red)) { append("*") }
                },
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(15.dp))
            college = dropDown("학과")
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                modifier = Modifier.width(270.dp),
                text = buildAnnotatedString {
                    append("학번을 선택하세요. ")
                    withStyle(SpanStyle(color = Color.Red)) { append("*") }
                },
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(15.dp))
            studentId = dropDown("학번")
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                modifier = Modifier.width(270.dp),
                text = buildAnnotatedString {
                    append("관심사를 선택하세요. ")
                    withStyle(SpanStyle(color = Color.Red)) { append("*") }
                    append("\n(중복 선택 가능)")
                },
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(15.dp))
            interest = multiSelectDropDown("나의 관심 분야")
            Spacer(modifier = Modifier.height(80.dp))
            CommonBlueButton(
                text = "다음",
                onClick = {
                    if (nickname == "" || college == "" || studentId == "" || interest == listOf("")) {
                        Toasty
                            .error(loginContext, "정보 입력을 완료해주세요!", Toast.LENGTH_SHORT, true)
                            .show()
                    } else {
                        if (!isNicknameChecked || isNicknameError) {
                            Toasty
                                .error(loginContext, "닉네임 중복 확인을 해주세요!", Toast.LENGTH_SHORT, true)
                                .show()
                        } else {
                            if (signupNickname != nickname) {
                                isNicknameChecked = false
                                Toasty
                                    .error(
                                        loginContext,
                                        "닉네임 중복 확인을 해주세요!",
                                        Toast.LENGTH_SHORT,
                                        true
                                    )
                                    .show()
                            } else {
                                isNicknameChecked = true
                                signupViewModel.updateMajor(college)
                                signupViewModel.updateGrade(studentId)
                                signupViewModel.updateInterest(
                                    if (interest.size == 1) {
                                        interest[0]
                                    } else {
                                        interest.joinToString(", ").drop(2)
                                    }
                                )
                                loginNavController.navigate(LoginRoute.SignupTerms.route)
                            }
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

    when (loginApiUiState) {
        is LoginApiUiState.Success -> {
            isNicknameError = false
            signupViewModel.updateNickname(nickname)
            Toasty
                .success(loginContext, "사용 가능한 닉네임입니다", Toast.LENGTH_SHORT, true)
                .show()
            loginApiViewModel.resetLoginApiUiState()
        }

        is LoginApiUiState.HttpError -> {
            isNicknameError = true
            Toasty
                .warning(loginContext, "이미 존재하는 닉네임입니다", Toast.LENGTH_SHORT, true)
                .show()
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
            loginApiViewModel.resetLoginApiUiState()
        }

        is LoginApiUiState.Loading -> {
            /* Loading State, may add some loading UI */
        }

        else -> {
            /* Other Success State, do nothing */
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SignupUserInfoScreenPreview() {
//    SignupUserInfoScreen(rememberNavController(), {}, loginContext = LocalContext.current)
//}