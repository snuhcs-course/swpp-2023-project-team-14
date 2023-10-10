package com.example.haengsha.ui.uiComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.haengsha.R
import com.example.haengsha.ui.theme.poppins
import kotlinx.coroutines.delay

@Composable
fun Toast(message: String) {
    Box(
        modifier = Modifier
            .size(width = 220.dp, height = 40.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(color = colorResource(id = R.color.ToastBlue)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = message,
            color = Color.White,
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ShowToast(message: String, triggerToast: Boolean, toastKey: Int) {
    var toastState by remember { mutableStateOf(triggerToast) }

    LaunchedEffect(key1 = toastKey) {
        if (triggerToast) {
            toastState = true
            delay(1000)
            toastState = false
        }
    }

    if (toastState) {
        Toast(message = message)
    }
}

// 토스트 예제
@Composable
fun ToastPreview() {
    var triggerToast by remember { mutableStateOf(false) }
    var toastKey by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text("Hello World!")
        Spacer(modifier = Modifier.height(30.dp))
        Text(stringResource(id = R.string.long_text))
        Spacer(modifier = Modifier.height(100.dp))
        Button(onClick = { triggerToast = true; toastKey++ }
        ) { Text("show toast") }
        Spacer(modifier = Modifier.height(50.dp))
        ShowToast("비밀번호를 입력해주세요", triggerToast, toastKey)
    }
}