package com.example.haengsha.ui.screens.signup

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.haengsha.R
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.CommonBlueButton

@Composable
fun SignupTypeScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(1) {
            Text(
                modifier = Modifier.width(300.dp),
                text = "개인 유저 가입하기",
                fontFamily = poppins,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.width(300.dp),
                text = stringResource(id = R.string.personal_signup_guide),
                fontFamily = poppins,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(40.dp))
            CommonBlueButton(text = "개인 유저 가입하기") {/* TODO 이메일 인증 화면 넘어가기 */ }
            Spacer(modifier = Modifier.height(75.dp))
            Text(
                modifier = Modifier.width(300.dp),
                text = "단체 유저 가입하기",
                fontFamily = poppins,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.width(300.dp),
                text = stringResource(id = R.string.organizer_signup_guide),
                fontFamily = poppins,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(40.dp))
            CommonBlueButton(text = "단체 유저 가입하기") {/* TODO 단체 가입 화면 넘어가기 */ }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignupTypeScreenPreview() {
    SignupTypeScreen()
}