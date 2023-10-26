package com.example.haengsha.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.haengsha.model.uiState.UserUiState

@Composable
fun Home(userUiState: UserUiState, mainNavController: NavController) {
    HomeScreen(userUiState = userUiState)
}

@Composable
fun HomeScreen(userUiState: UserUiState) {
    /* TODO 여기에 홈 UI 넣기 */
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(text = "This is HomeScreen")
            Text(text = "User token: ${userUiState.token}")
        }
    }
}