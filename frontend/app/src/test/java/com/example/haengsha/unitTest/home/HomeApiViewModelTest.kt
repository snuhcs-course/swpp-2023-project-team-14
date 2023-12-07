package com.example.haengsha.unitTest.home

import com.example.haengsha.fakeData.home.FakeHomeDataSource
import com.example.haengsha.fakeData.home.FakeNetworkHomeDataRepository
import com.example.haengsha.model.uiState.home.HomeApiUiState
import com.example.haengsha.model.uiState.home.RecommendationApiUiState
import com.example.haengsha.model.viewModel.home.HomeApiViewModel
import com.example.haengsha.testRules.TestDispatcherRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class HomeApiViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()

    @Test
    fun homeViewModel_getEventByDate_verifyHomeUiStateSuccess() = runTest {
        val homeApiViewModel = HomeApiViewModel(
            homeDataRepository = FakeNetworkHomeDataRepository()
        )
        homeApiViewModel.getEventByDate(FakeHomeDataSource.date)
        assertEquals(
            HomeApiUiState.Success(
                FakeHomeDataSource.listOfEventResponse,
                FakeHomeDataSource.listOfEventResponse
            ), homeApiViewModel.homeApiUiState
        )
    }

    @Test
    fun homeViewModel_getRecommendationList_verifyRecommendationUiStateSuccess() = runTest {
        val homeApiViewModel = HomeApiViewModel(
            homeDataRepository = FakeNetworkHomeDataRepository()
        )
        homeApiViewModel.getRecommendationList(FakeHomeDataSource.token)
        assertEquals(
            RecommendationApiUiState.RecommendationListResult(
                FakeHomeDataSource.listOfRecommendResponse
            ), homeApiViewModel.recommendationApiUiState
        )
    }
}