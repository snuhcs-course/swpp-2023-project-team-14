package com.example.haengsha.ui.uiComponents

import android.content.Context
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.haengsha.R
import com.example.haengsha.model.dataSource.SampleImage
import com.example.haengsha.model.network.dataModel.BoardListResponse
import com.example.haengsha.ui.theme.HaengshaBlue
import com.example.haengsha.ui.theme.HaengshaGrey
import com.example.haengsha.ui.theme.LikePink
import com.example.haengsha.ui.theme.PlaceholderGrey
import com.example.haengsha.ui.theme.md_theme_light_surface
import com.example.haengsha.ui.theme.poppins
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun boardListItem(
    isFavorite: Boolean,
    event: BoardListResponse
): Int {
    val configuration = LocalConfiguration.current
    val deviceWidth = configuration.screenWidthDp.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 15.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(top = 2.2.dp),
                text = if (event.isFestival == true) "공연" else "학술",
                fontFamily = poppins,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = HaengshaGrey
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.widthIn(max = deviceWidth - 80.dp),
                text = event.title,
                fontFamily = poppins,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis
            )
            if (isFavorite) {
                Spacer(Modifier.width(5.dp))
                Image(
                    modifier = Modifier
                        .size(15.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.favorite_fill_icon),
                    contentDescription = "favorite icon",
                    alignment = Alignment.TopCenter,
                    contentScale = ContentScale.Inside
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    modifier = Modifier.widthIn(max = 130.dp),
                    text = event.place ?: "장소가 등록되지 않았어요",
                    fontFamily = poppins,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal,
                    color = HaengshaGrey,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .size(width = 1.dp, height = 13.dp)
                        .padding(bottom = 1.dp)
                        .background(PlaceholderGrey)
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    modifier = Modifier.widthIn(max = 120.dp),
                    text = if (event.eventDurations.isEmpty()) {
                        "날짜 등록 안 됨"
                    } else if (event.eventDurations.size > 1) {
                        event.eventDurations[0].eventDay + " ~ " + event.eventDurations.last().eventDay
                    } else {
                        event.eventDurations[0].eventDay
                    },
                    fontFamily = poppins,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal,
                    color = HaengshaGrey,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Image(
                    modifier = Modifier.size(12.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.like_fill_icon),
                    contentDescription = "like icon",
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Inside
                )
                Spacer(Modifier.width(5.dp))
                Text(
                    text = event.likeCount.toString(),
                    fontFamily = poppins,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = LikePink
                )
            }
        }
    }
    return event.id
}

@Composable
fun HomeListItem(
    organizer: String,
    eventTitle: String,
    image: String?,
    startDate: LocalDate,
    endDate: LocalDate,
    likes: Int,
    eventType: String,
    homeContext: Context
) {
    val configuration = LocalConfiguration.current
    val deviceWidth = configuration.screenWidthDp.dp
    val deviceHeight = configuration.screenHeightDp.dp

    val formatter = DateTimeFormatter.ofPattern("MM-dd")
    val eventDuration = startDate.format(formatter) + " ~ " + endDate.format(formatter)

    val randomFestivalId by rememberSaveable { mutableIntStateOf((0..9).random()) }
    val randomAcademicId by rememberSaveable { mutableIntStateOf((10..19).random()) }

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
                val painter = rememberAsyncImagePainter(model = imageModel)
                if (painter.state is AsyncImagePainter.State.Error) {
                    Image(
                        painter = painterResource(SampleImage.ImageAcademicDefault.id),
                        contentDescription = "sample image",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(10.dp)),
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop
                    )
                } else {
                    AsyncImage(
                        model = imageModel,
                        contentDescription = "festival poster",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(10.dp)),
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop
                    )
                }
            } else {
                val imageId = if (eventType == "Festival") {
                    SampleImage.entries[randomFestivalId].id
                } else {
                    SampleImage.entries[randomAcademicId].id
                }
                Image(
                    painter = painterResource(imageId),
                    contentDescription = "sample image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10.dp)),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop
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

@Composable
fun CommentListItem(
    userNickName: String,
    commentDate: String,
    commentContent: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(vertical = 10.dp, horizontal = 15.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(25.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.user_comment_icon),
                contentDescription = "user comment Icon",
                tint = HaengshaBlue
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = userNickName,
                fontFamily = poppins,
                fontSize = 13.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = commentDate,
                fontFamily = poppins,
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                color = PlaceholderGrey
            )
        }
        Text(
            text = commentContent,
            fontFamily = poppins,
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun BoardListPreview() {
//    HaengshaTheme {
//        LazyColumn(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            items(3) {
//                BoardList(true)
//            }
//        }
//    }
//}