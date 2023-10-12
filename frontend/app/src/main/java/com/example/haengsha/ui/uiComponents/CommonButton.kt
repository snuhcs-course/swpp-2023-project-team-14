package com.example.haengsha.ui.uiComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.haengsha.ui.theme.ButtonBlue
import com.example.haengsha.ui.theme.ButtonGrey
import com.example.haengsha.ui.theme.poppins

@Composable
fun CommonBlueButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(width = 270.dp, height = 50.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(10.dp))
            .background(color = ButtonBlue)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = text,
            color = Color.White,
            fontFamily = poppins,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CommonGreyButton(text: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(width = 270.dp, height = 50.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(10.dp))
            .background(color = ButtonGrey)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = text,
            color = Color.White,
            fontFamily = poppins,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CommonButtonPreview() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CommonBlueButton("다음") {/**/ }
        CommonGreyButton("비활성화") {/**/ }
    }
}