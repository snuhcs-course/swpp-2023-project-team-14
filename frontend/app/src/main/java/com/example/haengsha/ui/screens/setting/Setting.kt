package com.example.haengsha.ui.screens.setting

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.haengsha.model.route.SettingRoute
import com.example.haengsha.model.uiState.UserUiState
import com.example.haengsha.model.viewModel.UserViewModel

@Composable
fun Setting(
    userViewModel: UserViewModel,
    userUiState: UserUiState,
    mainNavController: NavController
) {
    val settingNavController = rememberNavController()
    val settingBackStackEntry = settingNavController.currentBackStackEntryAsState()

    NavHost(
        navController = settingNavController,
        startDestination = SettingRoute.Setting.route
    ) {
        composable(SettingRoute.Setting.route) {
            SettingScreen(settingNavController = settingNavController)
        }
    }
}