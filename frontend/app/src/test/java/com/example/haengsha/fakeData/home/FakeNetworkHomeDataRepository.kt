package com.example.haengsha.fakeData.home

import com.example.haengsha.model.dataSource.HomeDataRepository
import com.example.haengsha.model.network.dataModel.EventResponse
import com.example.haengsha.model.network.dataModel.RecommendResponse

class FakeNetworkHomeDataRepository : HomeDataRepository {
    override suspend fun getEventByDate(eventType: Int, date: String): List<EventResponse> {
        return FakeHomeDataSource.listOfEventResponse
    }

    override suspend fun getRecommendationList(token: String): List<RecommendResponse> {
        return FakeHomeDataSource.listOfRecommendResponse
    }
}