package com.example.haengsha.ui.screens.setting

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.haengsha.model.route.MainRoute
import com.example.haengsha.model.route.SettingRoute

@Composable
fun Setting(mainNavController: NavController) {
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