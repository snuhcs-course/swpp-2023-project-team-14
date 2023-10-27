package com.example.haengsha.model.route

sealed class BoardRoute(val route: String) {
    object Board : BoardRoute("board")
}
