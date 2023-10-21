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
import com.example.haengsha.model.uiState.login.LoginUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class LoginViewModel(private val loginDataRepository: LoginDataRepository) : ViewModel() {
    var loginUiState: LoginUiState by mutableStateOf(LoginUiState.Loading)
        private set

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as HaengshaApplication
                val loginDataRepository = application.container.loginDataRepository
                LoginViewModel(loginDataRepository)
            }
        }
    }

    fun loginSuccess(email: String, password: String) {
        viewModelScope.launch {
            loginUiState = LoginUiState.Loading
            loginUiState = try {
                val loginSuccessResult =
                    loginDataRepository.loginSuccess(LoginRequest(email, password))
                LoginUiState.LoginSuccess(
                    loginSuccessResult.token,
                    loginSuccessResult.role,
                    loginSuccessResult.message
                )
            } catch (e: HttpException) {
                val errorMessage = e.response()?.errorBody()?.string() ?: "입력한 정보를 확인해주세요"
                LoginUiState.HttpError(errorMessage)
            } catch (e: IOException) {
                LoginUiState.NetworkError
            }
        }
    }

    fun loginFail(email: String, password: String) {
        viewModelScope.launch {
            loginUiState = LoginUiState.Loading
            loginUiState = try {
                val loginFailResult = loginDataRepository.loginFail(LoginRequest(email, password))
                LoginUiState.LoginSuccess(
                    loginFailResult.token,
                    loginFailResult.role,
                    loginFailResult.message
                )
            } catch (e: HttpException) {
                val errorMessage = e.response()?.errorBody()?.string() ?: "입력한 정보를 확인해주세요"
                LoginUiState.HttpError(errorMessage)
            } catch (e: IOException) {
                LoginUiState.NetworkError
            }
        }
    }

    fun loginCodeVerify(email: String, code: String) {
        viewModelScope.launch {
            loginUiState = LoginUiState.Loading
            loginUiState = try {
                val loginCodeVerificationResult =
                    loginDataRepository.loginCodeVerify(LoginCodeVerifyRequest(email, code))
                LoginUiState.Success(
                    loginCodeVerificationResult.message
                )
            } catch (e: HttpException) {
                val errorMessage = e.response()?.errorBody()?.string() ?: "입력한 정보를 확인해주세요"
                LoginUiState.HttpError(errorMessage)
            } catch (e: IOException) {
                LoginUiState.NetworkError
            }
        }
    }

    fun signupEmailVerify(email: String) {
        viewModelScope.launch {
            loginUiState = LoginUiState.Loading
            loginUiState = try {
                val signupEmailVerifyResult = loginDataRepository.signupEmailVerify(
                    SignupEmailVerifyRequest(email)
                )
                LoginUiState.Success(
                    signupEmailVerifyResult.message
                )
            } catch (e: HttpException) {
                val errorMessage = e.response()?.errorBody()?.string() ?: "입력한 정보를 확인해주세요"
                LoginUiState.HttpError(errorMessage)
            } catch (e: IOException) {
                LoginUiState.NetworkError
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
            loginUiState = LoginUiState.Loading
            loginUiState = try {
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
                LoginUiState.Success(
                    signupRegisterResult.message
                )
            } catch (e: HttpException) {
                val errorMessage = e.response()?.errorBody()?.string() ?: "입력한 정보를 확인해주세요"
                LoginUiState.HttpError(errorMessage)
            } catch (e: IOException) {
                LoginUiState.NetworkError
            }
        }
    }

    fun checkNickname(nickname: String) {
        viewModelScope.launch {
            loginUiState = LoginUiState.Loading
            loginUiState = try {
                val checkNicknameResult = loginDataRepository.checkNickname(
                    CheckNicknameRequest(nickname)
                )
                LoginUiState.Success(
                    checkNicknameResult.message
                )
            } catch (e: HttpException) {
                val errorMessage = e.response()?.errorBody()?.string() ?: "입력한 정보를 확인해주세요"
                LoginUiState.HttpError(errorMessage)
            } catch (e: IOException) {
                LoginUiState.NetworkError
            }
        }
    }

    fun findEmailVerify(email: String) {
        viewModelScope.launch {
            loginUiState = LoginUiState.Loading
            loginUiState = try {
                val findEmailVerifyResult = loginDataRepository.findEmailVerify(
                    FindEmailVerifyRequest(email)
                )
                LoginUiState.Success(
                    findEmailVerifyResult.message
                )
            } catch (e: HttpException) {
                val errorMessage = e.response()?.errorBody()?.string() ?: "입력한 정보를 확인해주세요"
                LoginUiState.HttpError(errorMessage)
            } catch (e: IOException) {
                LoginUiState.NetworkError
            }
        }
    }

    fun findChangePassword(email: String, newPassword: String, newPasswordAgain: String) {
        viewModelScope.launch {
            loginUiState = LoginUiState.Loading
            loginUiState = try {
                val findChangePasswordResult = loginDataRepository.findChangePassword(
                    FindChangePasswordRequest(email, newPassword, newPasswordAgain)
                )
                LoginUiState.Success(
                    findChangePasswordResult.message
                )
            } catch (e: HttpException) {
                val errorMessage = e.response()?.errorBody()?.string() ?: "입력한 정보를 확인해주세요"
                LoginUiState.HttpError(errorMessage)
            } catch (e: IOException) {
                LoginUiState.NetworkError
            }
        }
    }

}