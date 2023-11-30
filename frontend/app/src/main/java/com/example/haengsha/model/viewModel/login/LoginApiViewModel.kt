package com.example.haengsha.model.viewModel.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.haengsha.HaengshaApplication
import com.example.haengsha.model.dataSource.LoginDataRepository
import com.example.haengsha.model.network.dataModel.CheckNicknameRequest
import com.example.haengsha.model.network.dataModel.FindChangePasswordRequest
import com.example.haengsha.model.network.dataModel.FindEmailVerifyRequest
import com.example.haengsha.model.network.dataModel.LoginCodeVerifyRequest
import com.example.haengsha.model.network.dataModel.LoginRequest
import com.example.haengsha.model.network.dataModel.SignupEmailVerifyRequest
import com.example.haengsha.model.network.dataModel.SignupRegisterRequest
import com.example.haengsha.model.uiState.login.LoginApiUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class LoginApiViewModel(private val loginDataRepository: LoginDataRepository) : ViewModel() {
    var loginApiUiState: LoginApiUiState by mutableStateOf(LoginApiUiState.Loading)
        private set

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as HaengshaApplication
                val loginDataRepository = application.container.loginDataRepository
                LoginApiViewModel(loginDataRepository)
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginApiUiState = LoginApiUiState.Loading
            loginApiUiState = try {
                val loginSuccessResult = loginDataRepository.login(LoginRequest(email, password))
                LoginApiUiState.LoginSuccess(
                    loginSuccessResult.token,
                    loginSuccessResult.nickname,
                    loginSuccessResult.role,
                    loginSuccessResult.message
                )
            } catch (e: HttpException) {
                LoginApiUiState.HttpError("입력한 정보를 확인해주세요")
            } catch (e: IOException) {
                LoginApiUiState.NetworkError
            }
        }
    }

    fun loginCodeVerify(email: String, code: String) {
        viewModelScope.launch {
            loginApiUiState = LoginApiUiState.Loading
            loginApiUiState = try {
                val loginCodeVerificationResult =
                    loginDataRepository.loginCodeVerify(LoginCodeVerifyRequest(email, code))
                LoginApiUiState.Success(loginCodeVerificationResult.message)
            } catch (e: HttpException) {
                LoginApiUiState.HttpError("입력한 정보를 확인해주세요")
            } catch (e: IOException) {
                LoginApiUiState.NetworkError
            }
        }
    }

    fun signupEmailVerify(email: String) {
        viewModelScope.launch {
            loginApiUiState = LoginApiUiState.Loading
            loginApiUiState = try {
                val signupEmailVerifyResult = loginDataRepository.signupEmailVerify(
                    SignupEmailVerifyRequest(email)
                )
                LoginApiUiState.Success(signupEmailVerifyResult.message)
            } catch (e: HttpException) {
                if (e.code() == 401) {
                    LoginApiUiState.HttpError("이미 가입된 계정입니다")
                } else {
                    LoginApiUiState.HttpError("입력한 정보를 확인해주세요")
                }
            } catch (e: IOException) {
                LoginApiUiState.NetworkError
            }
        }
    }

    fun checkNickname(nickname: String) {
        viewModelScope.launch {
            loginApiUiState = LoginApiUiState.Loading
            loginApiUiState = try {
                val checkNicknameResult = loginDataRepository.checkNickname(
                    CheckNicknameRequest(nickname)
                )
                LoginApiUiState.Success(checkNicknameResult.message)
            } catch (e: HttpException) {
                LoginApiUiState.HttpError("입력한 정보를 확인해주세요")
            } catch (e: IOException) {
                LoginApiUiState.NetworkError
            }
        }
    }

    fun signupRegister(
        email: String,
        password: String,
        nickname: String,
        role: String,
        major: String,
        grade: String,
        interest: String
    ) {
        viewModelScope.launch {
            loginApiUiState = LoginApiUiState.Loading
            loginApiUiState = try {
                val signupRegisterResult = loginDataRepository.signupRegister(
                    SignupRegisterRequest(
                        nickname,
                        email,
                        password,
                        role,
                        major,
                        grade,
                        interest
                    )
                )
                LoginApiUiState.Success(signupRegisterResult.message)
            } catch (e: HttpException) {
                LoginApiUiState.HttpError("입력한 정보를 확인해주세요")
            } catch (e: IOException) {
                LoginApiUiState.NetworkError
            }
        }
    }

    fun findEmailVerify(email: String) {
        viewModelScope.launch {
            loginApiUiState = LoginApiUiState.Loading
            loginApiUiState = try {
                val findEmailVerifyResult = loginDataRepository.findEmailVerify(
                    FindEmailVerifyRequest(email)
                )
                LoginApiUiState.Success(findEmailVerifyResult.message)
            } catch (e: HttpException) {
                LoginApiUiState.HttpError("입력한 정보를 확인해주세요")
            } catch (e: IOException) {
                LoginApiUiState.NetworkError
            }
        }
    }

    fun findChangePassword(email: String, newPassword: String, newPasswordAgain: String) {
        viewModelScope.launch {
            loginApiUiState = LoginApiUiState.Loading
            loginApiUiState = try {
                val findChangePasswordResult = loginDataRepository.findChangePassword(
                    FindChangePasswordRequest(email, newPassword, newPasswordAgain)
                )
                LoginApiUiState.Success(findChangePasswordResult.message)
            } catch (e: HttpException) {
                LoginApiUiState.HttpError("입력한 정보를 확인해주세요")
            } catch (e: IOException) {
                LoginApiUiState.NetworkError
            }
        }
    }

    fun logout(token: String) {
        viewModelScope.launch {
            loginApiUiState = LoginApiUiState.Loading
            loginApiUiState = try {
                val authToken = "Token $token"
                loginDataRepository.logout(authToken)
                LoginApiUiState.Success("로그아웃 성공")
            } catch (e: HttpException) {
                LoginApiUiState.HttpError("로그아웃에 실패하였습니다. 다시 시도해주세요")
            } catch (e: IOException) {
                LoginApiUiState.NetworkError
            }
        }
    }

    fun resetLoginApiUiState() {
        loginApiUiState = LoginApiUiState.Loading
    }
}