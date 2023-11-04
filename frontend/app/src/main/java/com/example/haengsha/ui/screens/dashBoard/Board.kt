package com.example.haengsha.ui.screens.dashBoard

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.haengsha.model.uiState.UserUiState
import com.example.haengsha.ui.uiComponents.HaengshaBottomAppBar
import com.example.haengsha.ui.uiComponents.HaengshaTopAppBar

@Composable
fun Board(userUiState: UserUiState, mainNavController: NavController) {
    val currentScreen = "Board"
    val canNavigateBack = true

    Scaffold(
        topBar = {
            HaengshaTopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = canNavigateBack,
                navigateBack = { /*TODO*/ }
            )
        },
        bottomBar = {
            HaengshaBottomAppBar(
                navigateFavorite = { /*TODO*/ },
                navigateHome = { /*TODO*/ },
                navigateBoard = { /*TODO*/ }
            )
        }
    ) { innerPadding ->
        //BoardScreen(innerPadding)
        BoardDetailScreen(innerPadding)
    }
}