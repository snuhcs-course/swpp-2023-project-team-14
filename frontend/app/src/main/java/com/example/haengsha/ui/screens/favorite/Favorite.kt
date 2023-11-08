package com.example.haengsha.ui.screens.favorite

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.example.haengsha.model.route.FavoriteRoute
import com.example.haengsha.model.route.MainRoute
import com.example.haengsha.model.uiState.UserUiState
import com.example.haengsha.model.viewModel.board.BoardViewModel
import com.example.haengsha.ui.screens.dashBoard.BoardDetailScreen
import com.example.haengsha.ui.screens.dashBoard.boardScreen
import com.example.haengsha.ui.uiComponents.HaengshaBottomAppBar
import com.example.haengsha.ui.uiComponents.HaengshaTopAppBar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Favorite(
    userUiState: UserUiState,
    boardViewModel: BoardViewModel,
    mainNavController: NavController
) {
    val favoriteNavController = rememberNavController()
    val backStackEntry by favoriteNavController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route ?: FavoriteRoute.FavoriteBoard.route
    val canNavigateBack = currentScreen == "Details"
    var eventId by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            HaengshaTopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = canNavigateBack,
                navigateBack = { /* Do thing */ }
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
            navController = favoriteNavController,
            startDestination = FavoriteRoute.FavoriteBoard.route
        ) {
            composable(FavoriteRoute.FavoriteBoard.route) {
                eventId = boardScreen(
                    innerPadding = innerPadding,
                    boardViewModel = boardViewModel,
                    boardNavController = favoriteNavController,
                    isFavorite = true,
                    userToken = userUiState.token
                )
            }
            composable(FavoriteRoute.FavoriteDetail.route) {
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