package com.example.haengsha.model.dataSource

import com.example.haengsha.model.network.apiService.EventApiService
import com.example.haengsha.model.network.apiService.EventResponse

interface EventDataRepository {
    suspend fun getEventByDate(eventType: Int, date: String): List<EventResponse>?
}


class NetworkEventDataRepository(
    private val eventApiService: EventApiService
) : EventDataRepository {
    override suspend fun getEventByDate(eventType: Int, date: String): List<EventResponse>? {
        return eventApiService.getEventByDate("", eventType, date)
    }
}