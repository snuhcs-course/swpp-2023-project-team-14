package com.example.haengsha.instrumentedTest.uiTest

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
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
import com.example.haengsha.model.uiState.login.LoginApiUiState
import com.example.haengsha.model.viewModel.UserViewModel
import com.example.haengsha.model.viewModel.board.BoardViewModel
import com.example.haengsha.model.viewModel.login.LoginApiViewModel
import com.example.haengsha.testConfig.onNodeWithTagForStringId
import com.example.haengsha.ui.screens.login.Login
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginScreenUITest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var loginNavController: TestNavHostController
    private lateinit var mainNavController: NavHostController
    private lateinit var loginApiViewModel: LoginApiViewModel
    private lateinit var loginApiUiState: LoginApiUiState
    private lateinit var userViewModel: UserViewModel
    private lateinit var boardViewModel: BoardViewModel

    @Before
    fun setupLoginScreen() {
        composeTestRule.setContent {
            loginNavController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            mainNavController = rememberNavController()
            loginApiViewModel = viewModel(factory = LoginApiViewModel.Factory)
            loginApiUiState = loginApiViewModel.loginApiUiState
            userViewModel = UserViewModel()
            boardViewModel = BoardViewModel()

            // Login screen의 isTest를 true로 설정하기 위해 HaengshaApp()을 직접 호출하지 않음

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
    fun loginScreen_loginSuccess_loadingScreen() {
        composeTestRule.onNodeWithTagForStringId(R.string.suffixTextField)
            .performTextInput("user2@snu.ac.kr")
        composeTestRule.onNodeWithTagForStringId(R.string.passwordTextField)
            .performTextInput("user2")
        composeTestRule.onNodeWithText("로그인하기").performClick()
        composeTestRule.onNodeWithText("행샤에 로그인하는 중...")
            .assertIsDisplayed()
    }

    @Test
    fun signupEmailVerificationScreen_sendCode_loadingTextDisplay() {
        navigateToSignupEmailVerificationScreen()
        composeTestRule.onNodeWithTagForStringId(R.string.suffixTextField)
            .performTextInput("test@snu.ac.kr")
        composeTestRule.onNodeWithText("인증번호 발송")
            .performClick()
        composeTestRule.onNodeWithText("인증번호 발송 중...")
            .assertIsDisplayed()
    }

    @Test
    fun signupUserInfoScreen_clickCollegeDropDown_collegeDropDownItemListDisplay() {
        navigateToSignupUserInfoScreen()
        composeTestRule.onNodeWithText("학과")
            .performClick()
        composeTestRule.onNodeWithText("공과대학")
            .assertIsDisplayed()
    }

    @Test
    fun signupUserInfoScreen_clickCollegeDropDownItem_collegeDropDownItemListNotDisplay() {
        navigateToSignupUserInfoScreen()
        composeTestRule.onNodeWithText("학과")
            .performClick()
        composeTestRule.onNodeWithText("공과대학")
            .performClick()
        composeTestRule.onNodeWithText("경영대학")
            .assertIsNotDisplayed()
    }

    @Test
    fun signupUserInfoScreen_clickCollegeDropDownItem_collegeDropDownItemApply() {
        navigateToSignupUserInfoScreen()
        composeTestRule.onNodeWithText("학과")
            .performClick()
        composeTestRule.onNodeWithText("공과대학")
            .performClick()
        composeTestRule.onNodeWithText("공과대학")
            .assertIsDisplayed()
    }

    @Test
    fun signupUserInfoScreen_clickStudentIdDropDown_studentIdDropDownItemListDisplay() {
        navigateToSignupUserInfoScreen()
        composeTestRule.onNodeWithText("학번")
            .performClick()
        composeTestRule.onNodeWithText("19학번")
            .assertIsDisplayed()
    }

    @Test
    fun signupUserInfoScreen_clickStudentIdDropDownItem_studentIdDropDownItemListNotDisplay() {
        navigateToSignupUserInfoScreen()
        composeTestRule.onNodeWithText("학번")
            .performClick()
        composeTestRule.onNodeWithText("21학번")
            .performClick()
        composeTestRule.onNodeWithText("20학번")
            .assertIsNotDisplayed()
    }

    @Test
    fun signupUserInfoScreen_clickStudentIdDropDownItem_studentIdDropDownItemApply() {
        navigateToSignupUserInfoScreen()
        composeTestRule.onNodeWithText("학번")
            .performClick()
        composeTestRule.onNodeWithText("19학번")
            .performClick()
        composeTestRule.onNodeWithText("19학번")
            .assertIsDisplayed()
    }

    @Test
    fun signupUserInfoScreen_clickInterestDropDown_interestDropDownItemListDisplay() {
        navigateToSignupUserInfoScreen()
        composeTestRule.onNodeWithText("다음").performScrollTo()
        composeTestRule.onNodeWithText("나의 관심 분야")
            .performClick()
        composeTestRule.onNodeWithText("운동")
            .assertIsDisplayed()
    }

    @Test
    fun signupUserInfoScreen_clickInterestDropDownItem_interestDropDownItemListApply() {
        navigateToSignupUserInfoScreen()
        composeTestRule.onNodeWithText("다음").performScrollTo()
        composeTestRule.onNodeWithText("나의 관심 분야")
            .performClick()
        composeTestRule.onNodeWithText("운동")
            .performClick()
        composeTestRule.onNodeWithText("음악")
            .performClick()
        composeTestRule.onNodeWithText("운동, 음악")
            .assertIsDisplayed()
    }

    @Test
    fun signupUserInfoScreen_clickInterestDropDown_interestDropDownItemListNotDisplay() {
        navigateToSignupUserInfoScreen()
        composeTestRule.onNodeWithText("다음").performScrollTo()
        composeTestRule.onNodeWithText("나의 관심 분야")
            .performClick()
        composeTestRule.onNodeWithText("운동")
            .performClick()
        composeTestRule.onNodeWithText("음악")
            .performClick()
        composeTestRule.onNodeWithText("운동, 음악")
            .performClick()
        composeTestRule.onNodeWithText("예술")
            .assertIsNotDisplayed()
    }

    @Test
    fun signupTermsScreen_initialState_signupButtonDisable() {
        navigateToSignupTermsScreen()
        composeTestRule.onNodeWithText("동의 후 회원가입")
            .assertHasNoClickAction()
    }

    @Test
    fun signupTermsScreen_clickTermsOfUse_termsOfUseModalDisplay() {
        navigateToSignupTermsScreen()
        composeTestRule.onNodeWithText("서비스 이용약관 (필수) *")
            .performClick()
        composeTestRule.onNodeWithTag("TermsOfUseModal")
            .assertIsDisplayed()
    }

    @Test
    fun signupTermsScreen_clickTermsOfUse_clickClose_termsOfUseModalNotDisplay() {
        navigateToSignupTermsScreen()
        composeTestRule.onNodeWithText("서비스 이용약관 (필수) *")
            .performClick()
        composeTestRule.onNodeWithText("닫기")
            .performClick()
        composeTestRule.onNodeWithTag("TermsOfUseModal")
            .assertIsNotDisplayed()
    }

    @Test
    fun signupTermsScreen_clickPrivacyPolicy_privacyPolicyModalDisplay() {
        navigateToSignupTermsScreen()
        composeTestRule.onNodeWithText("개인정보 수집 및 처리 방침 (필수) *")
            .performClick()
        composeTestRule.onNodeWithTag("PrivacyPolicyModal")
            .assertIsDisplayed()
    }

    @Test
    fun signupTermsScreen_clickPrivacyPolicy_clickClose_privacyPolicyModalNotDisplay() {
        navigateToSignupTermsScreen()
        composeTestRule.onNodeWithText("개인정보 수집 및 처리 방침 (필수) *")
            .performClick()
        composeTestRule.onNodeWithText("닫기")
            .performClick()
        composeTestRule.onNodeWithTag("PrivacyPolicyModal")
            .assertIsNotDisplayed()
    }

    @Test
    fun signupTermsScreen_clickAllAgree_signupButtonClickable() {
        navigateToSignupTermsScreen()
        composeTestRule.onNodeWithTag("전체 동의")
            .performClick()
        composeTestRule.onNodeWithText("동의 후 회원가입")
            .assertHasClickAction()
    }

    @Test
    fun findPasswordScreen_sendCode_loadingTextDisplay() {
        navigateToFindPasswordScreen()
        composeTestRule.onNodeWithTagForStringId(R.string.suffixTextField)
            .performTextInput("user2@snu.ac.kr")
        composeTestRule.onNodeWithText("인증번호 발송")
            .performClick()
        composeTestRule.onNodeWithText("인증번호 발송 중...")
            .assertIsDisplayed()
    }

    private fun navigateToSignupTypeScreen() {
        composeTestRule.onNodeWithText("회원가입하기").performClick()
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

    private fun navigateToFindPasswordScreen() {
        composeTestRule.onNodeWithText("비밀번호 찾기").performClick()
    }
}