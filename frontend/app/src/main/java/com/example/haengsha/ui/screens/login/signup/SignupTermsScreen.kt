package com.example.haengsha.ui.screens.login.signup

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.haengsha.model.route.LoginRoute
import com.example.haengsha.model.uiState.login.LoginApiUiState
import com.example.haengsha.model.uiState.login.SignupUiState
import com.example.haengsha.model.viewModel.login.LoginApiViewModel
import com.example.haengsha.ui.theme.ButtonBlue
import com.example.haengsha.ui.theme.ButtonGrey
import com.example.haengsha.ui.theme.HaengshaGrey
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.CheckBox
import com.example.haengsha.ui.uiComponents.CommonBlueButton
import com.example.haengsha.ui.uiComponents.CommonGreyButton
import com.example.haengsha.ui.uiComponents.PrivacyPolicyModalText
import com.example.haengsha.ui.uiComponents.TermsOfUseModalText
import es.dmoral.toasty.Toasty

@Composable
fun SignupTermsScreen(
    loginApiViewModel: LoginApiViewModel,
    loginUiState: LoginApiUiState,
    signupStateReset: () -> Unit,
    signupUiState: SignupUiState,
    loginNavController: NavController,
    loginNavBack: () -> Unit,
    loginContext: Context
) {
    var isAllChecked by rememberSaveable { mutableStateOf(false) }
    var isTermsChecked by rememberSaveable { mutableStateOf(false) }
    var isPolicyChecked by rememberSaveable { mutableStateOf(false) }
    var isTermsModal by remember { mutableStateOf(false) }
    var isPolicyModal by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = if (isTermsModal || isPolicyModal) HaengshaGrey else Color.White),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(1) {
                Text(
                    modifier = Modifier.width(330.dp),
                    text = "개인정보 동의",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 30.sp
                )
                Spacer(modifier = Modifier.height(80.dp))
                Row(
                    modifier = Modifier.width(335.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.clickable {
                            isAllChecked = !isAllChecked
                            if (isAllChecked) {
                                isTermsChecked = true
                                isPolicyChecked = true
                            } else {
                                isTermsChecked = false
                                isPolicyChecked = false
                            }
                        }
                    ) { CheckBox(color = if (isAllChecked) ButtonBlue else ButtonGrey, size = 28) }
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        modifier = Modifier.width(220.dp),
                        text = "전체 동의",
                        fontFamily = poppins,
                        fontWeight = FontWeight.Medium,
                        fontSize = 24.sp
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider(modifier = Modifier.width(335.dp), color = HaengshaGrey)
                Spacer(modifier = Modifier.height(60.dp))
                Row(
                    modifier = Modifier.width(335.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.clickable {
                                isTermsChecked = !isTermsChecked
                                if (isAllChecked && !isTermsChecked) isAllChecked = false
                            }
                        ) {
                            CheckBox(
                                color = if (isTermsChecked) ButtonBlue else ButtonGrey,
                                size = 25
                            )
                        }
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(
                            modifier = Modifier.clickable { isTermsModal = true },
                            text = buildAnnotatedString {
                                withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                                    append(
                                        "서비스 이용약관 (필수)"
                                    )
                                }
                                append(" ")
                                withStyle(SpanStyle(color = Color.Red)) { append("*") }
                            },
                            fontFamily = poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(60.dp))
                Row(
                    modifier = Modifier.width(335.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.clickable {
                                isPolicyChecked = !isPolicyChecked
                                if (isAllChecked && !isPolicyChecked) isAllChecked = false
                            }
                        ) {
                            CheckBox(
                                color = if (isPolicyChecked) ButtonBlue else ButtonGrey,
                                size = 25
                            )
                        }
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(
                            modifier = Modifier.clickable { isPolicyModal = true },
                            text = buildAnnotatedString {
                                withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                                    append(
                                        "개인정보 수집 및 처리 방침 (필수)"
                                    )
                                }
                                append(" ")
                                withStyle(SpanStyle(color = Color.Red)) { append("*") }
                            },
                            fontFamily = poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp
                        )
                    }
                }

                if (isTermsChecked && isPolicyChecked) isAllChecked = true

                Spacer(modifier = Modifier.height(130.dp))
                if (isTermsChecked && isPolicyChecked) {
                    CommonBlueButton(
                        text = "동의 후 회원가입",
                        onClick = {
                            loginApiViewModel.signupRegister(
                                email = signupUiState.email,
                                password = signupUiState.password,
                                nickname = signupUiState.nickname,
                                role = "User",
                                major = signupUiState.major,
                                grade = signupUiState.grade,
                                interest = signupUiState.interest
                            )
                        }
                    )
                } else CommonGreyButton(text = "동의 후 회원가입")
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
        if (isTermsModal || isPolicyModal) {
            // TODO Dialog로 바꿔서 UiComponent.kt로 옮기기
            LazyColumn(
                modifier = Modifier
                    .size(width = 340.dp, height = 580.dp)
                    .shadow(elevation = 10.dp, shape = RoundedCornerShape(5.dp))
                    .background(Color.White)
                    .zIndex(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(1) {
                    if (isTermsModal) AgreementModal(text = "서비스 이용약관 (필수)")
                    else AgreementModal(text = "개인정보 수집 및 처리 방침 (필수)")
                    Box(
                        modifier = Modifier
                            .size(width = 300.dp, height = 1.dp)
                            .background(color = Color(0xFFF1F1F1))
                    )
                    Box(
                        modifier = Modifier
                            .size(width = 300.dp, height = 60.dp)
                            .clickable {
                                isTermsModal = false
                                isPolicyModal = false
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "닫기",
                            fontFamily = poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            textDecoration = TextDecoration.Underline
                        )
                    }
                }
            }
        }
    }

    when (loginUiState) {
        is LoginApiUiState.Success -> {
            signupStateReset()
            loginNavController.navigate(LoginRoute.SignupComplete.route) {
                popUpTo(LoginRoute.Login.route) { inclusive = false }
            }
        }

        is LoginApiUiState.HttpError -> {
            Toasty
                .error(
                    loginContext,
                    loginUiState.message,
                    Toast.LENGTH_SHORT,
                    true
                )
                .show()
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
        }

        is LoginApiUiState.Loading -> {
            /* Loading State, may add some loading UI */
        }

        else -> {
            /* Other Success State, do nothing */
        }
    }
}

@Composable
fun AgreementModal(text: String) {
    LazyColumn(
        modifier = Modifier
            .size(width = 340.dp, height = 520.dp)
            .padding(top = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(1) {
            Text(
                modifier = Modifier.width(300.dp),
                text = text,
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(15.dp))
            Box(
                modifier = Modifier
                    .size(width = 300.dp, height = 420.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(color = Color(0xFFF1F1F1))
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp)
                ) {
                    items(1) {
                        if (text == "서비스 이용약관 (필수)") TermsOfUseModalText()
                        else PrivacyPolicyModalText()
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SignupTermsScreenPreview() {
//    SignupTermsScreen(rememberNavController()) {}
//}