package com.example.haengsha.model.dataSource

import com.example.haengsha.model.network.apiService.EventApiService
import com.example.haengsha.model.network.apiService.LoginApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

interface AppContainer {
    val loginDataRepository: LoginDataRepository
    val eventDataRepository: EventDataRepository
}

class HaengshaAppContainer : AppContainer {
    private val baseUrl = "http://ec2-52-79-228-36.ap-northeast-2.compute.amazonaws.com:8080/"
    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()

    private val retrofitEventService: EventApiService by lazy {
        retrofit.create(EventApiService::class.java)
    }

    override val eventDataRepository: EventDataRepository by lazy {
        NetworkEventDataRepository(
            retrofitEventService
        )
    }

    private val retrofitLoginService: LoginApiService by lazy {
        retrofit.create(LoginApiService::class.java)
    }

    override val loginDataRepository: LoginDataRepository by lazy {
        NetworkLoginDataRepository(retrofitLoginService)
    }
}