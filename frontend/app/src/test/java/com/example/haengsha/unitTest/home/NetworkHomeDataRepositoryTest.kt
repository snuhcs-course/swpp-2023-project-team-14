package com.example.haengsha.unitTest.home

import com.example.haengsha.fakeData.home.FakeHomeApiService
import com.example.haengsha.fakeData.home.FakeHomeDataSource
import com.example.haengsha.model.dataSource.NetworkHomeDataRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class NetworkHomeDataRepositoryTest {
    private val repository = NetworkHomeDataRepository(
        homeApiService = FakeHomeApiService()
    )

    @Test
    fun networkHomeDataRepository_getHomeList_verifyResponse() {
        runTest {
            assertEquals(
                FakeHomeDataSource.listOfEventResponse,
                repository.getEventByDate(0, FakeHomeDataSource.date.toString())
            )
        }
    }

    @Test
    fun networkHomeDataRepository_getRecommendationList_verifyResponse() {
        runTest {
            assertEquals(
                FakeHomeDataSource.listOfRecommendResponse,
                repository.getRecommendationList(FakeHomeDataSource.token)
            )
        }
    }
}