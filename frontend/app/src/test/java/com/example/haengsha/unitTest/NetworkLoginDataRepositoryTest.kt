package com.example.haengsha.unitTest

import com.example.haengsha.fakeData.FakeDataSource
import com.example.haengsha.fakeData.FakeLoginApiService
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

    private val email = "fakeEmail"
    private val password = "fakePassword"
    private val code = "fakeCode"
    private val nickname = "fakeNickname"
    private val role = "fakeRole"
    private val major = "fakeMajor"
    private val grade = "fakeGrade"
    private val interest = "fakeInterest"
    private val newPassword = "fakeNewPassword"
    private val newPasswordAgain = "fakeNewPasswordAgain"

    @Test
    fun networkLoginDataRepository_login_verifyResponse() =
        runTest {
            assertEquals(
                FakeDataSource.loginResponse,
                repository.login(LoginRequest(email, password))
            )
        }

    @Test
    fun networkLoginDataRepository_loginCodeVerify_verifyResponse() =
        runTest {
            assertEquals(
                FakeDataSource.loginCodeVerificationResponse,
                repository.loginCodeVerify(LoginCodeVerifyRequest(email, code))
            )
        }

    @Test
    fun networkLoginDataRepository_signupEmailVerify_verifyResponse() =
        runTest {
            assertEquals(
                FakeDataSource.signupEmailVerificationResponse,
                repository.signupEmailVerify(SignupEmailVerifyRequest(email))
            )
        }

    @Test
    fun networkLoginDataRepository_signupRegister_verifyResponse() =
        runTest {
            assertEquals(
                FakeDataSource.signupRegisterResponse,
                repository.signupRegister(
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
            )
        }

    @Test
    fun networkLoginDataRepository_checkNickname_verifyResponse() =
        runTest {
            assertEquals(
                FakeDataSource.checkNicknameResponse,
                repository.checkNickname(CheckNicknameRequest(nickname))
            )
        }

    @Test
    fun networkLoginDataRepository_findEmailVerify_verifyResponse() =
        runTest {
            assertEquals(
                FakeDataSource.findEmailVerificationResponse,
                repository.findEmailVerify(FindEmailVerifyRequest(email))
            )
        }

    @Test
    fun networkLoginDataRepository_findChangePassword_verifyResponse() =
        runTest {
            assertEquals(
                FakeDataSource.findChangePasswordResponse,
                repository.findChangePassword(
                    FindChangePasswordRequest(
                        email,
                        newPassword,
                        newPasswordAgain
                    )
                )
            )
        }
}