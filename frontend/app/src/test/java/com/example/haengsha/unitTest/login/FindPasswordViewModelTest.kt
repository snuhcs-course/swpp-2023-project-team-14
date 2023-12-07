package com.example.haengsha.unitTest.login

import com.example.haengsha.model.uiState.login.FindPasswordUiState
import com.example.haengsha.model.viewModel.login.FindPasswordViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Test

class FindPasswordViewModelTest {
    private val viewModel = FindPasswordViewModel()

    @Test
    fun findPasswordViewModel_updateEmail_updateIsCorrect() {
        viewModel.updateEmail("email@email.com")
        assertEquals(viewModel.uiState.value.email, "email@email.com")
    }

    @Test
    fun findPasswordViewModel_resetFindPasswordData_resetIsCorrect() {
        viewModel.updateEmail("email@email.com")
        viewModel.resetFindPasswordData()
        assertEquals(viewModel.uiState.value, FindPasswordUiState())
    }
}