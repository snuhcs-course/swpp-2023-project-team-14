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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.haengsha.ui.theme.ButtonBlue
import com.example.haengsha.ui.theme.ButtonGrey
import com.example.haengsha.ui.theme.HaengshaGrey
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.CommonBlueButton
import com.example.haengsha.ui.uiComponents.CommonGreyButton
import com.example.haengsha.ui.uiComponents.PrivacyPolicyModalText
import com.example.haengsha.ui.uiComponents.TermsOfUseModalText

@Composable
fun SignupAgreementScreen() {
    var isAllChecked by rememberSaveable { mutableStateOf(false) }
    var isTermsChecked by rememberSaveable { mutableStateOf(false) }
    var isPolicyChecked by rememberSaveable { mutableStateOf(false) }
    var isTermsModal by rememberSaveable { mutableStateOf(false) }
    var isPolicyModal by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = if (isTermsModal || isPolicyModal) HaengshaGrey else Color.White),
        contentAlignment = Alignment.Center
    ) {
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
                    modifier = Modifier.width(270.dp),
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
                    ) { CheckBox(color = if (isAllChecked) ButtonBlue else ButtonGrey) }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        modifier = Modifier.width(220.dp),
                        text = "전체 동의",
                        fontFamily = poppins,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .size(width = 270.dp, height = 1.dp)
                        .background(color = HaengshaGrey)
                )
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier.width(270.dp),
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
                            CheckBox(color = if (isTermsChecked) ButtonBlue else ButtonGrey)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = buildAnnotatedString {
                                append("서비스 이용약관 (필수) ")
                                withStyle(SpanStyle(color = Color.Red)) { append("*") }
                            },
                            fontFamily = poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = 13.sp
                        )
                    }
                    Box(
                        modifier = Modifier.clickable { isTermsModal = true }
                    ) {
                        Text(
                            text = "보기",
                            fontFamily = poppins,
                            fontWeight = FontWeight.Normal,
                            textDecoration = TextDecoration.Underline,
                            fontSize = 10.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    modifier = Modifier.width(270.dp),
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
                            CheckBox(color = if (isPolicyChecked) ButtonBlue else ButtonGrey)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = buildAnnotatedString {
                                append("개인정보 수집 및 처리 방침 (필수) ")
                                withStyle(SpanStyle(color = Color.Red)) { append("*") }
                            },
                            fontFamily = poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = 13.sp
                        )
                    }
                    Box(
                        modifier = Modifier.clickable { isPolicyModal = true }
                    ) {
                        Text(
                            text = "보기",
                            fontFamily = poppins,
                            fontWeight = FontWeight.Normal,
                            textDecoration = TextDecoration.Underline,
                            fontSize = 10.sp
                        )
                    }
                }

                if (isTermsChecked && isPolicyChecked) isAllChecked = true

                Spacer(modifier = Modifier.height(80.dp))
                if (isTermsChecked && isPolicyChecked) {
                    CommonBlueButton(
                        text = "동의 후 회원가입",
                        onClick = {
                            /*TODO 버튼 누르면 임시 저장했던 가입 정보 전부 DB에 저장*/
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
        if (isTermsModal || isPolicyModal) {
            LazyColumn(
                modifier = Modifier
                    .size(width = 300.dp, height = 500.dp)
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
                            .size(width = 260.dp, height = 1.dp)
                            .background(color = Color(0xFFF1F1F1))
                    )
                    Box(
                        modifier = Modifier
                            .size(width = 260.dp, height = 40.dp)
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
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            textDecoration = TextDecoration.Underline
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CheckBox(color: Color) {
    Box(
        modifier = Modifier
            .size(25.dp)
            .background(color = color),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "check icon",
            tint = Color.White
        )
    }
}

@Composable
fun AgreementModal(text: String) {
    LazyColumn(
        modifier = Modifier
            .size(width = 300.dp, height = 460.dp)
            .padding(top = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(1) {
            Text(
                modifier = Modifier.width(260.dp),
                text = text,
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(15.dp))
            Box(
                modifier = Modifier
                    .size(width = 260.dp, height = 380.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(color = Color(0xFFF1F1F1))
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
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

@Preview(showBackground = true)
@Composable
fun SignupAgreementScreenPreview() {
    SignupAgreementScreen()
}