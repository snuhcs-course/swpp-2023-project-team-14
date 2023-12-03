package com.example.haengsha.ui.screens.login.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.haengsha.R
import com.example.haengsha.model.route.LoginRoute
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.CommonBlueButton

@Composable
fun SignupTypeScreen(
    loginNavController: NavHostController,
    loginNavBack: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val deviceWidth = configuration.screenWidthDp.dp
    val deviceHeight = configuration.screenHeightDp.dp

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = deviceHeight / 15),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(1) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = deviceWidth / 15),
                text = "개인 유저 가입하기",
                fontFamily = poppins,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = deviceWidth / 15),
                text = stringResource(id = R.string.personal_signup_guide),
                fontFamily = poppins,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(60.dp))
            CommonBlueButton(text = "개인 유저 가입하기") {
                loginNavController.navigate(LoginRoute.SignupEmail.route)
            }
            Spacer(modifier = Modifier.height(75.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = deviceWidth / 15),
                text = "단체 유저 가입하기",
                fontFamily = poppins,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = deviceWidth / 15),
                text = stringResource(id = R.string.organizer_signup_guide),
                fontFamily = poppins,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(60.dp))
            CommonBlueButton(text = "단체 유저 가입하기") {
                loginNavController.navigate(LoginRoute.SignupOrganizer.route)
            }
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
}

@Preview(showBackground = true)
@Composable
fun SignupTypeScreenPreview() {
    SignupTypeScreen(rememberNavController()) {}
}