package com.example.haengsha.fakeData.home

import com.example.haengsha.model.network.apiService.HomeApiService
import com.example.haengsha.model.network.dataModel.EventResponse
import com.example.haengsha.model.network.dataModel.RecommendResponse

class FakeHomeApiService : HomeApiService {
    override suspend fun getEventByDate(
        keyword: String,
        isFestival: Int,
        date: String
    ): List<EventResponse> {
        return FakeHomeDataSource.listOfEventResponse
    }

    override suspend fun getRecommendationList(token: String): List<RecommendResponse> {
        return FakeHomeDataSource.listOfRecommendResponse
    }
}