package com.example.haengsha.ui.screens.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.haengsha.R
import com.example.haengsha.ui.theme.LikePink
import com.example.haengsha.ui.theme.md_theme_light_surface
import com.example.haengsha.ui.theme.poppins
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun formatDateToMMDD(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("MM-dd")
    return date.format(formatter)
}


@Composable
fun EventCard(
    organizer: String,
    eventTitle: String,
    image: String?,
    startDate: LocalDate,
    endDate: LocalDate,
    likes: Int,
    homeContext: Context
) {
    val configuration = LocalConfiguration.current
    val deviceWidth = configuration.screenWidthDp.dp
    val deviceHeight = configuration.screenHeightDp.dp
    val eventDuration = formatDateToMMDD(startDate) + " ~ " + formatDateToMMDD(endDate)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(deviceHeight / 5)
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .testTag("EventCard"),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(deviceHeight / 6)
                .border(1.dp, Color.Black, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            if ((image?.isNotEmpty() == true && image != "image.jpg")) {
                val imageModel = ImageRequest.Builder(context = homeContext)
                    .data(image)
                    .size(Size.ORIGINAL)
                    .build()
                AsyncImage(
                    model = imageModel,
                    contentDescription = "festival poster",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            } else {
                Text(
                    text = "(이미지 없음)",
                    fontFamily = poppins,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray.copy(0.5f)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(deviceHeight / 7)
        ) {
            Box(
                modifier = Modifier
                    .height(deviceHeight / 7)
                    .width(1.dp)
                    .zIndex(1f),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(deviceHeight / 7 - 2.dp)
                        .background(md_theme_light_surface)
                        .zIndex(1f)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        1.dp, Color.Black,
                        RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp)
                    ),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = eventDuration,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, end = 10.dp),
                    fontFamily = poppins,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                    textAlign = TextAlign.End
                )
                Column(modifier = Modifier.padding(start = 8.dp)) {
                    Text(
                        text = organizer,
                        modifier = Modifier.widthIn(max = deviceWidth / 2f),
                        fontFamily = poppins,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = eventTitle,
                        modifier = Modifier.widthIn(max = deviceWidth / 1.8f),
                        fontFamily = poppins,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 3.dp, end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        modifier = Modifier
                            .size(12.dp)
                            .padding(top = 1.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.like_fill_icon),
                        contentDescription = "like icon",
                        tint = LikePink
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = likes.toString(),
                        modifier = Modifier.padding(top = 2.dp),
                        fontFamily = poppins,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = LikePink
                    )
                }
            }
        }
    }
}

