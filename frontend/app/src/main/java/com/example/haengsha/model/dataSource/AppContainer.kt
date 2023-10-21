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
    private val baseUrl = "https://4a02234b-9fc4-4609-8008-2e56e217c5e6.mock.pstmn.io"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: LoginApiService by lazy {
        retrofit.create(LoginApiService::class.java)
    }

    override val loginDataRepository: LoginDataRepository by lazy {
        NetworkLoginDataRepository(retrofitService)
    }
}