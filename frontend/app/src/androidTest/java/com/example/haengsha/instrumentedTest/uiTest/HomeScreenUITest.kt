package com.example.haengsha.instrumentedTest.uiTest

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeScreenUITest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setupApplication() {
        composeTestRule.setContent {
            HaengshaApp()
        }
    }

    @Test
    fun homeScreen_initialState_loadingDisplay() {
        personalUser_loginToHomeScreen()
        composeTestRule.onNodeWithText("행사 불러오는 중...")
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_personalUser_clickRecommend_loadingDisplay() {
        personalUser_loginToHomeScreen()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("행사 불러오는 중...")
                .isNotDisplayed()
        }
        composeTestRule.onNodeWithText("행사 추천 받기")
            .performClick()
        composeTestRule.onNodeWithText("추천 행사 불러오는 중...")
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_personalUser_clickRecommend_eventsDisplay() {
        personalUser_loginToHomeScreen()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("행사 불러오는 중...")
                .isNotDisplayed()
        }
        composeTestRule.onNodeWithText("행사 추천 받기")
            .performClick()
        composeTestRule.waitUntil {
            composeTestRule.onNodeWithText("추천 행사 불러오는 중...")
                .isNotDisplayed()
        }
        composeTestRule.onAllNodesWithTag("EventCard").onFirst()
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_personalUser_clickRecommend_clickClose_dialogNotDisplay() {
        personalUser_loginToHomeScreen()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("행사 불러오는 중...")
                .isNotDisplayed()
        }
        composeTestRule.onNodeWithText("행사 추천 받기")
            .performClick()
        composeTestRule.onNodeWithText("닫기")
            .performClick()
        composeTestRule.onNodeWithText("행사 추천 받기")
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_personalUser_clickCalendarIcon_datePickerDisplay() {
        personalUser_loginToHomeScreen()
        composeTestRule.onNodeWithContentDescription("calendar icon")
            .performClick()
        composeTestRule.onNodeWithText(
            LocalDate.now()
                // .minusDays(1) // 표준 시간대랑 달라서, 조정해줘야 할 때 주석 제거
                .format(DateTimeFormatter.ofPattern("MMM d, yyyy"))
        ).assertIsDisplayed()
    }

    @Test
    fun homeScreen_personalUser_clickCalendarIcon_clickConfirm_datePickerNotDisplay() {
        personalUser_loginToHomeScreen()
        composeTestRule.onNodeWithContentDescription("calendar icon")
            .performClick()
        composeTestRule.onNodeWithText("확인")
            .performClick()
        composeTestRule.onNodeWithText("확인")
            .assertIsNotDisplayed()
    }

    @Test
    fun homeScreen_personalUser_clickCalendarIcon_clickDate_clickConfirm_eventLoading() {
        personalUser_loginToHomeScreen()
        composeTestRule.onNodeWithContentDescription("calendar icon")
            .performClick()
        composeTestRule.onNodeWithText("Sunday, December 10, 2023")
            .performClick()
        composeTestRule.onNodeWithText("확인")
            .performClick()
        composeTestRule.onNodeWithText("행사 불러오는 중...")
            .isDisplayed()
    }

    @Test
    fun homeScreen_personalUser_clickCalendarIcon_clickDate_clickConfirm_festivalEventDisplay() {
        personalUser_loginToHomeScreen()
        composeTestRule.onNodeWithContentDescription("calendar icon")
            .performClick()
        composeTestRule.onNodeWithText("Sunday, December 10, 2023")
            .performClick()
        composeTestRule.onNodeWithText("확인")
            .performClick()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("SNUPO SNUp\nSNUPO & SNUpia 하계 앙상블")
                .isDisplayed()
        }
        composeTestRule.onNodeWithText("SNUPO SNUp\nSNUPO & SNUpia 하계 앙상블")
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_personalUser_clickCalendarIcon_clickDate_clickConfirm_academicEventDisplay() {
        personalUser_loginToHomeScreen()
        composeTestRule.onNodeWithContentDescription("calendar icon")
            .performClick()
        composeTestRule.onNodeWithText("Sunday, December 10, 2023")
            .performClick()
        composeTestRule.onNodeWithText("확인")
            .performClick()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("행사 추천 받기")
                .isDisplayed()
        }
        composeTestRule.onNodeWithText("Academic")
            .performClick()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("데이터사이언스대학원\n동적 3D 세계 다음 세대의 인공지능")
                .isDisplayed()
        }
        composeTestRule.onNodeWithText("데이터사이언스대학원\n동적 3D 세계 다음 세대의 인공지능")
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_personalUser_clickCalendarIcon_clickDate_clickConfirm_noFestivalEventDisplay() {
        personalUser_loginToHomeScreen()
        composeTestRule.onNodeWithContentDescription("calendar icon")
            .performClick()
        composeTestRule.onNodeWithText("Friday, December 1, 2023")
            .performClick()
        composeTestRule.onNodeWithText("확인")
            .performClick()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("오늘은 예정된 축제가 없어요!")
                .isDisplayed()
        }
        composeTestRule.onNodeWithText("오늘은 예정된 축제가 없어요!")
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_personalUser_clickCalendarIcon_clickDate_clickConfirm_noAcademicEventDisplay() {
        personalUser_loginToHomeScreen()
        composeTestRule.onNodeWithContentDescription("calendar icon")
            .performClick()
        composeTestRule.onNodeWithText("Friday, December 1, 2023")
            .performClick()
        composeTestRule.onNodeWithText("확인")
            .performClick()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("행사 불러오는 중...")
                .isNotDisplayed()
        }
        composeTestRule.onNodeWithText("Academic")
            .performClick()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("오늘은 예정된 학술제가 없어요!")
                .isDisplayed()
        }
        composeTestRule.onNodeWithText("오늘은 예정된 학술제가 없어요!")
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_groupUser_clickRecommend_guideTextDisplay() {
        groupUser_loginToHomeScreen()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("행사 불러오는 중...")
                .isNotDisplayed()
        }
        composeTestRule.onNodeWithText("행사 추천 받기")
            .performClick()
        composeTestRule.onNodeWithText("단체 계정은 추천 기능을\n\n이용할 수 없어요")
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_groupUser_clickLogout_logoutDialogDisplay() {
        groupUser_loginToHomeScreen()
        composeTestRule.onNodeWithContentDescription("logout icon")
            .performClick()
        composeTestRule.onNodeWithText("로그아웃 하시겠어요?")
            .assertIsDisplayed()
    }

    private fun personalUser_loginToHomeScreen() {
        composeTestRule.onNodeWithTagForStringId(R.string.suffixTextField)
            .performTextInput("user2@snu.ac.kr")
        composeTestRule.onNodeWithTagForStringId(R.string.passwordTextField)
            .performTextInput("user2")
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