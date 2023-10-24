package com.example.haengsha.ui.screens.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.haengsha.model.uiState.UserUiState

@Composable
fun Favorite(userUiState: UserUiState, mainNavController: NavController) {
    FavoriteScreen()
}

@Composable
fun FavoriteScreen() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "This is FavoriteScreen")
    }
}