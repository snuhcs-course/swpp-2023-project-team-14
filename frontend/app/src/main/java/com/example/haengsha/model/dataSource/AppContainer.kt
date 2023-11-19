package com.example.haengsha.model.dataSource

import com.example.haengsha.model.network.apiService.BoardApiService
import com.example.haengsha.model.network.apiService.EventApiService
import com.example.haengsha.model.network.apiService.LoginApiService
import com.example.haengsha.model.network.apiService.RecommendationApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

interface AppContainer {
    val loginDataRepository: LoginDataRepository
    val eventDataRepository: EventDataRepository
    val boardDataRepository: BoardDataRepository
    val recommendationDataRepository: RecommendationDataRepository
}

class HaengshaAppContainer : AppContainer {
    private val baseUrl = "http://ec2-52-79-228-92.ap-northeast-2.compute.amazonaws.com:8080/"
    private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()

    private val retrofitBoardService: BoardApiService by lazy {
        retrofit.create(BoardApiService::class.java)
    }

    override val boardDataRepository: BoardDataRepository by lazy {
        NetworkBoardDataRepository(
            retrofitBoardService
        )
    }

    private val retrofitEventService: EventApiService by lazy {
        retrofit.create(EventApiService::class.java)
    }

    override val eventDataRepository: EventDataRepository by lazy {
        NetworkEventDataRepository(
            retrofitEventService
        )
    }

    private val retrofitRecommendationService: RecommendationApiService by lazy {
        retrofit.create(RecommendationApiService::class.java)
    }

    override val recommendationDataRepository: RecommendationDataRepository by lazy{
        NetworkRecommendationRepository(
            retrofitRecommendationService
        )
    }

    private val retrofitLoginService: LoginApiService by lazy {
        retrofit.create(LoginApiService::class.java)
    }

    override val loginDataRepository: LoginDataRepository by lazy {
        NetworkLoginDataRepository(retrofitLoginService)
    }
}