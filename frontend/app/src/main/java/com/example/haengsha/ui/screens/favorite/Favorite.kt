package com.example.haengsha.ui.screens.favorite

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.haengsha.model.route.FavoriteRoute
import com.example.haengsha.model.uiState.UserUiState
import com.example.haengsha.model.viewModel.NavigationViewModel
import com.example.haengsha.model.viewModel.board.BoardApiViewModel
import com.example.haengsha.model.viewModel.board.BoardViewModel
import com.example.haengsha.ui.screens.board.BoardDetailScreen

@Composable
fun Favorite(
    innerPadding: PaddingValues,
    userUiState: UserUiState,
    boardApiViewModel: BoardApiViewModel,
    boardViewModel: BoardViewModel,
    navigationViewModel: NavigationViewModel,
    favoriteNavController: NavHostController = rememberNavController(),
    isTest: Boolean = false
) {
    NavHost(
        navController = favoriteNavController,
        startDestination = FavoriteRoute.Favorite.route
    ) {
        composable(FavoriteRoute.Favorite.route) {
            navigationViewModel.updateRouteUiState("Favorite", FavoriteRoute.Favorite.route)
            FavoriteScreen(
                innerPadding = innerPadding,
                boardApiViewModel = boardApiViewModel,
                boardViewModel = boardViewModel,
                favoriteNavController = favoriteNavController,
                userUiState = userUiState,
                isTest = isTest
            )
        }
        composable(
            FavoriteRoute.FavoriteDetail.route,
            enterTransition = {
                when (initialState.destination.route) {
                    "Favorite" -> {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )
                    }

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    "Favorite" -> {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    }

                    else -> null
                }
            }
        ) {
            navigationViewModel.updateRouteUiState("Favorite", FavoriteRoute.FavoriteDetail.route)
            BoardDetailScreen(
                innerPadding = innerPadding,
                boardApiViewModel = boardApiViewModel,
                boardViewModel = boardViewModel,
                userUiState = userUiState,
                eventId = boardViewModel.eventId.value
            )
        }
    }
}