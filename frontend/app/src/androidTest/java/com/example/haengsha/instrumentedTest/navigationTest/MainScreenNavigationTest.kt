package com.example.haengsha.instrumentedTest.navigationTest

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.haengsha.R
import com.example.haengsha.model.route.MainRoute
import com.example.haengsha.testConfig.assertCurrentRouteName
import com.example.haengsha.testConfig.onNodeWithTagForStringId
import com.example.haengsha.ui.HaengshaApp
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainScreenNavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var mainNavController: TestNavHostController

    @Before
    fun setupMainNavHost() {
        composeTestRule.setContent {
            mainNavController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            HaengshaApp(mainNavController = mainNavController)
        }
    }

    @Test
    fun mainNavController_verifyStartDestination() {
        mainNavController.assertCurrentRouteName(MainRoute.Login.route)
    }

    @Test
    fun mainNavController_clickLogin_loginToHomeScreen() {
        loginToHomeScreen()
        mainNavController.assertCurrentRouteName(MainRoute.Home.route)
    }

    @Test
    fun mainNavController_clickBottomAppBarFavoriteButton_navigatesToFavoriteScreen() {
        navigateToFavoriteScreen()
        mainNavController.assertCurrentRouteName(MainRoute.Favorite.route)
    }

    @Test
    fun mainNavController_clickBottomAppBarBoardButton_navigatesToBoardScreen() {
        navigateToBoardScreen()
        mainNavController.assertCurrentRouteName(MainRoute.Board.route)
    }

    @Test
    fun mainNavController_clickBottomAppBarHomeButton_navigatesToHomeScreen() {
        navigateToHomeScreen()
        mainNavController.assertCurrentRouteName(MainRoute.Home.route)
    }

    @Test
    fun mainNavController_clickTopAppBarLogoutButton_logoutToLoginScreen() {
        loginToHomeScreen()
        performLogout()
        mainNavController.assertCurrentRouteName(MainRoute.Login.route)
    }

    private fun loginToHomeScreen() {
        composeTestRule.onNodeWithTagForStringId(R.string.suffixTextField)
            .performTextInput("user2@snu.ac.kr")
        composeTestRule.onNodeWithTagForStringId(R.string.passwordTextField)
            .performTextInput("user2")
        composeTestRule.onNodeWithText("로그인하기")
            .performClick()
        composeTestRule.waitUntil {
            composeTestRule.onNodeWithText("Home").isDisplayed()
        }
    }

    private fun navigateToFavoriteScreen() {
        loginToHomeScreen()
        composeTestRule.onNodeWithContentDescription("favorite screen icon")
            .performClick()
        composeTestRule.waitUntil {
            composeTestRule.onNodeWithText("Favorite").isDisplayed()
        }
    }

    private fun navigateToBoardScreen() {
        loginToHomeScreen()
        composeTestRule.onNodeWithContentDescription("board screen icon")
            .performClick()
        composeTestRule.waitUntil {
            composeTestRule.onNodeWithText("Board").isDisplayed()
        }
    }

    private fun navigateToHomeScreen() {
        navigateToBoardScreen()
        composeTestRule.onNodeWithContentDescription("home screen icon")
            .performClick()
        composeTestRule.waitUntil {
            composeTestRule.onNodeWithText("Home").isDisplayed()
        }
    }

    private fun performLogout() {
        composeTestRule.onNodeWithContentDescription("logout icon").performClick()
        composeTestRule.onNodeWithText("확인").performClick()
        composeTestRule.waitUntil {
            composeTestRule.onNodeWithText("로그인하기").isDisplayed()
        }
    }
}