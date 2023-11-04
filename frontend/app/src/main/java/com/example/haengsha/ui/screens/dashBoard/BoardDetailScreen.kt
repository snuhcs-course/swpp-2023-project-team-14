package com.example.haengsha.ui.screens.dashBoard

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.haengsha.R
import com.example.haengsha.ui.theme.CommentBlue
import com.example.haengsha.ui.theme.FavoriteYellow
import com.example.haengsha.ui.theme.HaengshaGrey
import com.example.haengsha.ui.theme.LikePink
import com.example.haengsha.ui.theme.PlaceholderGrey
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.CommentButton
import com.example.haengsha.ui.uiComponents.CommentList
import com.example.haengsha.ui.uiComponents.commentTextField

@Composable
fun BoardDetailScreen(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f, false)
        ) {
            items(1) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 30.dp, end = 30.dp)
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "ArtSpace@SNU 학생 공연",
                        fontFamily = poppins,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "공연",
                            fontFamily = poppins,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal,
                            color = HaengshaGrey
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        VerticalDivider(
                            modifier = Modifier.height(13.dp),
                            thickness = 1.dp,
                            color = PlaceholderGrey
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "음악대학",
                            fontFamily = poppins,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal,
                            color = HaengshaGrey
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "주최",
                            fontFamily = poppins,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        VerticalDivider(
                            modifier = Modifier.height(14.dp),
                            thickness = 1.dp
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "국제 자원봉사동아리 GIV",
                            fontFamily = poppins,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "일자",
                            fontFamily = poppins,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        VerticalDivider(
                            modifier = Modifier.height(14.dp),
                            thickness = 1.dp
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "2023.09.12 ~ 2023.09.13",
                            fontFamily = poppins,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "장소",
                            fontFamily = poppins,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        VerticalDivider(
                            modifier = Modifier.height(14.dp),
                            thickness = 1.dp
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "낙성대 우리 지금 만나",
                            fontFamily = poppins,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "시간",
                            fontFamily = poppins,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        VerticalDivider(
                            modifier = Modifier.height(14.dp),
                            thickness = 1.dp
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "오후 6시 ~ 오전 4시",
                            fontFamily = poppins,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = PlaceholderGrey
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier.size(360.dp),
                            painter = painterResource(id = R.drawable.nudge_image),
                            contentDescription = "festival poster",
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = stringResource(id = R.string.terms_of_use),
                        fontFamily = poppins,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.clickable { /*TODO 추천 누르고 하트도 바꾸기*/ },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = ImageVector.vectorResource(id = R.drawable.like_empty_icon),
                                contentDescription = "like icon",
                                tint = LikePink
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "421",
                                fontFamily = poppins,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal,
                                color = LikePink
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = ImageVector.vectorResource(id = R.drawable.comment_count_icon),
                                contentDescription = "comment count icon",
                                tint = CommentBlue
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "14",
                                fontFamily = poppins,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal,
                                color = CommentBlue
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Row(
                            modifier = Modifier.clickable { /*TODO 즐겨찾기 누르고 별 바꾸기*/ },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = ImageVector.vectorResource(id = R.drawable.favorite_empty_icon),
                                contentDescription = "favorite count icon",
                                tint = FavoriteYellow
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "26",
                                fontFamily = poppins,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal,
                                color = FavoriteYellow
                            )
                        }

                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = PlaceholderGrey
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "국제 자원봉사동아리 GIV\n",
                        fontFamily = poppins,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = stringResource(id = R.string.long_text),
                        fontFamily = poppins,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
            items(1) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 2.dp,
                    color = HaengshaGrey
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    modifier = Modifier.padding(start = 30.dp),
                    text = "댓글",
                    fontFamily = poppins,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(15.dp))
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = HaengshaGrey
                )
            }
            items(20) {
                CommentList(
                    userNickName = "천식만 먹는 사람",
                    commentDate = "2023.10.29",
                    commentContent = "여기 작년에 갔었는데 라인업 완전 망했음 ㅋㅋㅋ"
                )
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = PlaceholderGrey
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
                color = HaengshaGrey
            )
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                commentTextField()
                CommentButton(isCommented = true) { /*TODO onClick 넣기*/ }
            }
        }
    }
}
