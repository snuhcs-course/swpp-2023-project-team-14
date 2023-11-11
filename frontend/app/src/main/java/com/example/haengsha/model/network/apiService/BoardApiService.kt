package com.example.haengsha.model.network.apiService

import com.example.haengsha.model.network.dataModel.BoardDetailResponse
import com.example.haengsha.model.network.dataModel.BoardListResponse
import com.example.haengsha.model.network.dataModel.BoardPostResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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

    @Multipart
    @POST("/api/post")
    suspend fun postEvent(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part?,
        @Part("title") title: RequestBody,
        @Part("is_festival") isFestival: RequestBody,
        @Part("event_durations") eventDurations: List<RequestBody>,
        @Part("place") place: RequestBody,
        @Part("time") time: RequestBody,
        @Part("content") content: RequestBody
    ): BoardPostResponse
}