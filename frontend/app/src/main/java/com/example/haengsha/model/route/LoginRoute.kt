package com.example.haengsha.model.route

sealed class LoginRoute(val route: String) {
    object Login : LoginRoute("Login")
    object FindPassword : LoginRoute("FindPassword")
    object FindPasswordOrganizer : LoginRoute("FindPasswordOrganizer")
    object FindPasswordReset : LoginRoute("FindPasswordReset")
    object FindPasswordComplete : LoginRoute("FindPasswordComplete")
    object SignupType : LoginRoute("SignupType")
    object SignupEmail : LoginRoute("SignupEmail")
    object SignupOrganizer : LoginRoute("SignupOrganizer")
    object SignupPassword : LoginRoute("SignupPassword")
    object SignupUserInfo : LoginRoute("SignupUserInfo")
    object SignupTerms : LoginRoute("SignupTerms")
    object SignupComplete : LoginRoute("SignupComplete")
}