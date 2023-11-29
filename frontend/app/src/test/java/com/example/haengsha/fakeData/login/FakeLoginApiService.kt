package com.example.haengsha.fakeData.login

import com.example.haengsha.model.network.apiService.LoginApiService
import com.example.haengsha.model.network.dataModel.CheckNicknameRequest
import com.example.haengsha.model.network.dataModel.CheckNicknameResponse
import com.example.haengsha.model.network.dataModel.FindChangePasswordRequest
import com.example.haengsha.model.network.dataModel.FindChangePasswordResponse
import com.example.haengsha.model.network.dataModel.FindEmailVerifyResponse
import com.example.haengsha.model.network.dataModel.FindEmailVerifyRequest
import com.example.haengsha.model.network.dataModel.LoginCodeVerifyResponse
import com.example.haengsha.model.network.dataModel.LoginCodeVerifyRequest
import com.example.haengsha.model.network.dataModel.LoginRequest
import com.example.haengsha.model.network.dataModel.LoginResponse
import com.example.haengsha.model.network.dataModel.SignupEmailVerifyResponse
import com.example.haengsha.model.network.dataModel.SignupEmailVerifyRequest
import com.example.haengsha.model.network.dataModel.SignupRegisterRequest
import com.example.haengsha.model.network.dataModel.SignupRegisterResponse

class FakeLoginApiService : LoginApiService {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return FakeLoginDataSource.loginResponse
    }

    override suspend fun loginCodeVerify(loginCodeVerifyRequest: LoginCodeVerifyRequest): LoginCodeVerifyResponse {
        return FakeLoginDataSource.loginCodeVerifyResponse
    }

    override suspend fun signupEmailVerify(signupEmailVerifyRequest: SignupEmailVerifyRequest): SignupEmailVerifyResponse {
        return FakeLoginDataSource.signupEmailVerifyResponse
    }

    override suspend fun signupRegister(signupRegisterRequest: SignupRegisterRequest): SignupRegisterResponse {
        return FakeLoginDataSource.signupRegisterResponse
    }

    override suspend fun checkNickname(checkNicknameRequest: CheckNicknameRequest): CheckNicknameResponse {
        return FakeLoginDataSource.checkNicknameResponse
    }

    override suspend fun findEmailVerify(findEmailVerifyRequest: FindEmailVerifyRequest): FindEmailVerifyResponse {
        return FakeLoginDataSource.findEmailVerifyResponse
    }

    override suspend fun findChangePassword(findChangePasswordRequest: FindChangePasswordRequest): FindChangePasswordResponse {
        return FakeLoginDataSource.findChangePasswordResponse
    }

    override suspend fun logout(token: String) {
        // return nothing
    }
}