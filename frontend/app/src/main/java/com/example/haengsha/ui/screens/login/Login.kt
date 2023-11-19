package com.example.haengsha.ui.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.haengsha.model.route.LoginRoute
import com.example.haengsha.model.viewModel.UserViewModel
import com.example.haengsha.model.viewModel.board.BoardViewModel
import com.example.haengsha.model.viewModel.login.FindPasswordViewModel
import com.example.haengsha.model.viewModel.login.LoginApiViewModel
import com.example.haengsha.model.viewModel.login.SignupViewModel
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
fun Login(
    userViewModel: UserViewModel,
    boardViewModel: BoardViewModel,
    mainNavController: NavHostController
) {
    val loginApiViewModel: LoginApiViewModel = viewModel(factory = LoginApiViewModel.Factory)
    val loginUiState = loginApiViewModel.loginUiState
    val signupViewModel: SignupViewModel = viewModel()
    val signupUiState by signupViewModel.uiState.collectAsState()
    val findPasswordViewModel: FindPasswordViewModel = viewModel()
    val findPasswordUiState by findPasswordViewModel.uiState.collectAsState()
    val loginNavController = rememberNavController()
    val loginContext = LocalContext.current

    NavHost(
        navController = loginNavController,
        startDestination = LoginRoute.Login.route
    ) {
        composable(LoginRoute.Login.route) {
            LoginScreen(
                userViewModel = userViewModel,
                boardViewModel = boardViewModel,
                mainNavController = mainNavController,
                loginNavController = loginNavController,
                loginApiViewModel = loginApiViewModel,
                loginUiState = loginUiState,
                loginContext = loginContext
            )
        }

        composable(LoginRoute.FindPassword.route) {
            FindPasswordScreen(
                loginApiViewModel = loginApiViewModel,
                loginUiState = loginUiState,
                findPasswordEmailUpdate = { findPasswordViewModel.updateEmail(it) },
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
                loginApiViewModel = loginApiViewModel,
                loginUiState = loginUiState,
                findPasswordViewModel = findPasswordViewModel,
                findPasswordUiState = findPasswordUiState,
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
                loginApiViewModel = loginApiViewModel,
                loginUiState = loginUiState,
                signupEmailUpdate = { signupViewModel.updateEmail(it) },
                loginNavController = loginNavController,
                loginNavBack = { loginNavController.popBackStack() },
                loginContext = loginContext
            )
        }
        composable(LoginRoute.SignupPassword.route) {
            SignupPasswordSetScreen(
                signupPasswordUpdate = { signupViewModel.updatePassword(it) },
                loginNavController = loginNavController,
                loginNavBack = { loginNavController.popBackStack() },
                loginContext = loginContext
            )
        }
        composable(LoginRoute.SignupUserInfo.route) {
            SignupUserInfoScreen(
                checkNickname = { loginApiViewModel.checkNickname(it) },
                loginUiState = loginUiState,
                signupViewModel = signupViewModel,
                signupNickname = signupUiState.nickname,
                loginNavController = loginNavController,
                loginNavBack = { loginNavController.popBackStack() },
                loginContext = loginContext
            )
        }
        composable(LoginRoute.SignupTerms.route) {
            SignupTermsScreen(
                loginApiViewModel = loginApiViewModel,
                loginUiState = loginUiState,
                signupStateReset = { signupViewModel.resetSignupData() },
                signupUiState = signupUiState,
                loginNavController = loginNavController,
                loginNavBack = { loginNavController.popBackStack() },
                loginContext = loginContext
            )
        }
        composable(LoginRoute.SignupComplete.route) {
            SignupCompleteScreen(
                loginNavController = loginNavController
            )
        }
    }
}