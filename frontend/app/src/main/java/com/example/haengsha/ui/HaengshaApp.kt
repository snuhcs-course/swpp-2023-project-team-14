package com.example.haengsha.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.haengsha.model.route.MainRoute
import com.example.haengsha.model.uiState.login.LoginUiState
import com.example.haengsha.model.viewModel.login.LoginViewModel
import com.example.haengsha.ui.screens.dashBoard.Board
import com.example.haengsha.ui.screens.favorite.Favorite
import com.example.haengsha.ui.screens.home.Home
import com.example.haengsha.ui.screens.login.Login
import com.example.haengsha.ui.screens.setting.Setting

@Composable
fun HaengshaApp() {
    val mainNavController = rememberNavController()

    NavHost(
        navController = mainNavController,
        startDestination = MainRoute.Login.route
    ) {
        composable(MainRoute.Login.route) {
            Login(mainNavController = mainNavController)
        }
        composable(MainRoute.Home.route) {
            Home(mainNavController = mainNavController)
        }
        composable(MainRoute.Dashboard.route) {
            Board(mainNavController = mainNavController)
        }
        composable(MainRoute.Favorite.route) {
            Favorite(mainNavController = mainNavController)
        }
        composable(MainRoute.Setting.route) {
            Setting(mainNavController = mainNavController)
        }
    }
}

