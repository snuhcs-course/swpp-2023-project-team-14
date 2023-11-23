package com.example.haengsha.fakeData.board

import com.example.haengsha.model.network.apiService.BoardApiService
import com.example.haengsha.model.network.dataModel.BoardDetailResponse
import com.example.haengsha.model.network.dataModel.BoardListResponse
import com.example.haengsha.model.network.dataModel.BoardPostResponse
import com.example.haengsha.model.network.dataModel.PostLikeFavoriteResponse
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

    override suspend fun postLike(token: String, postId: Int): PostLikeFavoriteResponse {
        return FakeBoardDataSource.postLikeFavoriteResponse
    }

    override suspend fun postFavorite(token: String, postId: Int): PostLikeFavoriteResponse {
        return FakeBoardDataSource.postLikeFavoriteResponse
    }
}