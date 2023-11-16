package com.example.haengsha.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.haengsha.model.route.MainRoute
import com.example.haengsha.model.viewModel.UserViewModel
import com.example.haengsha.model.viewModel.board.BoardApiViewModel
import com.example.haengsha.ui.screens.dashBoard.Board
import com.example.haengsha.ui.screens.favorite.Favorite
import com.example.haengsha.ui.screens.home.Home
import com.example.haengsha.ui.screens.login.Login

@Composable
fun HaengshaApp() {
    val userViewModel: UserViewModel = viewModel()
    val userUiState by userViewModel.uiState.collectAsState()
    val boardApiViewModel: BoardApiViewModel = viewModel(factory = BoardApiViewModel.Factory)
    val mainNavController = rememberNavController()

    NavHost(
        navController = mainNavController,
        startDestination = MainRoute.Login.route
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
                mainNavController = mainNavController,
                boardApiViewModel = boardApiViewModel
            )
        }
        composable(MainRoute.Favorite.route) {
            Favorite(
                userUiState = userUiState,
                mainNavController = mainNavController,
                boardApiViewModel = boardApiViewModel
            )
        }
//        composable(MainRoute.Setting.route) {
//            Setting(
//                userViewModel = userViewModel,
//                userUiState = userUiState,
//                mainNavController = mainNavController
//            )
//        }
    }
}

