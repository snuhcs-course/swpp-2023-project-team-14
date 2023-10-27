package com.example.haengsha.unitTest.login

import com.example.haengsha.fakeData.login.FakeLoginDataSource
import com.example.haengsha.fakeData.login.FakeNetworkLoginDataRepository
import com.example.haengsha.model.uiState.login.LoginUiState
import com.example.haengsha.model.viewModel.login.LoginViewModel
import com.example.haengsha.testRules.TestDispatcherRule
import kotlinx.coroutines.test.runTest
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun loginViewModel_login_verifyLoginUiStateSuccess() = runTest {
        val loginViewModel = LoginViewModel(
            loginDataRepository = FakeNetworkLoginDataRepository()
        )
        loginViewModel.login(FakeLoginDataSource.email, FakeLoginDataSource.password)
        assertEquals(
            LoginUiState.LoginSuccess(
                FakeLoginDataSource.loginResponse.token,
                FakeLoginDataSource.loginResponse.role,
                FakeLoginDataSource.loginResponse.message
            ), loginViewModel.loginUiState
        )
    }

    @Test
    fun loginViewModel_loginCodeVerify_verifyLoginUiStateSuccess() = runTest {
        val loginViewModel = LoginViewModel(
            loginDataRepository = FakeNetworkLoginDataRepository()
        )
        loginViewModel.loginCodeVerify(FakeLoginDataSource.email, FakeLoginDataSource.code)
        assertEquals(
            LoginUiState.Success(FakeLoginDataSource.message), loginViewModel.loginUiState
        )
    }

    @Test
    fun loginViewModel_signupEmailVerify_verifyLoginUiStateSuccess() = runTest {
        val loginViewModel = LoginViewModel(
            loginDataRepository = FakeNetworkLoginDataRepository()
        )
        loginViewModel.signupEmailVerify(FakeLoginDataSource.email)
        assertEquals(
            LoginUiState.Success(FakeLoginDataSource.message), loginViewModel.loginUiState
        )
    }

    @Test
    fun loginViewModel_signupRegister_verifyLoginUiStateSuccess() = runTest {
        val loginViewModel = LoginViewModel(
            loginDataRepository = FakeNetworkLoginDataRepository()
        )
        loginViewModel.signupRegister(
            FakeLoginDataSource.email,
            FakeLoginDataSource.password,
            FakeLoginDataSource.nickname,
            FakeLoginDataSource.role,
            FakeLoginDataSource.major,
            FakeLoginDataSource.grade,
            FakeLoginDataSource.interest
        )
        assertEquals(
            LoginUiState.Success(FakeLoginDataSource.message), loginViewModel.loginUiState
        )
    }

    @Test
    fun loginViewModel_checkNickname_verifyLoginUiStateSuccess() = runTest {
        val loginViewModel = LoginViewModel(
            loginDataRepository = FakeNetworkLoginDataRepository()
        )
        loginViewModel.checkNickname(FakeLoginDataSource.nickname)
        assertEquals(
            LoginUiState.Success(FakeLoginDataSource.message), loginViewModel.loginUiState
        )
    }

    @Test
    fun loginViewModel_findEmailVerify_verifyLoginUiStateSuccess() = runTest {
        val loginViewModel = LoginViewModel(
            loginDataRepository = FakeNetworkLoginDataRepository()
        )
        loginViewModel.findEmailVerify(FakeLoginDataSource.email)
        assertEquals(
            LoginUiState.Success(FakeLoginDataSource.message), loginViewModel.loginUiState
        )
    }

    @Test
    fun loginViewModel_findChangePassword_verifyLoginUiStateSuccess() = runTest {
        val loginViewModel = LoginViewModel(
            loginDataRepository = FakeNetworkLoginDataRepository()
        )
        loginViewModel.findChangePassword(
            FakeLoginDataSource.email,
            FakeLoginDataSource.newPassword,
            FakeLoginDataSource.newPasswordAgain
        )
        assertEquals(
            LoginUiState.Success(FakeLoginDataSource.message), loginViewModel.loginUiState
        )
    }
}