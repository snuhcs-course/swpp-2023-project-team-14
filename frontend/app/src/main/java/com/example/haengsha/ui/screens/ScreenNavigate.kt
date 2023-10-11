package com.example.haengsha.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.haengsha.Routes

@Composable
fun ScreenNavigate() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Login.route) {

        composable(Routes.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(Routes.SignUp.route) {
            SignupScreen(navController = navController)
        }
        /*
        composable(Routes.ForgotPassword.route) { navBackStack ->
            ForgotPassword(navController = navController)
        }

        composable(Routes.Dashboard.route) {
            Dashboard(navController = navController)
        }

        composable(Routes.Home.route) {
            Home(navController = navController)
        }*/
    }
}