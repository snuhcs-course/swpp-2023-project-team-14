package com.example.haengsha.model.dataSource

import com.example.haengsha.model.network.apiService.LoginApiService
import com.example.haengsha.model.network.dataModel.CheckNicknameRequest
import com.example.haengsha.model.network.dataModel.FindChangePasswordResponse
import com.example.haengsha.model.network.dataModel.CheckNicknameResponse
import com.example.haengsha.model.network.dataModel.FindChangePasswordRequest
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
import retrofit2.http.Body

interface LoginDataRepository {
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse
    suspend fun loginCodeVerify(@Body loginCodeVerifyRequest: LoginCodeVerifyRequest): LoginCodeVerificationResponse
    suspend fun signupEmailVerify(@Body signupEmailVerifyRequest: SignupEmailVerifyRequest): SignupEmailVerificationResponse
    suspend fun signupRegister(@Body signupRegisterRequest: SignupRegisterRequest): SignupRegisterResponse
    suspend fun checkNickname(@Body checkNicknameRequest: CheckNicknameRequest): CheckNicknameResponse
    suspend fun findEmailVerify(@Body findEmailVerifyRequest: FindEmailVerifyRequest): FindEmailVerificationResponse
    suspend fun findChangePassword(@Body findChangePasswordRequest: FindChangePasswordRequest): FindChangePasswordResponse
}

class NetworkLoginDataRepository(
    private val loginApiService: LoginApiService
) : LoginDataRepository {
    override suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return loginApiService.login(loginRequest)
    }
    
    override suspend fun loginCodeVerify(loginCodeVerifyRequest: LoginCodeVerifyRequest): LoginCodeVerificationResponse {
        return loginApiService.loginCodeVerify(loginCodeVerifyRequest)
    }

    override suspend fun signupEmailVerify(signupEmailVerifyRequest: SignupEmailVerifyRequest): SignupEmailVerificationResponse {
        return loginApiService.signupEmailVerify(signupEmailVerifyRequest)
    }

    override suspend fun signupRegister(signupRegisterRequest: SignupRegisterRequest): SignupRegisterResponse {
        return loginApiService.signupRegister(signupRegisterRequest)
    }

    override suspend fun checkNickname(checkNicknameRequest: CheckNicknameRequest): CheckNicknameResponse {
        return loginApiService.checkNickname(checkNicknameRequest)
    }

    override suspend fun findEmailVerify(findEmailVerifyRequest: FindEmailVerifyRequest): FindEmailVerificationResponse {
        return loginApiService.findEmailVerify(findEmailVerifyRequest)
    }

    override suspend fun findChangePassword(findChangePasswordRequest: FindChangePasswordRequest): FindChangePasswordResponse {
        return loginApiService.findChangePassword(findChangePasswordRequest)
    }
}