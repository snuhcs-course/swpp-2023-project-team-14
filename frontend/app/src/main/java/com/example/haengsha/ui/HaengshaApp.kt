package com.example.haengsha.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.haengsha.model.route.MainRoute
import com.example.haengsha.model.viewModel.UserViewModel
import com.example.haengsha.ui.screens.dashBoard.Board
import com.example.haengsha.ui.screens.favorite.Favorite
import com.example.haengsha.ui.screens.home.Home
import com.example.haengsha.ui.screens.login.Login
import com.example.haengsha.ui.screens.setting.Setting

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HaengshaApp() {
    val userViewModel: UserViewModel = viewModel()
    val userUiState by userViewModel.uiState.collectAsState()
    val mainNavController = rememberNavController()

    NavHost(
        navController = mainNavController,
        // TODO 서버 없이 일단 board UI 확인
        // startDestination = MainRoute.Login.route
        startDestination = MainRoute.Dashboard.route
    ) {
        composable(MainRoute.Login.route) {
            Login(
                userViewModel = userViewModel,
                mainNavController = mainNavController
            )
        }
        composable(MainRoute.Home.route) {
            Home(
                userUiState = userUiState,
                mainNavController = mainNavController
            )
        }
        composable(MainRoute.Dashboard.route) {
            Board(
                userUiState = userUiState,
                mainNavController = mainNavController
            )
        }
        composable(MainRoute.Favorite.route) {
            Favorite(
                userUiState = userUiState,
                mainNavController = mainNavController
            )
        }
        composable(MainRoute.Setting.route) {
            Setting(
                userViewModel = userViewModel,
                userUiState = userUiState,
                mainNavController = mainNavController
            )
        }
    }
}

