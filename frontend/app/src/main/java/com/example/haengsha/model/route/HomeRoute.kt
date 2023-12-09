package com.example.haengsha.model.route

sealed class HomeRoute(val route: String) {
    data object Home : HomeRoute("Home")
    data object HomeDetail : HomeRoute("Details")
}