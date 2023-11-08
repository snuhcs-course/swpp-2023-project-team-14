package com.example.haengsha.ui.screens.dashBoard

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.haengsha.model.route.BoardRoute
import com.example.haengsha.model.route.MainRoute
import com.example.haengsha.model.uiState.UserUiState
import com.example.haengsha.model.viewModel.board.BoardViewModel
import com.example.haengsha.ui.uiComponents.HaengshaBottomAppBar
import com.example.haengsha.ui.uiComponents.HaengshaTopAppBar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Board(
    userUiState: UserUiState,
    mainNavController: NavController
) {
    val boardNavController = rememberNavController()
    val backStackEntry by boardNavController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: BoardRoute.Dashboard.route
    val canNavigateBack = currentScreen == "Details"
    val boardViewModel: BoardViewModel = viewModel(factory = BoardViewModel.Factory)
    var eventId by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            HaengshaTopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = canNavigateBack,
                navigateBack = { boardNavController.popBackStack() }
            )
        },
        bottomBar = {
            HaengshaBottomAppBar(
                navigateFavorite = { mainNavController.navigate(MainRoute.Favorite.route) },
                navigateHome = {
                    mainNavController.navigate(MainRoute.Home.route)
                    boardNavController.navigate(BoardRoute.Dashboard.route) {
                        popUpTo(BoardRoute.Dashboard.route) { inclusive = false }
                    }
                },
                navigateBoard = {
                    mainNavController.navigate(MainRoute.Dashboard.route)
                    boardNavController.navigate(BoardRoute.Dashboard.route) {
                        popUpTo(BoardRoute.Dashboard.route) { inclusive = false }
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = boardNavController,
            startDestination = BoardRoute.Dashboard.route
        ) {
            composable(BoardRoute.Dashboard.route) {
                eventId = boardScreen(
                    innerPadding = innerPadding,
                    boardViewModel = boardViewModel,
                    boardNavController = boardNavController
                )
            }
            composable(BoardRoute.BoardDetail.route) {
                BoardDetailScreen(
                    innerPadding = innerPadding,
                    boardViewModel = boardViewModel,
                    userToken = userUiState.token,
                    eventId = eventId
                )
            }
        }
    }
}