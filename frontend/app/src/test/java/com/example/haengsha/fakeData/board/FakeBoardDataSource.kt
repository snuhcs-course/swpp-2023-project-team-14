package com.example.haengsha.fakeData.board

import com.example.haengsha.model.network.dataModel.Author
import com.example.haengsha.model.network.dataModel.BoardDetailResponse
import com.example.haengsha.model.network.dataModel.BoardListResponse
import com.example.haengsha.model.network.dataModel.BoardPostRequest
import com.example.haengsha.model.network.dataModel.BoardPostResponse
import com.example.haengsha.model.network.dataModel.EventDurationResponse
import com.example.haengsha.model.network.dataModel.PatchLikeFavoriteResponse
import com.example.haengsha.model.network.dataModel.SearchRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

object FakeBoardDataSource {
    const val token = "fakeToken"
    const val date = "2023-11-09"
    const val id = 123
    private const val title = "fakeTitle"
    private const val isFestival = true
    private val author = Author("fakeAuthor")
    private val eventDuration = ArrayList(listOf(EventDurationResponse("fakeEventDuration")))
    private val eventDurations = listOf(EventDurationResponse("fakeEventDuration"))
    private const val place = "fakePlace"
    private const val time = "fakeTime"
    private const val content = "fakeContent"
    private val image = null
    private const val likeCount = 123
    private const val favoriteCount = 123
    private const val isLiked = true
    private const val isFavorite = true
    private const val isFestivalSearch = 1
    private const val keyword = "fakeKeyword"

    private val titleRequestBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
    private val isFestivalRequestBody =
        isFestival.toString().toRequestBody("text/plain".toMediaTypeOrNull())
    private val eventDurationRequestBody =
        eventDuration.toString().toRequestBody("text/plain".toMediaTypeOrNull())
    private val placeRequestBody = place.toRequestBody("text/plain".toMediaTypeOrNull())
    private val timeRequestBody = time.toRequestBody("text/plain".toMediaTypeOrNull())
    private val contentRequestBody = content.toRequestBody("text/plain".toMediaTypeOrNull())

    private val boardListResponse = BoardListResponse(
        id,
        title,
        isFestival,
        author,
        eventDurations,
        place,
        time,
        content,
        image,
        likeCount,
        favoriteCount
    )

    val listOfBoardListResponse = listOf(boardListResponse)

    val boardDetailResponse = BoardDetailResponse(
        id,
        title,
        isFestival,
        author,
        eventDurations,
        place,
        time,
        content,
        image,
        likeCount,
        favoriteCount,
        isLiked,
        isFavorite
    )

    val patchLikeFavoriteResponse = PatchLikeFavoriteResponse(
        id,
        title,
        isFestival,
        author,
        eventDurations,
        place,
        time,
        content,
        image,
        likeCount,
        favoriteCount,
        isLiked,
        isFavorite
    )

    val boardPostRequest = BoardPostRequest(
        token,
        image,
        titleRequestBody,
        isFestivalRequestBody,
        eventDurationRequestBody,
        placeRequestBody,
        timeRequestBody,
        contentRequestBody
    )

    val boardPostResponse = BoardPostResponse(
        id,
        title,
        isFestival,
        author,
        place,
        time,
        content,
        image,
        eventDuration,
        likeCount,
        favoriteCount
    )

    val searchRequest = SearchRequest(
        token,
        keyword,
        isFestivalSearch,
        date,
        date
    )
}