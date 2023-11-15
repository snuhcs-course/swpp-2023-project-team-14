package com.example.haengsha.ui.screens.dashBoard

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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

@Composable
fun Board(
    userUiState: UserUiState,
    boardViewModel: BoardViewModel,
    mainNavController: NavController
) {
    val boardNavController = rememberNavController()
    val backStackEntry by boardNavController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: BoardRoute.Dashboard.route
    val canNavigateBack = currentScreen != "Board"
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
                navigateHome = { mainNavController.navigate(MainRoute.Home.route) },
                navigateBoard = { mainNavController.navigate(MainRoute.Dashboard.route) }
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
                    boardNavController = boardNavController,
                    isFavorite = false,
                    userUiState = userUiState
                )
            }
            composable(BoardRoute.BoardDetail.route) {
                BoardDetailScreen(
                    innerPadding = innerPadding,
                    boardViewModel = boardViewModel,
                    userUiState = userUiState,
                    eventId = eventId
                )
            }
            composable(BoardRoute.BoardPost.route) {
                BoardPostScreen(
                    innerPadding = innerPadding,
                    boardViewModel = boardViewModel,
                    boardNavController = boardNavController,
                    userUiState = userUiState
                )
            }
        }
    }
}