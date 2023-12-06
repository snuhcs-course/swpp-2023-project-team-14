package com.example.haengsha.unitTest.login

import com.example.haengsha.model.uiState.login.SignupUiState
import com.example.haengsha.model.viewModel.login.SignupViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Test

class SignupViewModelTest {
    private val signupViewModel = SignupViewModel()

    @Test
    fun signupViewModel_updateEmail_updateIsCorrect() {
        signupViewModel.updateEmail("email@email.com")
        assertEquals(signupViewModel.uiState.value.email, "email@email.com")
    }

    @Test
    fun signupViewModel_updatePassword_updateIsCorrect() {
        signupViewModel.updatePassword("password")
        assertEquals(signupViewModel.uiState.value.password, "password")
    }

    @Test
    fun signupViewModel_updateNickname_updateIsCorrect() {
        signupViewModel.updateNickname("nickname")
        assertEquals(signupViewModel.uiState.value.nickname, "nickname")
    }

    @Test
    fun signupViewModel_updateMajor_updateIsCorrect() {
        signupViewModel.updateMajor("major")
        assertEquals(signupViewModel.uiState.value.major, "major")
    }

    @Test
    fun signupViewModel_updateGrade_updateIsCorrect() {
        signupViewModel.updateGrade("grade")
        assertEquals(signupViewModel.uiState.value.grade, "grade")
    }

    @Test
    fun signupViewModel_updateInterest_updateIsCorrect() {
        signupViewModel.updateInterest("interest")
        assertEquals(signupViewModel.uiState.value.interest, "interest")
    }

    @Test
    fun signupViewModel_resetSignupData_resetIsCorrect() {
        signupViewModel.updateEmail("email@email.com")
        signupViewModel.updateInterest("interest")
        signupViewModel.resetSignupData()
        assertEquals(signupViewModel.uiState.value, SignupUiState())
    }
}