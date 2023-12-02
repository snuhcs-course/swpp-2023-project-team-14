package com.example.haengsha.model.route

sealed class FavoriteRoute(val route: String) {
    data object Favorite : FavoriteRoute("Favorite")
    data object FavoriteDetail : FavoriteRoute("Details")
}