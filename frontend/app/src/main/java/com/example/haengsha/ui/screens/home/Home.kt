package com.example.haengsha.ui.screens.home

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.haengsha.model.route.HomeRoute
import com.example.haengsha.model.uiState.UserUiState
import com.example.haengsha.model.viewModel.NavigationViewModel
import com.example.haengsha.model.viewModel.board.BoardApiViewModel
import com.example.haengsha.model.viewModel.board.BoardViewModel
import com.example.haengsha.model.viewModel.home.HomeApiViewModel
import com.example.haengsha.model.viewModel.home.HomeViewModel
import com.example.haengsha.ui.screens.board.BoardDetailScreen
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun Home(
    homeViewModel: HomeViewModel,
    homeApiViewModel: HomeApiViewModel,
    boardApiViewModel: BoardApiViewModel,
    boardViewModel: BoardViewModel,
    navigationViewModel: NavigationViewModel,
    homeNavController: NavHostController = rememberNavController(),
    innerPadding: PaddingValues,
    userUiState: UserUiState
) {
    NavHost(
        navController = homeNavController,
        startDestination = HomeRoute.Home.route
    ) {
        composable(HomeRoute.Home.route) {
            navigationViewModel.updateRouteUiState("Home", HomeRoute.Home.route)
            HomeScreen(
                homeApiViewModel = homeApiViewModel,
                innerPadding = innerPadding,
                userUiState = userUiState,
                homeViewModel = homeViewModel,
                boardViewModel = boardViewModel,
                homeNavController = homeNavController,
            )
        }
        composable(
            HomeRoute.HomeDetail.route,
            enterTransition = {
                when (initialState.destination.route) {
                    "Home" -> {
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
                    "Home" -> {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    }

                    else -> null
                }
            }
        ) {
            navigationViewModel.updateRouteUiState("Home", HomeRoute.HomeDetail.route)
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

fun stringToDate(dateString: String): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    try {
        return LocalDate.parse(dateString, formatter)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return LocalDate.now()
}
