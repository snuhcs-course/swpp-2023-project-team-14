package com.example.haengsha.unitTest.login

import com.example.haengsha.fakeData.login.FakeLoginDataSource
import com.example.haengsha.fakeData.login.FakeNetworkLoginDataRepository
import com.example.haengsha.model.uiState.login.LoginApiUiState
import com.example.haengsha.model.viewModel.login.LoginApiViewModel
import com.example.haengsha.testRules.TestDispatcherRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class LoginApiViewModelTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun loginViewModel_login_verifyLoginUiStateSuccess() = runTest {
        val loginApiViewModel = LoginApiViewModel(
            loginDataRepository = FakeNetworkLoginDataRepository()
        )
        loginApiViewModel.login(FakeLoginDataSource.email, FakeLoginDataSource.password)
        assertEquals(
            LoginApiUiState.LoginSuccess(
                FakeLoginDataSource.loginResponse.token,
                FakeLoginDataSource.loginResponse.nickname,
                FakeLoginDataSource.loginResponse.role,
                FakeLoginDataSource.loginResponse.message
            ), loginApiViewModel.loginApiUiState
        )
    }

    @Test
    fun loginViewModel_loginCodeVerify_verifyLoginUiStateSuccess() = runTest {
        val loginApiViewModel = LoginApiViewModel(
            loginDataRepository = FakeNetworkLoginDataRepository()
        )
        loginApiViewModel.loginCodeVerify(FakeLoginDataSource.email, FakeLoginDataSource.code)
        assertEquals(
            LoginApiUiState.Success(FakeLoginDataSource.message), loginApiViewModel.loginApiUiState
        )
    }

    @Test
    fun loginViewModel_signupEmailVerify_verifyLoginUiStateSuccess() = runTest {
        val loginApiViewModel = LoginApiViewModel(
            loginDataRepository = FakeNetworkLoginDataRepository()
        )
        loginApiViewModel.signupEmailVerify(FakeLoginDataSource.email)
        assertEquals(
            LoginApiUiState.Success(FakeLoginDataSource.message), loginApiViewModel.loginApiUiState
        )
    }

    @Test
    fun loginViewModel_signupRegister_verifyLoginUiStateSuccess() = runTest {
        val loginApiViewModel = LoginApiViewModel(
            loginDataRepository = FakeNetworkLoginDataRepository()
        )
        loginApiViewModel.signupRegister(
            FakeLoginDataSource.email,
            FakeLoginDataSource.password,
            FakeLoginDataSource.nickname,
            FakeLoginDataSource.role,
            FakeLoginDataSource.major,
            FakeLoginDataSource.grade,
            FakeLoginDataSource.interest
        )
        assertEquals(
            LoginApiUiState.Success(FakeLoginDataSource.message), loginApiViewModel.loginApiUiState
        )
    }

    @Test
    fun loginViewModel_checkNickname_verifyLoginUiStateSuccess() = runTest {
        val loginApiViewModel = LoginApiViewModel(
            loginDataRepository = FakeNetworkLoginDataRepository()
        )
        loginApiViewModel.checkNickname(FakeLoginDataSource.nickname)
        assertEquals(
            LoginApiUiState.Success(FakeLoginDataSource.message), loginApiViewModel.loginApiUiState
        )
    }

    @Test
    fun loginViewModel_findEmailVerify_verifyLoginUiStateSuccess() = runTest {
        val loginApiViewModel = LoginApiViewModel(
            loginDataRepository = FakeNetworkLoginDataRepository()
        )
        loginApiViewModel.findEmailVerify(FakeLoginDataSource.email)
        assertEquals(
            LoginApiUiState.Success(FakeLoginDataSource.message), loginApiViewModel.loginApiUiState
        )
    }

    @Test
    fun loginViewModel_findChangePassword_verifyLoginUiStateSuccess() = runTest {
        val loginApiViewModel = LoginApiViewModel(
            loginDataRepository = FakeNetworkLoginDataRepository()
        )
        loginApiViewModel.findChangePassword(
            FakeLoginDataSource.email,
            FakeLoginDataSource.newPassword,
            FakeLoginDataSource.newPasswordAgain
        )
        assertEquals(
            LoginApiUiState.Success(FakeLoginDataSource.message), loginApiViewModel.loginApiUiState
        )
    }

    @Test
    fun loginViewModel_logout_verifyLoginUiStateSuccess() = runTest {
        val loginApiViewModel = LoginApiViewModel(
            loginDataRepository = FakeNetworkLoginDataRepository()
        )
        loginApiViewModel.logout(FakeLoginDataSource.token)
        assertEquals(
            LoginApiUiState.Success("로그아웃 성공"),
            loginApiViewModel.loginApiUiState
        )
    }
}