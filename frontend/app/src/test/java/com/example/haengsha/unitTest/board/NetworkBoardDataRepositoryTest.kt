package com.example.haengsha.unitTest.board

import com.example.haengsha.fakeData.board.FakeBoardApiService
import com.example.haengsha.fakeData.board.FakeBoardDataSource
import com.example.haengsha.model.dataSource.NetworkBoardDataRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class NetworkBoardDataRepositoryTest {
    private val repository = NetworkBoardDataRepository(
        boardApiService = FakeBoardApiService()
    )

    @Test
    fun networkBoardDataRepository_getBoardList_verifyResponse() {
        runTest {
            assertEquals(
                FakeBoardDataSource.listOfBoardListResponse,
                repository.getBoardList(FakeBoardDataSource.date)
            )
        }
    }

    @Test
    fun networkBoardDataRepository_getBoardDetail_verifyResponse() {
        runTest {
            assertEquals(
                FakeBoardDataSource.boardDetailResponse,
                repository.getBoardDetail(FakeBoardDataSource.token, FakeBoardDataSource.id)
            )
        }
    }

    @Test
    fun networkBoardDataRepository_getFavoriteBoardList_verifyResponse() {
        runTest {
            assertEquals(
                FakeBoardDataSource.listOfBoardListResponse,
                repository.getFavoriteList(FakeBoardDataSource.token)
            )
        }
    }

    @Test
    fun networkBoardDataRepository_postEvent_verifyResponse() {
        runTest {
            assertEquals(
                FakeBoardDataSource.boardPostResponse,
                repository.postEvent(FakeBoardDataSource.boardPostRequest)
            )
        }
    }

    @Test
    fun networkBoardDataRepository_patchLike_verifyResponse() {
        runTest {
            assertEquals(
                FakeBoardDataSource.patchLikeFavoriteResponse,
                repository.patchLike(FakeBoardDataSource.token, FakeBoardDataSource.id)
            )
        }
    }

    @Test
    fun networkBoardDataRepository_patchFavorite_verifyResponse() {
        runTest {
            assertEquals(
                FakeBoardDataSource.patchLikeFavoriteResponse,
                repository.patchFavorite(FakeBoardDataSource.token, FakeBoardDataSource.id)
            )
        }
    }

    @Test
    fun networkBoardDataRepository_searchEvent_verifyResponse() {
        runTest {
            assertEquals(
                FakeBoardDataSource.listOfBoardListResponse,
                repository.searchEvent(FakeBoardDataSource.searchRequest)
            )
        }
    }
}