package com.example.haengsha.model.viewModel

import androidx.lifecycle.ViewModel
import com.example.haengsha.model.uiState.UserUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UserUiState())
    val uiState = _uiState.asStateFlow()

    fun updateToken(token: String) {
        updateUserData(token, "token")
    }

    fun updateRole(role: String) {
        updateUserData(role, "role")
    }

    fun updateNickname(nickname: String) {
        updateUserData(nickname, "nickname")
    }

    fun resetUserData() {
        _uiState.value = UserUiState()
    }

    private fun updateUserData(newUserData: String, type: String) {
        _uiState.update { currentState ->
            currentState.copy(
                token = if (type == "token") newUserData else currentState.token,
                role = if (type == "role") newUserData else currentState.role,
                nickname = if (type == "nickname") newUserData else currentState.nickname
            )
        }
    }
}