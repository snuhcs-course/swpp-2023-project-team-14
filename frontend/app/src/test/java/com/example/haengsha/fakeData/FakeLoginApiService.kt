package com.example.haengsha.fakeData

import com.example.haengsha.model.network.apiService.LoginApiService
import com.example.haengsha.model.network.dataModel.CheckNicknameRequest
import com.example.haengsha.model.network.dataModel.CheckNicknameResponse
import com.example.haengsha.model.network.dataModel.FindChangePasswordRequest
import com.example.haengsha.model.network.dataModel.FindChangePasswordResponse
import com.example.haengsha.model.network.dataModel.FindEmailVerificationResponse
import com.example.haengsha.model.network.dataModel.FindEmailVerifyRequest
import com.example.haengsha.model.network.dataModel.LoginCodeVerificationResponse
import com.example.haengsha.model.network.dataModel.LoginCodeVerifyRequest
import com.example.haengsha.model.network.dataModel.LoginRequest
import com.example.haengsha.model.network.dataModel.LoginResponse
import com.example.haengsha.model.network.dataModel.SignupEmailVerificationResponse
import com.example.haengsha.model.network.dataModel.SignupEmailVerifyRequest
import com.example.haengsha.model.network.dataModel.SignupRegisterRequest
import com.example.haengsha.model.network.dataModel.SignupRegisterResponse

class FakeLoginApiService : LoginApiService {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return FakeDataSource.loginResponse
    }

    override suspend fun loginCodeVerify(loginCodeVerifyRequest: LoginCodeVerifyRequest): LoginCodeVerificationResponse {
        return FakeDataSource.loginCodeVerificationResponse
    }

    override suspend fun signupEmailVerify(signupEmailVerifyRequest: SignupEmailVerifyRequest): SignupEmailVerificationResponse {
        return FakeDataSource.signupEmailVerificationResponse
    }

    override suspend fun signupRegister(signupRegisterRequest: SignupRegisterRequest): SignupRegisterResponse {
        return FakeDataSource.signupRegisterResponse
    }

    override suspend fun checkNickname(checkNicknameRequest: CheckNicknameRequest): CheckNicknameResponse {
        return FakeDataSource.checkNicknameResponse
    }

    override suspend fun findEmailVerify(findEmailVerifyRequest: FindEmailVerifyRequest): FindEmailVerificationResponse {
        return FakeDataSource.findEmailVerificationResponse
    }

    override suspend fun findChangePassword(findChangePasswordRequest: FindChangePasswordRequest): FindChangePasswordResponse {
        return FakeDataSource.findChangePasswordResponse
    }
}