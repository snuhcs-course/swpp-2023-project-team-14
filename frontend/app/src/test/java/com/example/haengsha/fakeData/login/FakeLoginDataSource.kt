package com.example.haengsha.fakeData.login

import com.example.haengsha.model.network.dataModel.CheckNicknameRequest
import com.example.haengsha.model.network.dataModel.CheckNicknameResponse
import com.example.haengsha.model.network.dataModel.FindChangePasswordRequest
import com.example.haengsha.model.network.dataModel.FindChangePasswordResponse
import com.example.haengsha.model.network.dataModel.FindEmailVerifyRequest
import com.example.haengsha.model.network.dataModel.FindEmailVerifyResponse
import com.example.haengsha.model.network.dataModel.LoginCodeVerifyResponse
import com.example.haengsha.model.network.dataModel.LoginCodeVerifyRequest
import com.example.haengsha.model.network.dataModel.LoginRequest
import com.example.haengsha.model.network.dataModel.LoginResponse
import com.example.haengsha.model.network.dataModel.SignupEmailVerifyRequest
import com.example.haengsha.model.network.dataModel.SignupEmailVerifyResponse
import com.example.haengsha.model.network.dataModel.SignupRegisterRequest
import com.example.haengsha.model.network.dataModel.SignupRegisterResponse

object FakeLoginDataSource {
    const val token = "fakeToken"
    const val role = "fakeRole"
    const val message = "fakeMessage"
    const val email = "fakeEmail"
    const val password = "fakePassword"
    const val code = "fakeCode"
    const val nickname = "fakeNickname"
    const val major = "fakeMajor"
    const val grade = "fakeGrade"
    const val interest = "fakeInterest"
    const val newPassword = "fakeNewPassword"
    const val newPasswordAgain = "fakeNewPasswordAgain"

    val loginResponse = LoginResponse(token, role, nickname, message)

    val loginRequest = LoginRequest(email, password)

    val loginCodeVerifyResponse = LoginCodeVerifyResponse(message)

    val loginCodeVerifyRequest = LoginCodeVerifyRequest(email, code)

    val signupEmailVerifyResponse = SignupEmailVerifyResponse(message)

    val signupEmailVerifyRequest = SignupEmailVerifyRequest(email)

    val signupRegisterResponse = SignupRegisterResponse(message)

    val signupRegisterRequest =
        SignupRegisterRequest(nickname, email, password, role, major, grade, interest)

    val checkNicknameResponse = CheckNicknameResponse(message)

    val checkNicknameRequest = CheckNicknameRequest(nickname)

    val findEmailVerifyResponse = FindEmailVerifyResponse(message)

    val findEmailVerifyRequest = FindEmailVerifyRequest(email)

    val findChangePasswordResponse = FindChangePasswordResponse(message)

    val findChangePasswordRequest = FindChangePasswordRequest(email, newPassword, newPasswordAgain)
}