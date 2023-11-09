package com.example.haengsha.model.route

sealed class FavoriteRoute(val route: String) {
    object FavoriteBoard : FavoriteRoute("Favorite")
    object FavoriteDetail : FavoriteRoute("Details")
}