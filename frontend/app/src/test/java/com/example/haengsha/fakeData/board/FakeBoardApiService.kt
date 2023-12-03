package com.example.haengsha.fakeData.board

import com.example.haengsha.model.network.apiService.BoardApiService
import com.example.haengsha.model.network.dataModel.BoardDetailResponse
import com.example.haengsha.model.network.dataModel.BoardListResponse
import com.example.haengsha.model.network.dataModel.BoardPostResponse
import com.example.haengsha.model.network.dataModel.PatchLikeFavoriteResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FakeBoardApiService : BoardApiService {
    override suspend fun getBoardList(startDate: String): List<BoardListResponse> {
        return FakeBoardDataSource.listOfBoardListResponse
    }

    override suspend fun getBoardDetail(token: String, postId: Int): BoardDetailResponse {
        return FakeBoardDataSource.boardDetailResponse
    }

    override suspend fun getFavoriteList(token: String): List<BoardListResponse> {
        return FakeBoardDataSource.listOfBoardListResponse
    }

    override suspend fun postEvent(
        token: String,
        image: MultipartBody.Part?,
        title: RequestBody,
        isFestival: RequestBody,
        duration: RequestBody,
        place: RequestBody,
        time: RequestBody,
        content: RequestBody
    ): BoardPostResponse {
        return FakeBoardDataSource.boardPostResponse
    }

    override suspend fun patchLike(token: String, postId: Int): PatchLikeFavoriteResponse {
        return FakeBoardDataSource.patchLikeFavoriteResponse
    }

    override suspend fun patchFavorite(token: String, postId: Int): PatchLikeFavoriteResponse {
        return FakeBoardDataSource.patchLikeFavoriteResponse
    }

    override suspend fun searchEvent(
        token: String,
        keyword: String,
        isFestival: Int,
        startDate: String,
        endDate: String
    ): List<BoardListResponse> {
        return FakeBoardDataSource.listOfBoardListResponse
    }
}