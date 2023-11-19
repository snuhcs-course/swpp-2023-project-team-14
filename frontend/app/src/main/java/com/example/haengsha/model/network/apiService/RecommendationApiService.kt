package com.example.haengsha.model.network.apiService
import retrofit2.http.GET
import retrofit2.http.Header

interface RecommendationApiService {
    @GET("/post/recommend")
    suspend fun getRecommendationList(
        @Header("Authorization") token: String
    ): List<EventResponse>
}