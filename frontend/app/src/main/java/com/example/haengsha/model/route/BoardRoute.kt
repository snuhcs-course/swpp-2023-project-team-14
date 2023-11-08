package com.example.haengsha.model.route

sealed class BoardRoute(val route: String) {
    object Dashboard : BoardRoute("Board")
    object BoardDetail : BoardRoute("Details")
}