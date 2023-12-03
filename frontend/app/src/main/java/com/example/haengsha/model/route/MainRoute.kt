package com.example.haengsha.model.route

sealed class MainRoute(val route: String) {
    data object Login : MainRoute("Login")
    data object Home : MainRoute("Home")
    data object Favorite : MainRoute("Favorite")
    data object Board : MainRoute("Board")
    data object Setting : MainRoute("Setting")
}