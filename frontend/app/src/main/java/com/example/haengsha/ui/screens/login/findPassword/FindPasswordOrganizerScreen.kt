package com.example.haengsha.ui.screens.login.findPassword

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.OrganizerFindPasswordInstructionText

@Composable
fun FindPasswordOrganizerScreen(loginNavBack: () -> Unit) {
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
                    .padding(start = deviceWidth / 10),
                text = "단체 인증",
                fontFamily = poppins,
                fontSize = 30.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(40.dp))
            OrganizerFindPasswordInstructionText()
            Spacer(modifier = Modifier.height(40.dp))
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
fun FindPasswordOrganizerScreenPreview() {
    FindPasswordOrganizerScreen {}
}