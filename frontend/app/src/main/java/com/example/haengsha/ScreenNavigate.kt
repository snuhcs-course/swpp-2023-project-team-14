package com.example.haengsha

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.haengsha.ui.screens.HomeScreen
import com.example.haengsha.ui.screens.LoginScreen
import com.example.haengsha.ui.screens.signup.SignupTypeScreen

@Composable
fun ScreenNavigate() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Login.route) {

        composable(Routes.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(Routes.SignUp.route) {
            SignupTypeScreen(navController = navController)
        }
        /*
        composable(Routes.ForgotPassword.route) { navBackStack ->
            ForgotPassword(navController = navController)
        }

        composable(Routes.Dashboard.route) {
            Dashboard(navController = navController)
        }*/

        composable(Routes.Home.route) {
            HomeScreen(navController = navController)
        }
    }
}

sealed class Routes(val route: String) {
    object SignUp : Routes("SignUp")
    object ForgotPassword : Routes("ForgotPassword")
    object Login : Routes("Login")
    object Dashboard : Routes("Dashboard")
    object Home : Routes("Home")
}