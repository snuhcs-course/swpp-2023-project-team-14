package com.example.haengsha.instrumentedTest.navigationTest

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.rememberNavController
import androidx.navigation.testing.TestNavHostController
import com.example.haengsha.R
import com.example.haengsha.model.route.LoginRoute
import com.example.haengsha.model.uiState.login.LoginApiUiState
import com.example.haengsha.model.viewModel.UserViewModel
import com.example.haengsha.model.viewModel.board.BoardViewModel
import com.example.haengsha.model.viewModel.login.LoginApiViewModel
import com.example.haengsha.testConfig.assertCurrentRouteName
import com.example.haengsha.testConfig.onNodeWithTagForStringId
import com.example.haengsha.ui.screens.login.Login
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginScreenNavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var loginNavController: TestNavHostController
    private lateinit var mainNavController: NavHostController
    private lateinit var loginApiViewModel: LoginApiViewModel
    private lateinit var loginApiUiState: LoginApiUiState
    private lateinit var userViewModel: UserViewModel
    private lateinit var boardViewModel: BoardViewModel

    @Before
    fun setupMainNavHost() {
        composeTestRule.setContent {
            loginNavController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            mainNavController = rememberNavController()
            loginApiViewModel = viewModel(factory = LoginApiViewModel.Factory)
            loginApiUiState = loginApiViewModel.loginApiUiState
            userViewModel = UserViewModel()
            boardViewModel = BoardViewModel()

            Login(
                userViewModel = userViewModel,
                loginApiViewModel = loginApiViewModel,
                loginApiUiState = loginApiUiState,
                boardViewModel = boardViewModel,
                mainNavController = mainNavController,
                loginNavController = loginNavController,
                isTest = true
            )
        }
    }

    @Test
    fun loginNavController_verifyStartDestination() {
        loginNavController.assertCurrentRouteName(LoginRoute.Login.route)
    }

    @Test
    fun loginNavController_navigateToSignupTypeScreen() {
        navigateToSignupTypeScreen()
        loginNavController.assertCurrentRouteName(LoginRoute.SignupType.route)
    }

    @Test
    fun loginNavController_navigateToSignupOrganizerScreen() {
        navigateToSignupOrganizerScreen()
        loginNavController.assertCurrentRouteName(LoginRoute.SignupOrganizer.route)
    }

    @Test
    fun loginNavController_navigateToSignupEmailVerificationScreen() {
        navigateToSignupEmailVerificationScreen()
        loginNavController.assertCurrentRouteName(LoginRoute.SignupEmail.route)
    }

    @Test
    fun loginNavController_navigateToSignupPasswordScreen() {
        navigateToSignupPasswordScreen()
        loginNavController.assertCurrentRouteName(LoginRoute.SignupPassword.route)
    }

    @Test
    fun loginNavController_navigateToSignupUserInfoScreen() {
        navigateToSignupUserInfoScreen()
        loginNavController.assertCurrentRouteName(LoginRoute.SignupUserInfo.route)
    }

    @Test
    fun loginNavController_navigateToSignupTermsScreen() {
        navigateToSignupTermsScreen()
        loginNavController.assertCurrentRouteName(LoginRoute.SignupTerms.route)
    }

    @Test
    fun loginNavController_navigateToSignupCompleteScreen() {
        navigateToSignupCompleteScreen()
        loginNavController.assertCurrentRouteName(LoginRoute.SignupComplete.route)
    }

    @Test
    fun loginNavController_completeSignUp_navigateBackToLoginScreen() {
        completeSignUp_navigateBackToLoginScreen()
        loginNavController.assertCurrentRouteName(LoginRoute.Login.route)
    }

    @Test
    fun loginNavController_navigateToFindPasswordScreen() {
        navigateToFindPasswordScreen()
        loginNavController.assertCurrentRouteName(LoginRoute.FindPassword.route)
    }

    @Test
    fun loginNavController_navigateToFindPasswordOrganizerScreen() {
        navigateToFindPasswordOrganizerScreen()
        loginNavController.assertCurrentRouteName(LoginRoute.FindPasswordOrganizer.route)
    }

    @Test
    fun loginNavController_navigateToFindPasswordResetScreen() {
        navigateToFindPasswordResetScreen()
        loginNavController.assertCurrentRouteName(LoginRoute.FindPasswordReset.route)
    }

    @Test
    fun loginNavController_navigateToFindPasswordCompleteScreen() {
        navigateToFindPasswordCompleteScreen()
        loginNavController.assertCurrentRouteName(LoginRoute.FindPasswordComplete.route)
    }

    @Test
    fun loginNavController_completeResetPassword_navigateBackToLoginScreen() {
        completeResetPassword_navigateBackToLoginScreen()
        loginNavController.assertCurrentRouteName(LoginRoute.Login.route)
    }

    @Test
    fun loginNavController_navigateToSignupTypeScreen_navigateUp_navigateToLoginScreen() {
        navigateToSignupTypeScreen()
        performNavigateUp()
        loginNavController.assertCurrentRouteName(LoginRoute.Login.route)
    }

    @Test
    fun loginNavController_navigateToSignupOrganizerScreen_navigateUp_navigateToSignupTypeScreen() {
        navigateToSignupOrganizerScreen()
        performNavigateUp()
        loginNavController.assertCurrentRouteName(LoginRoute.SignupType.route)
    }

    @Test
    fun loginNavController_navigateToSignupEmailVerificationScreen_navigateUp_navigateToSignupTypeScreen() {
        navigateToSignupEmailVerificationScreen()
        performNavigateUp()
        loginNavController.assertCurrentRouteName(LoginRoute.SignupType.route)
    }

    @Test
    fun loginNavController_navigateToSignupPasswordScreen_navigateUp_navigateToSignupEmailVerificationScreen() {
        navigateToSignupPasswordScreen()
        performNavigateUp()
        loginNavController.assertCurrentRouteName(LoginRoute.SignupEmail.route)
    }

    @Test
    fun loginNavController_navigateToSignupUserInfoScreen_navigateUp_navigateToSignupPasswordScreen() {
        navigateToSignupUserInfoScreen()
        performNavigateUp()
        loginNavController.assertCurrentRouteName(LoginRoute.SignupPassword.route)
    }

    @Test
    fun loginNavController_navigateToSignupTermsScreen_navigateUp_navigateToSignupUserInfoScreen() {
        navigateToSignupTermsScreen()
        performNavigateUp()
        loginNavController.assertCurrentRouteName(LoginRoute.SignupUserInfo.route)
    }

    @Test
    fun loginNavController_navigateToFindPasswordScreen_navigateUp_navigateToLoginScreen() {
        navigateToFindPasswordScreen()
        performNavigateUp()
        loginNavController.assertCurrentRouteName(LoginRoute.Login.route)
    }

    @Test
    fun loginNavController_navigateToFindPasswordOrganizerScreen_navigateUp_navigateToFindPasswordScreen() {
        navigateToFindPasswordOrganizerScreen()
        performNavigateUp()
        loginNavController.assertCurrentRouteName(LoginRoute.FindPassword.route)
    }

    @Test
    fun loginNavController_navigateToFindPasswordResetScreen_navigateUp_navigateToFindPasswordScreen() {
        navigateToFindPasswordResetScreen()
        performNavigateUp()
        loginNavController.assertCurrentRouteName(LoginRoute.FindPassword.route)
    }

    private fun navigateToSignupTypeScreen() {
        composeTestRule.onNodeWithText("회원가입하기").performClick()
    }

    private fun navigateToSignupOrganizerScreen() {
        navigateToSignupTypeScreen()
        composeTestRule.onNodeWithTag("단체 유저 가입하기").performScrollTo().performClick()
    }

    private fun navigateToSignupEmailVerificationScreen() {
        navigateToSignupTypeScreen()
        composeTestRule.onNodeWithTag("개인 유저 가입하기").performClick()
    }

    private fun navigateToSignupPasswordScreen() {
        navigateToSignupEmailVerificationScreen()
        composeTestRule.onNodeWithTag("다음").performClick()
    }

    private fun navigateToSignupUserInfoScreen() {
        navigateToSignupPasswordScreen()
        composeTestRule.onNodeWithTagForStringId(R.string.passwordSetField)
            .performTextInput("qwer1234")
        composeTestRule.onNodeWithTagForStringId(R.string.passwordCheckTextField)
            .performTextInput("qwer1234")
        composeTestRule.onNodeWithTag("다음").performClick()
    }

    private fun navigateToSignupTermsScreen() {
        navigateToSignupUserInfoScreen()
        composeTestRule.onNodeWithTag("다음").performScrollTo().performClick()
    }

    private fun navigateToSignupCompleteScreen() {
        navigateToSignupTermsScreen()
        composeTestRule.onNodeWithTag("전체 동의").performClick()
        composeTestRule.onNodeWithTag("동의 후 회원가입").performClick()
    }

    private fun completeSignUp_navigateBackToLoginScreen() {
        navigateToSignupCompleteScreen()
        composeTestRule.onNodeWithTag("로그인 하러 가기").performClick()
    }

    private fun navigateToFindPasswordScreen() {
        composeTestRule.onNodeWithText("비밀번호 찾기").performClick()
    }

    private fun navigateToFindPasswordOrganizerScreen() {
        navigateToFindPasswordScreen()
        composeTestRule.onNodeWithText("단체 계정 비밀번호 찾기").performClick()
    }

    private fun navigateToFindPasswordResetScreen() {
        navigateToFindPasswordScreen()
        composeTestRule.onNodeWithTag("다음").performClick()
    }

    private fun navigateToFindPasswordCompleteScreen() {
        navigateToFindPasswordResetScreen()
        composeTestRule.onNodeWithTagForStringId(R.string.passwordSetField)
            .performTextInput("qwer1234")
        composeTestRule.onNodeWithTagForStringId(R.string.passwordCheckTextField)
            .performTextInput("qwer1234")
        composeTestRule.onNodeWithTag("변경 완료하기").performClick()
    }

    private fun completeResetPassword_navigateBackToLoginScreen() {
        navigateToFindPasswordCompleteScreen()
        composeTestRule.onNodeWithTag("로그인 하러 가기").performClick()
    }

    private fun performNavigateUp() {
        composeTestRule.onNodeWithText("이전 화면으로 돌아가기").performScrollTo().performClick()
    }
}