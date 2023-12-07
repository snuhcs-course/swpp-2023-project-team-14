package com.example.haengsha.fakeData.home

import com.example.haengsha.model.network.dataModel.Author
import com.example.haengsha.model.network.dataModel.EventDurationResponse
import com.example.haengsha.model.network.dataModel.EventResponse
import com.example.haengsha.model.network.dataModel.RecommendResponse
import java.time.LocalDate

object FakeHomeDataSource {
    const val token = "fakeToken"
    val date: LocalDate = LocalDate.now()
    private const val id = 123
    private const val title = "fakeTitle"
    private const val isFestival = true
    private val author = Author("fakeAuthor")
    private val eventDurations = listOf(EventDurationResponse("fakeEventDuration"))
    private const val place = "fakePlace"
    private const val time = "fakeTime"
    private const val content = "fakeContent"
    private const val image = "fakeImage"
    private const val likeCount = 123
    private const val favoriteCount = 123
    private const val score = 123

    private val eventResponse = EventResponse(
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

    private val recommendResponse = RecommendResponse(
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
        score
    )

    val listOfEventResponse = listOf(eventResponse)
    val listOfRecommendResponse = listOf(recommendResponse)
}