package com.example.haengsha.model.network.apiService

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
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginApiService {
    @POST("/api/signin")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("/api/verify_code")
    suspend fun loginCodeVerify(@Body loginCodeVerifyRequest: LoginCodeVerifyRequest): LoginCodeVerifyResponse

    @POST("/api/verify_snu_email")
    suspend fun signupEmailVerify(@Body signupEmailVerifyRequest: SignupEmailVerifyRequest): SignupEmailVerifyResponse

    @POST("/api/signup")
    suspend fun signupRegister(@Body signupRegisterRequest: SignupRegisterRequest): SignupRegisterResponse

    @POST("/api/check_nickname")
    suspend fun checkNickname(@Body checkNicknameRequest: CheckNicknameRequest): CheckNicknameResponse

    @POST("/api/find/verify_email")
    suspend fun findEmailVerify(@Body findEmailVerifyRequest: FindEmailVerifyRequest): FindEmailVerifyResponse

    @POST("/api/find/change_password")
    suspend fun findChangePassword(@Body findChangePasswordRequest: FindChangePasswordRequest): FindChangePasswordResponse

    @POST("/api/logout")
    suspend fun logout(@Header("Authorization") token: String)
}
