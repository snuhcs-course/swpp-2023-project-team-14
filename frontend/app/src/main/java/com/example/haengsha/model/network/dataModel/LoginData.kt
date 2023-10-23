package com.example.haengsha.model.network.dataModel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
    val role: String,
    val message: String
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class LoginCodeVerificationResponse(
    val message: String
)

@Serializable
data class LoginCodeVerifyRequest(
    val email: String,
    val code: String
)

@Serializable
data class SignupEmailVerificationResponse(
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
data class FindEmailVerificationResponse(
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
    @SerialName("new password")
    val newPassword: String,
    @SerialName("new password again")
    val newPasswordAgain: String
)