package com.example.haengsha.ui.uiComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.haengsha.R
import com.example.haengsha.model.network.dataModel.BoardListResponse
import com.example.haengsha.ui.theme.HaengshaBlue
import com.example.haengsha.ui.theme.HaengshaGrey
import com.example.haengsha.ui.theme.LikePink
import com.example.haengsha.ui.theme.PlaceholderGrey
import com.example.haengsha.ui.theme.poppins

@Composable
fun listItem(
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
                    text = if (event.eventDurations.size > 1) {
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
fun CommentList(
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