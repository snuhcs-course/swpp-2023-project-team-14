package com.example.haengsha.ui.uiComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomVerticalDivider(
    height: Int,
    color: Color
) {
    Box(
        modifier = Modifier
            .height(height.dp)
            .width(1.dp)
            .background(color)
    )
}

@Composable
fun CustomHorizontalDivider(color: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color)
    )
}

