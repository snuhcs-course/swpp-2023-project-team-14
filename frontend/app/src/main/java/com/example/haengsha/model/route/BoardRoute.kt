package com.example.haengsha.model.route

sealed class BoardRoute(val route: String) {
    data object Board : BoardRoute("Board")
    data object BoardDetail : BoardRoute("Details")
    data object BoardPost : BoardRoute("Write")
}