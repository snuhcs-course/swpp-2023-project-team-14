package com.example.haengsha.model.dataSource

import com.example.haengsha.model.network.apiService.LoginApiService
import com.example.haengsha.model.network.dataModel.CheckNicknameRequest
import com.example.haengsha.model.network.dataModel.CheckNicknameResponse
import com.example.haengsha.model.network.dataModel.FindChangePasswordRequest
import com.example.haengsha.model.network.dataModel.FindChangePasswordResponse
import com.example.haengsha.model.network.dataModel.FindEmailVerifyRequest
import com.example.haengsha.model.network.dataModel.FindEmailVerifyResponse
import com.example.haengsha.model.network.dataModel.LoginCodeVerifyRequest
import com.example.haengsha.model.network.dataModel.LoginCodeVerifyResponse
import com.example.haengsha.model.network.dataModel.LoginRequest
import com.example.haengsha.model.network.dataModel.LoginResponse
import com.example.haengsha.model.network.dataModel.SignupEmailVerifyRequest
import com.example.haengsha.model.network.dataModel.SignupEmailVerifyResponse
import com.example.haengsha.model.network.dataModel.SignupRegisterRequest
import com.example.haengsha.model.network.dataModel.SignupRegisterResponse

interface LoginDataRepository {
    suspend fun login(loginRequest: LoginRequest): LoginResponse
    suspend fun loginCodeVerify(loginCodeVerifyRequest: LoginCodeVerifyRequest): LoginCodeVerifyResponse
    suspend fun signupEmailVerify(signupEmailVerifyRequest: SignupEmailVerifyRequest): SignupEmailVerifyResponse
    suspend fun signupRegister(signupRegisterRequest: SignupRegisterRequest): SignupRegisterResponse
    suspend fun checkNickname(checkNicknameRequest: CheckNicknameRequest): CheckNicknameResponse
    suspend fun findEmailVerify(findEmailVerifyRequest: FindEmailVerifyRequest): FindEmailVerifyResponse
    suspend fun findChangePassword(findChangePasswordRequest: FindChangePasswordRequest): FindChangePasswordResponse
    suspend fun logout(token: String)
}

class NetworkLoginDataRepository(
    private val loginApiService: LoginApiService
) : LoginDataRepository {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return loginApiService.login(loginRequest)
    }

    override suspend fun loginCodeVerify(loginCodeVerifyRequest: LoginCodeVerifyRequest): LoginCodeVerifyResponse {
        return loginApiService.loginCodeVerify(loginCodeVerifyRequest)
    }

    override suspend fun signupEmailVerify(signupEmailVerifyRequest: SignupEmailVerifyRequest): SignupEmailVerifyResponse {
        return loginApiService.signupEmailVerify(signupEmailVerifyRequest)
    }

    override suspend fun signupRegister(signupRegisterRequest: SignupRegisterRequest): SignupRegisterResponse {
        return loginApiService.signupRegister(signupRegisterRequest)
    }

    override suspend fun checkNickname(checkNicknameRequest: CheckNicknameRequest): CheckNicknameResponse {
        return loginApiService.checkNickname(checkNicknameRequest)
    }

    override suspend fun findEmailVerify(findEmailVerifyRequest: FindEmailVerifyRequest): FindEmailVerifyResponse {
        return loginApiService.findEmailVerify(findEmailVerifyRequest)
    }

    override suspend fun findChangePassword(findChangePasswordRequest: FindChangePasswordRequest): FindChangePasswordResponse {
        return loginApiService.findChangePassword(findChangePasswordRequest)
    }

    override suspend fun logout(token: String) {
        return loginApiService.logout(token)
    }
}