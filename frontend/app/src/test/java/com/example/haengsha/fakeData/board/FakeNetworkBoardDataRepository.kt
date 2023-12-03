package com.example.haengsha.fakeData.board

import com.example.haengsha.model.dataSource.BoardDataRepository
import com.example.haengsha.model.network.dataModel.BoardDetailResponse
import com.example.haengsha.model.network.dataModel.BoardListResponse
import com.example.haengsha.model.network.dataModel.BoardPostRequest
import com.example.haengsha.model.network.dataModel.BoardPostResponse
import com.example.haengsha.model.network.dataModel.PatchLikeFavoriteResponse
import com.example.haengsha.model.network.dataModel.SearchRequest

class FakeNetworkBoardDataRepository : BoardDataRepository {
    override suspend fun getBoardList(startDate: String): List<BoardListResponse> {
        return FakeBoardDataSource.listOfBoardListResponse
    }

    override suspend fun getBoardDetail(token: String, postId: Int): BoardDetailResponse {
        return FakeBoardDataSource.boardDetailResponse
    }

    override suspend fun getFavoriteList(token: String): List<BoardListResponse> {
        return FakeBoardDataSource.listOfBoardListResponse
    }

    override suspend fun postEvent(boardPostRequest: BoardPostRequest): BoardPostResponse {
        return FakeBoardDataSource.boardPostResponse
    }

    override suspend fun patchLike(token: String, postId: Int): PatchLikeFavoriteResponse {
        return FakeBoardDataSource.patchLikeFavoriteResponse
    }

    override suspend fun patchFavorite(token: String, postId: Int): PatchLikeFavoriteResponse {
        return FakeBoardDataSource.patchLikeFavoriteResponse
    }

    override suspend fun searchEvent(searchRequest: SearchRequest): List<BoardListResponse> {
        return FakeBoardDataSource.listOfBoardListResponse
    }
}