package com.example.haengsha.ui.uiComponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.haengsha.ui.theme.poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HaengshaTopAppBar(
    currentScreen: String,
    canNavigateBack: Boolean,
    navigateBack: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = currentScreen,
                fontFamily = poppins,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = { navigateBack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Navigate Back Icon"
                    )
                }
            }
        },
//        TODO setting 화면 가능하면 추가
//        actions = {
//            if (!isSettingScreen) {
//                Icon(
//                    imageVector = ImageVector.vectorResource(id = R.drawable.setting_page_icon),
//                    contentDescription = "setting page icon",
//                    modifier = Modifier.clickable { /* TODO 세팅화면 접속 */ }
//                )
//            }
//        }
    )
}