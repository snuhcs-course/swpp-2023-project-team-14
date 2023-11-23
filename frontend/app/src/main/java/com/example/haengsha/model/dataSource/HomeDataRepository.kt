package com.example.haengsha.model.dataSource

import com.example.haengsha.model.network.apiService.HomeApiService
import com.example.haengsha.model.network.dataModel.EventResponse
import com.example.haengsha.model.network.dataModel.RecommendResponse

interface HomeDataRepository {
    suspend fun getEventByDate(eventType: Int, date: String): List<EventResponse>?
    suspend fun getRecommendationList(token: String): List<RecommendResponse>
}


class NetworkHomeDataRepository(
    private val homeApiService: HomeApiService
) : HomeDataRepository {
    override suspend fun getEventByDate(eventType: Int, date: String): List<EventResponse>? {
        return homeApiService.getEventByDate("", eventType, date)
    }

    override suspend fun getRecommendationList(token: String): List<RecommendResponse> {
        return homeApiService.getRecommendationList(token)
    }
}