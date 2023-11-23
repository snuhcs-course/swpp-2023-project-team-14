package com.example.haengsha.ui.screens.login.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.haengsha.model.route.LoginRoute
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.CommonBlueButton

@Composable
fun SignupCompleteScreen(
    loginNavController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 240.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.width(240.dp),
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0089ED)
                    )
                ) { append("행샤") }
                append(" 가입이\n\n\n완료되었습니다!")
            },
            fontFamily = poppins,
            fontSize = 30.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(120.dp))
        CommonBlueButton(text = "로그인 하러 가기") {
            loginNavController.navigate(LoginRoute.Login.route) {
                popUpTo(LoginRoute.Login.route) { inclusive = true }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignupCompleteScreen() {
    SignupCompleteScreen(rememberNavController())
}