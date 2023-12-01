package com.example.haengsha.model.network.dataModel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
    val nickname: String,
    val role: String,
    val message: String
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

// TODO 쓸모없는 response 받지 말기
@Serializable
data class LoginCodeVerifyResponse(
    val message: String
)

@Serializable
data class LoginCodeVerifyRequest(
    val email: String,
    val code: String
)

@Serializable
data class SignupEmailVerifyResponse(
    val message: String
)

@Serializable
data class SignupEmailVerifyRequest(
    val email: String
)

@Serializable
data class SignupRegisterResponse(
    val message: String
)

@Serializable
data class SignupRegisterRequest(
    val nickname: String,
    val email: String,
    val password: String,
    val role: String,
    val major: String,
    val grade: String,
    val interest: String
)

@Serializable
data class CheckNicknameResponse(
    val message: String
)

@Serializable
data class CheckNicknameRequest(
    val nickname: String
)

@Serializable
data class FindEmailVerifyResponse(
    val message: String
)

@Serializable
data class FindEmailVerifyRequest(
    val email: String
)

@Serializable
data class FindChangePasswordResponse(
    val message: String
)

@Serializable
data class FindChangePasswordRequest(
    val email: String,
    @SerialName("password")
    val newPassword: String,
    @SerialName("password_confirm")
    val newPasswordAgain: String
)