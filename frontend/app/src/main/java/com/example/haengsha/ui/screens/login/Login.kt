package com.example.haengsha.ui.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.haengsha.model.route.LoginRoute
import com.example.haengsha.ui.screens.login.findPassword.FindPasswordCompleteScreen
import com.example.haengsha.ui.screens.login.findPassword.FindPasswordOrganizerScreen
import com.example.haengsha.ui.screens.login.findPassword.FindPasswordScreen
import com.example.haengsha.ui.screens.login.findPassword.PasswordResetScreen
import com.example.haengsha.ui.screens.login.signup.SignUpOrganizerScreen
import com.example.haengsha.ui.screens.login.signup.SignupCompleteScreen
import com.example.haengsha.ui.screens.login.signup.SignupEmailVerificationScreen
import com.example.haengsha.ui.screens.login.signup.SignupPasswordSetScreen
import com.example.haengsha.ui.screens.login.signup.SignupTermsScreen
import com.example.haengsha.ui.screens.login.signup.SignupTypeScreen
import com.example.haengsha.ui.screens.login.signup.SignupUserInfoScreen

@Composable
fun Login(mainNavController: NavHostController) {
    val loginNavController = rememberNavController()
    val loginContext = LocalContext.current

    NavHost(
        navController = loginNavController,
        startDestination = LoginRoute.Login.route
    ) {
        composable(LoginRoute.Login.route) {
            LoginScreen(
                mainNavController = mainNavController,
                loginNavController = loginNavController,
                loginContext = loginContext
            )
        }

        composable(LoginRoute.FindPassword.route) {
            FindPasswordScreen(
                loginNavController = loginNavController,
                loginNavBack = { loginNavController.popBackStack() },
                loginContext = loginContext
            )
        }
        composable(LoginRoute.FindPasswordOrganizer.route) {
            FindPasswordOrganizerScreen(
                loginNavBack = { loginNavController.popBackStack() }
            )
        }
        composable(LoginRoute.FindPasswordReset.route) {
            PasswordResetScreen(
                loginNavController = loginNavController,
                loginNavBack = { loginNavController.popBackStack() },
                loginContext = loginContext
            )
        }
        composable(LoginRoute.FindPasswordComplete.route) {
            FindPasswordCompleteScreen(
                loginNavController = loginNavController
            )
        }

        composable(LoginRoute.SignupType.route) {
            SignupTypeScreen(
                loginNavController = loginNavController,
                loginNavBack = { loginNavController.popBackStack() }
            )
        }
        composable(LoginRoute.SignupOrganizer.route) {
            SignUpOrganizerScreen(
                loginNavBack = { loginNavController.popBackStack() }
            )
        }
        composable(LoginRoute.SignupEmail.route) {
            SignupEmailVerificationScreen(
                loginNavController = loginNavController,
                loginNavBack = { loginNavController.popBackStack() },
                loginContext = loginContext
            )
        }
        composable(LoginRoute.SignupPassword.route) {
            SignupPasswordSetScreen(
                loginNavController = loginNavController,
                loginNavBack = { loginNavController.popBackStack() },
                loginContext = loginContext
            )
        }
        composable(LoginRoute.SignupUserInfo.route) {
            SignupUserInfoScreen(
                loginNavController = loginNavController,
                loginNavBack = { loginNavController.popBackStack() },
                loginContext = loginContext
            )
        }
        composable(LoginRoute.SignupTerms.route) {
            SignupTermsScreen(
                loginNavController = loginNavController,
                loginNavBack = { loginNavController.popBackStack() }
            )
        }
        composable(LoginRoute.SignupComplete.route) {
            SignupCompleteScreen(
                loginNavController = loginNavController
            )
        }
    }
}