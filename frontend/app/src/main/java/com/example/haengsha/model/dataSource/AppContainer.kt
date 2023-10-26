package com.example.haengsha.model.dataSource

import com.example.haengsha.model.network.apiService.LoginApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val loginDataRepository: LoginDataRepository
}

class HaengshaAppContainer : AppContainer {
    private val baseUrl = "http://ec2-43-201-28-141.ap-northeast-2.compute.amazonaws.com:8080/"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitLoginService: LoginApiService by lazy {
        retrofit.create(LoginApiService::class.java)
    }

    override val loginDataRepository: LoginDataRepository by lazy {
        NetworkLoginDataRepository(retrofitLoginService)
    }
}