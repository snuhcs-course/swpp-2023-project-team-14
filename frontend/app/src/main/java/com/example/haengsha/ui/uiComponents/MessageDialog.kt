package com.example.haengsha.ui.uiComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.haengsha.ui.theme.BackgroundGrey
import com.example.haengsha.ui.theme.md_theme_light_onPrimary
import com.example.haengsha.ui.theme.md_theme_light_tertiary
import com.example.haengsha.ui.theme.poppins

@Composable
fun ConfirmOnlyDialog(
    onDismissRequest: () -> Unit,
    onClick: () -> Unit,
    text: String
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .size(width = 320.dp, height = 160.dp)
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(15.dp))
                    .background(color = md_theme_light_onPrimary),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = text,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = md_theme_light_tertiary
                )
                Spacer(modifier = Modifier.height(21.dp))
                Box(
                    modifier = Modifier
                        .size(width = 320.dp, height = 1.dp)
                        .background(color = BackgroundGrey)
                )
                Spacer(modifier = Modifier.height(21.dp))
                ModalConfirmButton(
                    onClick = onClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessageDialogPreview() {
    ConfirmOnlyDialog(onDismissRequest = {}, onClick = {}, text = "HI")
}