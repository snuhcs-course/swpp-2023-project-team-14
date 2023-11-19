package com.example.haengsha.model.dataSource

import com.example.haengsha.model.network.apiService.EventResponse
import com.example.haengsha.model.network.apiService.RecommendationApiService

interface RecommendationDataRepository {
    suspend fun getRecommendationList(token: String): List<EventResponse>?
}

class NetworkRecommendationRepository(
    private val recommendationApiService: RecommendationApiService
) : RecommendationDataRepository {
    override suspend fun getRecommendationList(token: String): List<EventResponse>? {
        return recommendationApiService.getRecommendationList(token)
    }
}