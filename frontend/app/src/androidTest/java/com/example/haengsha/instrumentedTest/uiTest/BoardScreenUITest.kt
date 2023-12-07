package com.example.haengsha.instrumentedTest.uiTest

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import com.example.haengsha.R
import com.example.haengsha.testConfig.onNodeWithTagForStringId
import com.example.haengsha.ui.HaengshaApp
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class BoardScreenUITest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setupApplication() {
        composeTestRule.setContent {
            HaengshaApp()
        }
    }

    @Test
    fun boardScreen_personalUser_navigateToBoardScreen_postButtonNotDisplay() {
        personalUser_login_navigateToBoardScreen()
        composeTestRule.onNodeWithContentDescription("event post Button")
            .assertIsNotDisplayed()
    }

    @Test
    fun boardScreen_personalUser_filterClick_filterDisplay() {
        personalUser_login_navigateToBoardScreen()
        composeTestRule.onNodeWithTag("filter")
            .performClick()
        composeTestRule.onNodeWithText("필터 설정")
            .assertIsDisplayed()
    }

    @Test
    fun boardScreen_personalUser_filterClick_closeClick_filterNotDisplay() {
        personalUser_login_navigateToBoardScreen()
        composeTestRule.onNodeWithTag("filter")
            .performClick()
        composeTestRule.onNodeWithText("취소")
            .performClick()
        composeTestRule.onNodeWithText("필터 설정")
            .assertIsNotDisplayed()
    }

    @Test
    fun boardScreen_personalUser_filterClick_startDateClick_datePickerDisplay() {
        personalUser_login_navigateToBoardScreen()
        composeTestRule.onNodeWithTag("filter")
            .performClick()
        composeTestRule.onNodeWithText("시작일을 선택해주세요")
            .performClick()
        composeTestRule.onNodeWithText(
            LocalDate.now()
                // .minusDays(1) // 표준 시간대랑 달라서, 조정해줘야 할 때 주석 제거
                .format(DateTimeFormatter.ofPattern("MMM d, yyyy"))
        ).assertIsDisplayed()
    }

    @Test
    fun boardScreen_personalUser_filterClick_startDateClick_closeClick_datePickerNotDisplay() {
        personalUser_login_navigateToBoardScreen()
        composeTestRule.onNodeWithTag("filter")
            .performClick()
        composeTestRule.onNodeWithText("시작일을 선택해주세요")
            .performClick()
        composeTestRule.onAllNodesWithText("취소").onLast()
            .performClick()
        composeTestRule.onNodeWithText("확인")
            .assertIsNotDisplayed()
    }

    @Test
    fun boardScreen_personalUser_filterClick_startDateClick_clickDate_clickConfirm_startDateApply() {
        personalUser_login_navigateToBoardScreen()
        composeTestRule.onNodeWithTag("filter")
            .performClick()
        composeTestRule.onNodeWithText("시작일을 선택해주세요")
            .performClick()
        composeTestRule.onNodeWithText("Friday, December 1, 2023")
            .performClick()
        composeTestRule.onNodeWithText("확인")
            .performClick()
        composeTestRule.onNodeWithText("2023-12-01")
            .assertIsDisplayed()
    }

    @Test
    fun boardScreen_personalUser_filterClick_endDateClick_datePickerDisplay() {
        personalUser_login_navigateToBoardScreen()
        composeTestRule.onNodeWithTag("filter")
            .performClick()
        composeTestRule.onNodeWithText("종료일을 선택해주세요")
            .performClick()
        composeTestRule.onNodeWithText(
            LocalDate.now()
                // .minusDays(1) // 표준 시간대랑 달라서, 조정해줘야 할 때 주석 제거
                .format(DateTimeFormatter.ofPattern("MMM d, yyyy"))
        ).assertIsDisplayed()
    }

    @Test
    fun boardScreen_personalUser_filterClick_endDateClick_closeClick_datePickerNotDisplay() {
        personalUser_login_navigateToBoardScreen()
        composeTestRule.onNodeWithTag("filter")
            .performClick()
        composeTestRule.onNodeWithText("종료일을 선택해주세요")
            .performClick()
        composeTestRule.onAllNodesWithText("취소").onLast()
            .performClick()
        composeTestRule.onNodeWithText("확인")
            .assertIsNotDisplayed()
    }

    @Test
    fun boardScreen_personalUser_filterClick_endDateClick_clickDate_clickConfirm_endDateApply() {
        personalUser_login_navigateToBoardScreen()
        composeTestRule.onNodeWithTag("filter")
            .performClick()
        composeTestRule.onNodeWithText("종료일을 선택해주세요")
            .performClick()
        composeTestRule.onNodeWithText("Sunday, December 31, 2023")
            .performClick()
        composeTestRule.onNodeWithText("확인")
            .performClick()
        composeTestRule.onNodeWithText("2023-12-31")
            .assertIsDisplayed()
    }

    @Test
    fun boardScreen_personalUser_filterClick_festivalCheckBoxClickable() {
        personalUser_login_navigateToBoardScreen()
        composeTestRule.onNodeWithTag("filter")
            .performClick()
        composeTestRule.onNodeWithTag("festival checkBox")
            .assertHasClickAction()
    }

    @Test
    fun boardScreen_personalUser_filterClick_academicCheckBoxClickable() {
        personalUser_login_navigateToBoardScreen()
        composeTestRule.onNodeWithTag("filter")
            .performClick()
        composeTestRule.onNodeWithTag("academic checkBox")
            .assertHasClickAction()
    }

    @Test
    fun boardScreen_personalUser_filterApply_filterTextDisplay() {
        personalUser_login_navigateToBoardScreen()
        composeTestRule.onNodeWithTag("filter")
            .performClick()
        composeTestRule.onNodeWithText("시작일을 선택해주세요")
            .performClick()
        composeTestRule.onNodeWithText("Friday, December 1, 2023")
            .performClick()
        composeTestRule.onNodeWithText("확인")
            .performClick()
        composeTestRule.onNodeWithText("종료일을 선택해주세요")
            .performClick()
        composeTestRule.onNodeWithText("Sunday, December 31, 2023")
            .performClick()
        composeTestRule.onNodeWithText("확인")
            .performClick()
        composeTestRule.onNodeWithTag("academic checkBox")
            .performClick()
        composeTestRule.onNodeWithText("적용")
            .performClick()
        composeTestRule.onNodeWithText("적용")
            .assertIsNotDisplayed()
        composeTestRule.onNodeWithText("필터 : 2023-12-01 ~ 2023-12-31, 공연")
            .assertIsDisplayed()
    }

    @Test
    fun boardScreen_personalUser_searchKeyword_eventLoading() {
        personalUser_login_navigateToBoardScreen()
        composeTestRule.onNodeWithTagForStringId(R.string.searchBar)
            .performTextInput("물리")
        composeTestRule.onNodeWithTagForStringId(R.string.searchBar)
            .performImeAction()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("찾고 싶은 행사를 검색해보세요!")
                .isNotDisplayed()
        }
        composeTestRule.onNodeWithText("행사 찾아보는 중...")
            .assertIsDisplayed()
    }

    @Test
    fun boardScreen_personalUser_searchKeyword_eventsDisplay() {
        personalUser_login_navigateToBoardScreen()
        composeTestRule.onNodeWithTagForStringId(R.string.searchBar)
            .performTextInput("물리")
        composeTestRule.onNodeWithTagForStringId(R.string.searchBar)
            .performImeAction()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("찾고 싶은 행사를 검색해보세요!")
                .isNotDisplayed()
        }
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("행사 찾아보는 중...")
                .isNotDisplayed()
        }
        composeTestRule.onAllNodesWithContentDescription("like icon").onFirst()
            .assertIsDisplayed()
    }

    @Test
    fun boardScreen_personalUser_searchKeyword_applyFilter_eventsLoadingAndDisplay() {
        personalUser_login_navigateToBoardScreen()
        composeTestRule.onNodeWithTagForStringId(R.string.searchBar)
            .performTextInput("물리")
        composeTestRule.onNodeWithTagForStringId(R.string.searchBar)
            .performImeAction()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("찾고 싶은 행사를 검색해보세요!")
                .isNotDisplayed()
        }
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("행사 찾아보는 중...")
                .isNotDisplayed()
        }
        composeTestRule.onNodeWithTag("filter")
            .performClick()
        composeTestRule.onNodeWithText("시작일을 선택해주세요")
            .performClick()
        composeTestRule.onNodeWithText("Friday, December 1, 2023")
            .performClick()
        composeTestRule.onNodeWithText("확인")
            .performClick()
        composeTestRule.onNodeWithText("종료일을 선택해주세요")
            .performClick()
        composeTestRule.onNodeWithText("Sunday, December 31, 2023")
            .performClick()
        composeTestRule.onNodeWithText("확인")
            .performClick()
        composeTestRule.onNodeWithTag("academic checkBox")
            .performClick()
        composeTestRule.onNodeWithText("적용")
            .performClick()
        composeTestRule.onNodeWithText("행사 찾아보는 중...")
            .assertIsDisplayed()

        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("행사 찾아보는 중...")
                .isNotDisplayed()
        }
        composeTestRule.onAllNodesWithContentDescription("like icon").onFirst()
            .assertIsDisplayed()
    }

    @Test
    fun boardScreen_personalUser_searchKeyword_applyFilter_eventsNotDisplay() {
        personalUser_login_navigateToBoardScreen()
        composeTestRule.onNodeWithTagForStringId(R.string.searchBar)
            .performTextInput("물리")
        composeTestRule.onNodeWithTagForStringId(R.string.searchBar)
            .performImeAction()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("찾고 싶은 행사를 검색해보세요!")
                .isNotDisplayed()
        }
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("행사 찾아보는 중...")
                .isNotDisplayed()
        }
        composeTestRule.onNodeWithTag("filter")
            .performClick()
        composeTestRule.onNodeWithText("시작일을 선택해주세요")
            .performClick()
        composeTestRule.onNodeWithText("Friday, December 1, 2023")
            .performClick()
        composeTestRule.onNodeWithText("확인")
            .performClick()
        composeTestRule.onNodeWithText("종료일을 선택해주세요")
            .performClick()
        composeTestRule.onNodeWithText("Friday, December 1, 2023")
            .performClick()
        composeTestRule.onNodeWithText("확인")
            .performClick()
        composeTestRule.onNodeWithTag("academic checkBox")
            .performClick()
        composeTestRule.onNodeWithText("적용")
            .performClick()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("행사 찾아보는 중...")
                .isNotDisplayed()
        }
        composeTestRule.onNodeWithText("찾는 행사가 없어요 :(")
            .assertIsDisplayed()
    }

    @Test
    fun boardScreen_personalUser_searchKeyword_eventsNotDisplay() {
        personalUser_login_navigateToBoardScreen()
        composeTestRule.onNodeWithTagForStringId(R.string.searchBar)
            .performTextInput("홯홯")
        composeTestRule.onNodeWithTagForStringId(R.string.searchBar)
            .performImeAction()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("찾고 싶은 행사를 검색해보세요!")
                .isNotDisplayed()
        }
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("행사 찾아보는 중...")
                .isNotDisplayed()
        }
        composeTestRule.onNodeWithText("찾는 행사가 없어요 :(")
            .assertIsDisplayed()
    }

    @Test
    fun boardScreen_groupUser_navigateToBoardScreen_postButtonDisplay() {
        groupUser_login_navigateToBoardScreen()
        composeTestRule.onNodeWithContentDescription("event post Button")
            .assertIsDisplayed()
    }

    @Test
    fun postScreen_groupUser_startDateClick_datePickerDisplay() {
        groupUser_login_navigateToPostScreen()
        composeTestRule.onNodeWithText("시작일 (필수)")
            .performClick()
        composeTestRule.onNodeWithText(
            LocalDate.now()
                // .minusDays(1) // 표준 시간대랑 달라서, 조정해줘야 할 때 주석 제거
                .format(DateTimeFormatter.ofPattern("MMM d, yyyy"))
        ).assertIsDisplayed()
    }

    @Test
    fun postScreen_groupUser_startDateClick_clickClose_datePickerNotDisplay() {
        groupUser_login_navigateToPostScreen()
        composeTestRule.onNodeWithText("시작일 (필수)")
            .performClick()
        composeTestRule.onNodeWithText("취소")
            .performClick()
        composeTestRule.onNodeWithText("확인")
            .assertIsNotDisplayed()
    }

    @Test
    fun postScreen_groupUser_startDateClick_clickDate_startDateApply() {
        groupUser_login_navigateToPostScreen()
        composeTestRule.onNodeWithText("시작일 (필수)")
            .performClick()
        composeTestRule.onNodeWithText("Sunday, December 3, 2023")
            .performClick()
        composeTestRule.onNodeWithText("확인")
            .performClick()
        composeTestRule.onNodeWithText("2023-12-03")
            .assertIsDisplayed()
    }

    @Test
    fun postScreen_groupUser_endDateClick_datePickerDisplay() {
        groupUser_login_navigateToPostScreen()
        composeTestRule.onNodeWithText("종료일 (선택)")
            .performClick()
        composeTestRule.onNodeWithText(
            LocalDate.now()
                // .minusDays(1) // 표준 시간대랑 달라서, 조정해줘야 할 때 주석 제거
                .format(DateTimeFormatter.ofPattern("MMM d, yyyy"))
        ).assertIsDisplayed()
    }

    @Test
    fun postScreen_groupUser_endDateClick_clickClose_datePickerNotDisplay() {
        groupUser_login_navigateToPostScreen()
        composeTestRule.onNodeWithText("종료일 (선택)")
            .performClick()
        composeTestRule.onNodeWithText("취소")
            .performClick()
        composeTestRule.onNodeWithText("확인")
            .assertIsNotDisplayed()
    }

    @Test
    fun postScreen_groupUser_endDateClick_clickDate_endDateApply() {
        groupUser_login_navigateToPostScreen()
        composeTestRule.onNodeWithText("종료일 (선택)")
            .performClick()
        composeTestRule.onNodeWithText("Sunday, December 31, 2023")
            .performClick()
        composeTestRule.onNodeWithText("확인")
            .performClick()
        composeTestRule.onNodeWithText("2023-12-31")
            .assertIsDisplayed()
    }

    @Test
    fun postScreen_groupUser_categoryClickable() {
        groupUser_login_navigateToPostScreen()
        composeTestRule.onNodeWithTag("festivalCheckBox")
            .assertHasClickAction()
        composeTestRule.onNodeWithTag("academicCheckBox")
            .assertHasClickAction()
    }

    @Test
    fun postScreen_groupUser_clickPostButton_dialogDisplay() {
        groupUser_login_navigateToPostScreen()
        composeTestRule.onNodeWithContentDescription("event upload Button")
            .performClick()
        composeTestRule.onNodeWithText("글을 업로드 하시겠어요?")
            .assertIsDisplayed()
    }

    @Test
    fun postScreen_groupUser_clickPostButton_clickClose_dialogDisplay() {
        groupUser_login_navigateToPostScreen()
        composeTestRule.onNodeWithContentDescription("event upload Button")
            .performClick()
        composeTestRule.onNodeWithText("취소")
            .performClick()
        composeTestRule.onNodeWithText("글을 업로드 하시겠어요?")
            .assertIsNotDisplayed()
    }

    @Test
    fun postScreen_groupUser_tryToNavigateBoardScreen_dialogDisplay() {
        groupUser_login_navigateToPostScreen()
        composeTestRule.onNodeWithContentDescription("home screen icon")
            .performClick()
        composeTestRule.onNodeWithText("취소")
            .assertIsDisplayed()
    }

    @Test
    fun postScreen_groupUser_tryToNavigateBoardScreen_clickClose_dialogNotDisplay() {
        groupUser_login_navigateToPostScreen()
        composeTestRule.onNodeWithContentDescription("home screen icon")
            .performClick()
        composeTestRule.onNodeWithText("취소")
            .performClick()
        composeTestRule.onNodeWithText("취소")
            .assertIsNotDisplayed()
    }

    @Test
    fun postScreen_groupUser_tryToNavigateBoardScreen_clickConfirm_navigateToBoardScreen() {
        groupUser_login_navigateToPostScreen()
        composeTestRule.onNodeWithContentDescription("board screen icon")
            .performClick()
        composeTestRule.onNodeWithText("확인")
            .performClick()
        composeTestRule.onNodeWithText("찾고 싶은 행사를 검색해보세요!")
            .assertIsDisplayed()
    }

    private fun personalUser_login_navigateToBoardScreen() {
        composeTestRule.onNodeWithTagForStringId(R.string.suffixTextField)
            .performTextInput("user2@snu.ac.kr")
        composeTestRule.onNodeWithTagForStringId(R.string.passwordTextField)
            .performTextInput("user2")
        composeTestRule.onNodeWithText("로그인하기")
            .performClick()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("Home").isDisplayed()
        }
        composeTestRule.onNodeWithContentDescription("board screen icon")
            .performClick()
    }

    private fun groupUser_login_navigateToBoardScreen() {
        composeTestRule.onNodeWithTagForStringId(R.string.suffixTextField)
            .performTextInput("groupuser52@snu.ac.kr")
        composeTestRule.onNodeWithTagForStringId(R.string.passwordTextField)
            .performTextInput("groupuser52")
        composeTestRule.onNodeWithText("로그인하기")
            .performClick()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("Home").isDisplayed()
        }
        composeTestRule.onNodeWithContentDescription("board screen icon")
            .performClick()
    }

    private fun groupUser_login_navigateToPostScreen() {
        composeTestRule.onNodeWithTagForStringId(R.string.suffixTextField)
            .performTextInput("groupuser52@snu.ac.kr")
        composeTestRule.onNodeWithTagForStringId(R.string.passwordTextField)
            .performTextInput("groupuser52")
        composeTestRule.onNodeWithText("로그인하기")
            .performClick()
        composeTestRule.waitUntil(5000) {
            composeTestRule.onNodeWithText("Home").isDisplayed()
        }
        composeTestRule.onNodeWithContentDescription("board screen icon")
            .performClick()
        composeTestRule.onNodeWithContentDescription("event post Button")
            .performClick()
    }
}