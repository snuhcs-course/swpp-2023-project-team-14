package com.example.haengsha.unitTest.login

import com.example.haengsha.fakeData.login.FakeLoginDataSource
import com.example.haengsha.fakeData.login.FakeLoginApiService
import com.example.haengsha.model.dataSource.NetworkLoginDataRepository
import com.example.haengsha.model.network.dataModel.CheckNicknameRequest
import com.example.haengsha.model.network.dataModel.FindChangePasswordRequest
import com.example.haengsha.model.network.dataModel.FindEmailVerifyRequest
import com.example.haengsha.model.network.dataModel.LoginCodeVerifyRequest
import com.example.haengsha.model.network.dataModel.LoginRequest
import com.example.haengsha.model.network.dataModel.SignupEmailVerifyRequest
import com.example.haengsha.model.network.dataModel.SignupRegisterRequest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class NetworkLoginDataRepositoryTest {
    private val repository = NetworkLoginDataRepository(
        loginApiService = FakeLoginApiService()
    )

    @Test
    fun networkLoginDataRepository_login_verifyResponse() =
        runTest {
            assertEquals(
                FakeLoginDataSource.loginResponse,
                repository.login(FakeLoginDataSource.loginRequest)
            )
        }

    @Test
    fun networkLoginDataRepository_loginCodeVerify_verifyResponse() =
        runTest {
            assertEquals(
                FakeLoginDataSource.loginCodeVerifyResponse,
                repository.loginCodeVerify(FakeLoginDataSource.loginCodeVerifyRequest)
            )
        }

    @Test
    fun networkLoginDataRepository_signupEmailVerify_verifyResponse() =
        runTest {
            assertEquals(
                FakeLoginDataSource.signupEmailVerifyResponse,
                repository.signupEmailVerify(FakeLoginDataSource.signupEmailVerifyRequest)
            )
        }

    @Test
    fun networkLoginDataRepository_signupRegister_verifyResponse() =
        runTest {
            assertEquals(
                FakeLoginDataSource.signupRegisterResponse,
                repository.signupRegister(FakeLoginDataSource.signupRegisterRequest)
            )
        }

    @Test
    fun networkLoginDataRepository_checkNickname_verifyResponse() =
        runTest {
            assertEquals(
                FakeLoginDataSource.checkNicknameResponse,
                repository.checkNickname(FakeLoginDataSource.checkNicknameRequest)
            )
        }

    @Test
    fun networkLoginDataRepository_findEmailVerify_verifyResponse() =
        runTest {
            assertEquals(
                FakeLoginDataSource.findEmailVerifyResponse,
                repository.findEmailVerify(FakeLoginDataSource.findEmailVerifyRequest)
            )
        }

    @Test
    fun networkLoginDataRepository_findChangePassword_verifyResponse() =
        runTest {
            assertEquals(
                FakeLoginDataSource.findChangePasswordResponse,
                repository.findChangePassword(FakeLoginDataSource.findChangePasswordRequest)
            )
        }
}