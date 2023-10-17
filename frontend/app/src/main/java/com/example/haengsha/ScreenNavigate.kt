package com.example.haengsha

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.haengsha.ui.screens.HomeScreen
import com.example.haengsha.ui.screens.LoginScreen
import com.example.haengsha.ui.screens.findPassword.FindPasswordCompleteScreen
import com.example.haengsha.ui.screens.findPassword.FindPasswordOrganizerScreen
import com.example.haengsha.ui.screens.findPassword.FindPasswordScreen
import com.example.haengsha.ui.screens.findPassword.PasswordResetScreen
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

        composable(Routes.FindPassword.route) { navBackStack ->
            FindPasswordScreen(navController = navController, context = LocalContext.current)
        }
        composable(Routes.FindPasswordOrganization.route) { navBackStack ->
            FindPasswordOrganizerScreen(navController = navController)
        }
        composable(Routes.PasswordReset.route) { navBackStack ->
            PasswordResetScreen(navController = navController, context = LocalContext.current)
        }
        composable(Routes.FindPasswordComplete.route) { navBackStack ->
            FindPasswordCompleteScreen(navController = navController)
        }
        /*
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
    object FindPassword : Routes("FindPassword")
    object FindPasswordOrganization : Routes("FindPasswordOrganization")
    object PasswordReset : Routes("PasswordReset")
    object FindPasswordComplete : Routes("FindPasswordComplete")
    object Login : Routes("Login")
    object Dashboard : Routes("Dashboard")
    object Home : Routes("Home")
}