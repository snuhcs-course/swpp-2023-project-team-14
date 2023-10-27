package com.example.haengsha.fakeData

import com.example.haengsha.model.network.dataModel.CheckNicknameResponse
import com.example.haengsha.model.network.dataModel.FindChangePasswordResponse
import com.example.haengsha.model.network.dataModel.FindEmailVerificationResponse
import com.example.haengsha.model.network.dataModel.LoginCodeVerificationResponse
import com.example.haengsha.model.network.dataModel.LoginResponse
import com.example.haengsha.model.network.dataModel.SignupEmailVerificationResponse
import com.example.haengsha.model.network.dataModel.SignupRegisterResponse

object FakeDataSource {
    private const val token = "fakeToken"
    private const val role = "fakeRole"
    private const val message = "fakeMessage"

    val loginResponse = LoginResponse(
        token = token,
        role = role,
        message = message
    )

    val loginCodeVerificationResponse = LoginCodeVerificationResponse(
        message = message
    )

    val signupEmailVerificationResponse = SignupEmailVerificationResponse(
        message = message
    )

    val signupRegisterResponse = SignupRegisterResponse(
        message = message
    )

    val checkNicknameResponse = CheckNicknameResponse(
        message = message
    )

    val findEmailVerificationResponse = FindEmailVerificationResponse(
        message = message
    )

    val findChangePasswordResponse = FindChangePasswordResponse(
        message = message
    )
}