package com.example.haengsha.model.route

sealed class MainRoute(val route: String) {
    object Login : MainRoute("Login")
    object Home : MainRoute("Home")
    object Favorite : MainRoute("Favorite")
    object Dashboard : MainRoute("Dashboard")
    object Setting : MainRoute("Setting")
}