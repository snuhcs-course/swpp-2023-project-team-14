package com.example.haengsha.model.network.apiService

import com.example.haengsha.model.network.dataModel.BoardResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface BoardApiService {
    @GET("/api/post/start_date/{start_date}")
    suspend fun getBoardList(
        @Header("Authorization") token: String,
        @Path("start_date") startDate: String
    ): BoardResponse

    @GET("/api/post/{post_id}")
    suspend fun getBoardDetail(
        @Path("post_id") postId: Int,
    ): BoardResponse
}