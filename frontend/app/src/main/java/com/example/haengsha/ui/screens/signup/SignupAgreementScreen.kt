package com.example.haengsha.ui.screens.signup

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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.haengsha.ui.theme.ButtonBlue
import com.example.haengsha.ui.theme.ButtonGrey
import com.example.haengsha.ui.theme.HaengshaGrey
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.CommonBlueButton
import com.example.haengsha.ui.uiComponents.CommonGreyButton
import com.example.haengsha.ui.uiComponents.checkBox

@Composable
fun SignupAgreementScreen() {
    var isAllChecked by rememberSaveable { mutableStateOf(false) }
    var isTermsChecked by rememberSaveable { mutableStateOf(false) }
    var isPolicyChecked by rememberSaveable { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 90.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(1) {
            Text(
                modifier = Modifier.width(270.dp),
                text = "개인정보 동의",
                fontFamily = poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(45.dp))
            Row(
                modifier = Modifier
                    .width(270.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.width(220.dp),
                    text = "약관 전체 동의",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
                isAllChecked = checkBox(color = if (isAllChecked) ButtonBlue else ButtonGrey)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .size(width = 270.dp, height = 1.dp)
                    .background(color = HaengshaGrey)
            ) {}
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier
                    .width(270.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.width(220.dp),
                    text = buildAnnotatedString {
                        append("서비스 이용약관 (필수) ")
                        withStyle(SpanStyle(color = Color.Red)) { append("*") }
                    },
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
                isTermsChecked = checkBox(color = if (isTermsChecked) ButtonBlue else ButtonGrey)
            }
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier
                    .width(270.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.width(220.dp),
                    text = buildAnnotatedString {
                        append("개인정보 수집 및 처리 방침 (필수) ")
                        withStyle(SpanStyle(color = Color.Red)) { append("*") }
                    },
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
                isPolicyChecked = checkBox(color = if (isPolicyChecked) ButtonBlue else ButtonGrey)
            }

            if (isTermsChecked && isPolicyChecked) isAllChecked = true
            if (isAllChecked) {
                isTermsChecked = true; isPolicyChecked = true
            }

            Spacer(modifier = Modifier.height(80.dp))
            if (isAllChecked) {
                CommonBlueButton(
                    text = "동의 후 회원가입",
                    onClick = {
                        /*TODO=버튼 누르면 임시 저장했던 가입 정보 전부 DB에 저장*/
                    }
                )
            } else CommonGreyButton(text = "동의 후 회원가입")
            Spacer(modifier = Modifier.height(45.dp))
            Box(
                modifier = Modifier
                    .width(270.dp)
                    .height(20.dp)
                    .clickable {
                        /* TODO 이전 화면으로 돌아가기 */
                    }
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
}

@Composable
fun AgreementModal(text: String) {

}

@Preview(showBackground = true)
@Composable
fun SignupAgreementScreenPreview() {
    SignupAgreementScreen()
}