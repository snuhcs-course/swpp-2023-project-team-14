package com.example.haengsha.instrumentedTest.uiTest

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.haengsha.R
import com.example.haengsha.testConfig.onNodeWithTagForStringId
import com.example.haengsha.ui.HaengshaApp
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavoriteScreenUITest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setupApplication() {
        composeTestRule.setContent {
            HaengshaApp()
        }
    }

    @Test
    fun favoriteScreen_personalUser_loadingDisplay() {
        personalUser_loginToHomeScreen(true)
        composeTestRule.onNodeWithContentDescription("favorite screen icon")
            .performClick()
        composeTestRule.onNodeWithText("즐겨찾기 목록 불러오는 중...")
            .assertIsDisplayed()
    }

    @Test
    fun favoriteScreen_personalUser_favoriteExist_eventDisplay() {
        personalUser_loginToHomeScreen(true)
        composeTestRule.onNodeWithContentDescription("favorite screen icon")
            .performClick()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("즐겨찾기 목록 불러오는 중...")
                .isNotDisplayed()
        }
        composeTestRule.onAllNodesWithText("공연").onFirst()
            .assertIsDisplayed()
    }

    @Test
    fun favoriteScreen_personalUser_favoriteNotExist_guideTextDisplay() {
        personalUser_loginToHomeScreen(false)
        composeTestRule.onNodeWithContentDescription("favorite screen icon")
            .performClick()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("즐겨찾기 목록 불러오는 중...")
                .isNotDisplayed()
        }
        composeTestRule.onNodeWithText("관심 있는 행사를 즐겨찾기 해보세요!")
            .assertIsDisplayed()
    }

    @Test
    fun favoriteScreen_groupUser_guideTextDisplay() {
        groupUser_loginToHomeScreen()
        composeTestRule.onNodeWithContentDescription("favorite screen icon")
            .performClick()
        composeTestRule.onNodeWithText("단체 계정은 즐겨찾기를 할 수 없어요")
            .assertIsDisplayed()
    }


    private fun personalUser_loginToHomeScreen(favoriteExist: Boolean) {
        composeTestRule.onNodeWithTagForStringId(R.string.suffixTextField)
            .performTextInput(if (favoriteExist) "user2@snu.ac.kr" else "user4@snu.ac.kr")
        composeTestRule.onNodeWithTagForStringId(R.string.passwordTextField)
            .performTextInput(if (favoriteExist) "user2" else "user4")
        composeTestRule.onNodeWithText("로그인하기")
            .performClick()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("Home").isDisplayed()
        }
    }

    private fun groupUser_loginToHomeScreen() {
        composeTestRule.onNodeWithTagForStringId(R.string.suffixTextField)
            .performTextInput("groupuser52@snu.ac.kr")
        composeTestRule.onNodeWithTagForStringId(R.string.passwordTextField)
            .performTextInput("groupuser52")
        composeTestRule.onNodeWithText("로그인하기")
            .performClick()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("Home").isDisplayed()
        }
    }
}