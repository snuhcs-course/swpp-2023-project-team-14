package com.example.haengsha.unitTest

import com.example.haengsha.model.uiState.UserUiState
import com.example.haengsha.model.viewModel.UserViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Test

class UserViewModelTest {
    private val viewModel = UserViewModel()

    @Test
    fun userViewModel_updateToken_updateIsCorrect() {
        viewModel.updateToken("token")
        assertEquals(viewModel.uiState.value.token, "token")
    }

    @Test
    fun userViewModel_updateRole_updateIsCorrect() {
        viewModel.updateRole("role")
        assertEquals(viewModel.uiState.value.role, "role")
    }

    @Test
    fun userViewModel_updateNickname_updateIsCorrect() {
        viewModel.updateNickname("nickname")
        assertEquals(viewModel.uiState.value.nickname, "nickname")
    }

    @Test
    fun userViewModel_resetUserData_resetIsCorrect() {
        viewModel.updateToken("token")
        viewModel.resetUserData()
        assertEquals(viewModel.uiState.value, UserUiState())
    }
}