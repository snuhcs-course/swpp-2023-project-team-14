package com.example.haengsha.model.route

sealed class LoginRoute(val route: String) {
    data object Login : LoginRoute("Login")
    data object FindPassword : LoginRoute("FindPassword")
    data object FindPasswordOrganizer : LoginRoute("FindPasswordOrganizer")
    data object FindPasswordReset : LoginRoute("FindPasswordReset")
    data object FindPasswordComplete : LoginRoute("FindPasswordComplete")
    data object SignupType : LoginRoute("SignupType")
    data object SignupEmail : LoginRoute("SignupEmail")
    data object SignupOrganizer : LoginRoute("SignupOrganizer")
    data object SignupPassword : LoginRoute("SignupPassword")
    data object SignupUserInfo : LoginRoute("SignupUserInfo")
    data object SignupTerms : LoginRoute("SignupTerms")
    data object SignupComplete : LoginRoute("SignupComplete")
}