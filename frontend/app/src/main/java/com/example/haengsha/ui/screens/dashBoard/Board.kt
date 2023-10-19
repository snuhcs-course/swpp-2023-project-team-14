package com.example.haengsha.ui.screens.dashBoard

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.haengsha.model.route.BoardRoute

@Composable
fun Board(mainNavController: NavController) {
    val boardNavController = rememberNavController()

    NavHost(
        navController = boardNavController,
        startDestination = BoardRoute.Board.route
    ) {
        composable(BoardRoute.Board.route) {
            BoardScreen(boardNavController = boardNavController)
        }
    }
}