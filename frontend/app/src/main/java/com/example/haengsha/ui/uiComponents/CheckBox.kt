package com.example.haengsha.ui.uiComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.haengsha.ui.theme.ButtonBlue
import com.example.haengsha.ui.theme.HaengshaBlue
import com.example.haengsha.ui.theme.HaengshaGrey

@Composable
fun CheckBox(color: Color, size: Int = 25) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(RoundedCornerShape(percent = 30))
            .background(color = color)
            .border(
                width = if (color == ButtonBlue) 0.dp else 1.dp,
                color = HaengshaGrey,
                shape = RoundedCornerShape(percent = 30)
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "check icon",
            tint = Color.White
        )
    }
}