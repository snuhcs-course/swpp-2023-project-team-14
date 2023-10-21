package com.example.haengsha.model.network.apiService

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
import retrofit2.http.POST

interface LoginApiService {
    @POST("signin/success")
    suspend fun loginSuccess(@Body loginRequest: LoginRequest): LoginResponse

    @POST("signin/noregist")
    suspend fun loginFail(@Body loginRequest: LoginRequest): LoginResponse

    @POST("verify_code/success")
    suspend fun loginCodeVerify(@Body loginCodeVerifyRequest: LoginCodeVerifyRequest): LoginCodeVerificationResponse

    @POST("signup/verify_snu_email/success")
    suspend fun signupEmailVerify(@Body signupEmailVerifyRequest: SignupEmailVerifyRequest): SignupEmailVerificationResponse

    @POST("signup/success")
    suspend fun signupRegister(@Body signupRegisterRequest: SignupRegisterRequest): SignupRegisterResponse

    @POST("check_nickname/success")
    suspend fun checkNickname(@Body checkNicknameRequest: CheckNicknameRequest): CheckNicknameResponse

    @POST("find/verify_email/success")
    suspend fun findEmailVerify(@Body findEmailVerifyRequest: FindEmailVerifyRequest): FindEmailVerificationResponse

    @POST("find/change_password")
    suspend fun findChangePassword(@Body findChangePasswordRequest: FindChangePasswordRequest): FindChangePasswordResponse
}
