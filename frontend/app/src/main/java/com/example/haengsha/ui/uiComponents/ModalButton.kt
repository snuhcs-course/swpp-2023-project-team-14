package com.example.haengsha.ui.uiComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.example.haengsha.ui.theme.ModalButtonBlue
import com.example.haengsha.ui.theme.ModalButtonRed
import com.example.haengsha.ui.theme.poppins

@Composable
fun ModalConfirmButton(
    type: String = "확인",
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(width = 120.dp, height = 30.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(15.dp))
            .background(color = ModalButtonBlue)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = type,
            color = Color.White,
            fontFamily = poppins,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ModalCancelButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(width = 120.dp, height = 30.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(15.dp))
            .background(color = ModalButtonRed)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = "취소",
            color = Color.White,
            fontFamily = poppins,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ModalButtonPreview() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ModalCancelButton { /**/ }
        Spacer(Modifier.width(20.dp))
        ModalConfirmButton { /**/ }
    }
}