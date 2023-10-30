package com.example.haengsha.model.network.apiService

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.haengsha.ui.screens.home.EventCardData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import retrofit2.http.GET
import retrofit2.http.Path
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Serializable
data class EventResponse(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("is_festival") val isFestival: Boolean,
    @SerialName("author") val author: Author,
    @SerialName("event_durations") val eventDurations: List<EventDurationResponse>,
    @SerialName("place") val place: String,
    @SerialName("time") val time: String?,
    @SerialName("content") val content: String,
    @SerialName("image") val image: String?,
    @SerialName("like_count") val likeCount: Int,
    @SerialName("favorite_count") val favoriteCount: Int,
)

@Serializable
data class Author(
    @SerialName("nickname") val nickname: String
)

@Serializable
data class EventDurationResponse(
    @SerialName("event_day") val eventDay: String
)

@RequiresApi(Build.VERSION_CODES.O)
fun EventResponse.toEventCardData(): EventCardData {
    var startDate = stringToDate(eventDurations[0].eventDay)
    var endDate = startDate
    if (eventDurations.size > 1) {
        endDate = stringToDate(eventDurations[eventDurations.size - 1].eventDay)
    }

    var eventType = "Festival"
    if (!isFestival) {
        eventType = "Academic"
    }
    return EventCardData(
        organizer = author.nickname,
        eventTitle = title,
        startDate = startDate,
        endDate = endDate,
        likes = likeCount,
        favorites = favoriteCount,
        eventType = eventType,
        place = place,
        time = time ?: "wow"
    )
}

interface EventApiService {
    @GET("/api/post/festival/{eventType}/date/{date}/{date}")
    suspend fun getEventByDate(
        @Path("eventType") eventType: Int,  // Replace with appropriate type
        @Path("date") date: String
    ): List<EventResponse>? // Change the return type to a list of EventResponse
}

@RequiresApi(Build.VERSION_CODES.O)
fun stringToDate(dateString: String): LocalDate {
    var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    try {
        return LocalDate.parse(dateString, formatter)
        //val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        //date = format.parse(dateString)!!
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return LocalDate.now()
}
