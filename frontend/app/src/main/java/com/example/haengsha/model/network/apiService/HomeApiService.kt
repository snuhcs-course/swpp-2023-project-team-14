package com.example.haengsha.model.network.apiService

import com.example.haengsha.model.network.dataModel.EventResponse
import com.example.haengsha.model.network.dataModel.RecommendResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface HomeApiService {
    @GET("/api/post/keyword/{keyword}/festival/{is_festival}/date/{date}/{date}")
    suspend fun getEventByDate(
        @Path("keyword") keyword: String = "",
        @Path("is_festival") isFestival: Int,  // Replace with appropriate type
        @Path("date") date: String
    ): List<EventResponse>? // Change the return type to a list of EventResponse

    @GET("/api/post/recommend")
    suspend fun getRecommendationList(
        @Header("Authorization") token: String
    ): List<RecommendResponse>
}
