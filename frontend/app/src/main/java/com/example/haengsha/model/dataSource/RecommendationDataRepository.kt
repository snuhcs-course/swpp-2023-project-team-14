package com.example.haengsha.model.dataSource

import com.example.haengsha.model.network.apiService.RecommendResponse
import com.example.haengsha.model.network.apiService.RecommendationApiService

interface RecommendationDataRepository {
    suspend fun getRecommendationList(token: String): List<RecommendResponse>
}

class NetworkRecommendationRepository(
    private val recommendationApiService: RecommendationApiService
) : RecommendationDataRepository {
    override suspend fun getRecommendationList(token: String): List<RecommendResponse> {
        return recommendationApiService.getRecommendationList(token)
    }
}