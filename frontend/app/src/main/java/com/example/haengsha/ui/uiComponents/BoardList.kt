package com.example.haengsha.ui.uiComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.haengsha.R
import com.example.haengsha.ui.theme.HaengshaBlue
import com.example.haengsha.ui.theme.HaengshaGrey
import com.example.haengsha.ui.theme.HaengshaTheme
import com.example.haengsha.ui.theme.LikePink
import com.example.haengsha.ui.theme.PlaceholderGrey
import com.example.haengsha.ui.theme.poppins

@Composable
fun BoardList(isFavorite: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable { /*TODO 상세페이지 보여주기*/ }
            .padding(horizontal = 15.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "공연",
                fontFamily = poppins,
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraLight,
                color = HaengshaGrey
            ) // TODO 공연 / 학술 카테고리
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "ArtSpace@SNU 학생 공연",
                fontFamily = poppins,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            ) // TODO 행사 이름
            if (isFavorite) {
                Spacer(Modifier.width(5.dp))
                Image(
                    modifier = Modifier
                        .size(15.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.favorite_fill_icon),
                    contentDescription = "favorite icon",
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Inside
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    modifier = Modifier.width(130.dp),
                    text = "장소: 신촌 바플라이 2호점 별밤",
                    fontFamily = poppins,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraLight,
                    color = HaengshaGrey,
                    overflow = TextOverflow.Ellipsis
                ) // TODO 행사 장소
                Box(
                    modifier = Modifier
                        .size(width = 1.dp, height = 13.dp)
                        .padding(bottom = 1.dp)
                        .background(PlaceholderGrey)
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    modifier = Modifier.width(130.dp),
                    text = "일자: 23/10/08 ~ 23/10/11",
                    fontFamily = poppins,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraLight,
                    color = HaengshaGrey,
                    overflow = TextOverflow.Ellipsis
                ) // TODO 행사 일자
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(15.dp)
                        .padding(top = 3.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.like_fill_icon),
                    contentDescription = "like icon",
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Inside
                )
                Spacer(Modifier.width(3.dp))
                Text(
                    text = "421",
                    fontFamily = poppins,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    color = LikePink
                )
            } // TODO 좋아요
        }
    }
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

@Preview(showBackground = true)
@Composable
fun BoardListPreview() {
    HaengshaTheme {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(3) {
                BoardList(true)
            }
        }
    }
}