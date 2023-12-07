package com.example.haengsha.model.viewModel.login

import androidx.lifecycle.ViewModel
import com.example.haengsha.model.uiState.login.SignupUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignupViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()

    fun updateEmail(email: String = "") {
        updateSignupData(email, "email")
    }

    fun updatePassword(password: String = "") {
        updateSignupData(password, "password")
    }

    fun updateNickname(nickname: String = "") {
        updateSignupData(nickname, "nickname")
    }

    fun updateMajor(major: String = "") {
        updateSignupData(major, "major")
    }

    fun updateGrade(grade: String = "") {
        updateSignupData(grade, "grade")
    }

    fun updateInterest(interest: String = "") {
        updateSignupData(interest, "interest")
    }

    fun resetSignupData() {
        _uiState.value = SignupUiState()
    }

    private fun updateSignupData(newSignupData: String, type: String) {
        _uiState.update { currentState ->
            currentState.copy(
                email = if (type == "email") newSignupData else currentState.email,
                password = if (type == "password") newSignupData else currentState.password,
                nickname = if (type == "nickname") newSignupData else currentState.nickname,
                major = if (type == "major") newSignupData else currentState.major,
                grade = if (type == "grade") newSignupData else currentState.grade,
                interest = if (type == "interest") newSignupData else currentState.interest
            )
        }
    }
}