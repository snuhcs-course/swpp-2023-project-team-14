package com.example.haengsha.model.network.apiService

import com.example.haengsha.model.network.dataModel.BoardDetailResponse
import com.example.haengsha.model.network.dataModel.BoardListResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface BoardApiService {
    @GET("/api/post/start_date/{start_date}")
    suspend fun getBoardList(
        @Path("start_date") startDate: String
    ): List<BoardListResponse>

    @GET("/api/post/{post_id}")
    suspend fun getBoardDetail(
        @Header("Authorization") token: String,
        @Path("post_id") postId: Int,
    ): BoardDetailResponse

    @GET("/api/favorite")
    suspend fun getFavoriteList(
        @Header("Authorization") token: String
    ): List<BoardListResponse>
}