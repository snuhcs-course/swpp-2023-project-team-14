package com.example.haengsha.model.route

sealed class SettingRoute(val route: String) {
    object Setting : SettingRoute("Setting")
}
