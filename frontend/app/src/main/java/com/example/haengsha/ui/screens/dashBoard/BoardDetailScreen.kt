package com.example.haengsha.ui.screens.dashBoard

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.haengsha.R
import com.example.haengsha.model.uiState.UserUiState
import com.example.haengsha.model.uiState.board.BoardDetailUiState
import com.example.haengsha.model.uiState.board.PostLikeFavoriteUiState
import com.example.haengsha.model.viewModel.board.BoardApiViewModel
import com.example.haengsha.ui.theme.FavoriteYellow
import com.example.haengsha.ui.theme.LikePink
import com.example.haengsha.ui.theme.PlaceholderGrey
import com.example.haengsha.ui.theme.poppins
import es.dmoral.toasty.Toasty

@Composable
fun BoardDetailScreen(
    innerPadding: PaddingValues,
    boardApiViewModel: BoardApiViewModel,
    userUiState: UserUiState,
    eventId: Int
) {
    val boardContext = LocalContext.current
    val boardDetailUiState = boardApiViewModel.boardDetailUiState
    LaunchedEffect(Unit) {
        boardApiViewModel.getBoardDetail(userUiState.token, eventId)
    }

    when (boardDetailUiState) {
        is BoardDetailUiState.HttpError -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Toasty.warning(boardContext, "행사 정보에 문제가 있어요", Toasty.LENGTH_SHORT).show()
            }
        }

        is BoardDetailUiState.NetworkError -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Toasty.error(boardContext, "네트워크 연결을 확인해주세요.", Toasty.LENGTH_SHORT).show()
            }
        }

        is BoardDetailUiState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Toasty.error(boardContext, "알 수 없는 에러가 발생했어요 :( 메일로 제보해주세요!", Toasty.LENGTH_SHORT)
                    .show()
            }
        }

        is BoardDetailUiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }

        is BoardDetailUiState.BoardDetailResult -> {
            val boardDetail = boardDetailUiState.boardDetail
            var likeCount by remember { mutableIntStateOf(boardDetail.likeCount) }
            var favoriteCount by remember { mutableIntStateOf(boardDetail.favoriteCount) }
            var isLiked by remember { mutableStateOf(boardDetail.isLiked) }
            var isFavorite by remember { mutableStateOf(boardDetail.isFavorite) }
            when (val postLikeFavoriteUiState = boardApiViewModel.postLikeFavoriteUiState) {
                is PostLikeFavoriteUiState.Success -> {
                    likeCount = postLikeFavoriteUiState.likeCount
                    favoriteCount = postLikeFavoriteUiState.favoriteCount
                    isLiked = postLikeFavoriteUiState.isLiked
                    isFavorite = postLikeFavoriteUiState.isFavorite
                }

                is PostLikeFavoriteUiState.Loading -> {
                    // 로딩
                }

                is PostLikeFavoriteUiState.HttpError -> {
                    Toasty.warning(boardContext, "오류가 발생했습니다.\n다시 시도해주세요.", Toasty.LENGTH_SHORT)
                        .show()
                }

                is PostLikeFavoriteUiState.NetworkError -> {
                    Toasty.error(boardContext, "네트워크 연결을 확인해주세요.", Toasty.LENGTH_SHORT).show()
                }

                is PostLikeFavoriteUiState.Error -> {
                    // 앱 오류
                }
            }

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
                                text = boardDetail.title,
                                fontFamily = poppins,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
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
                                    text = boardDetail.author?.nickname ?: "닉네임이 없어요",
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
                                    text = if (boardDetail.eventDurations.size > 1) {
                                        boardDetail.eventDurations[0].eventDay + "~" + boardDetail.eventDurations.last().eventDay
                                    } else {
                                        boardDetail.eventDurations[0].eventDay
                                    },
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
                                    text = boardDetail.place ?: "장소가 등록되지 않았어요",
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
                                    text = boardDetail.time ?: "시간이 등록되지 않았어요",
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
                            if ((boardDetail.image?.isNotEmpty() == true && boardDetail.image != "image.jpg")) {
                                val imageModel = ImageRequest.Builder(context = boardContext)
                                    .data(boardDetail.image)
                                    .size(Size.ORIGINAL)
                                    .build()
                                val painter = rememberAsyncImagePainter(model = imageModel)
                                Box(contentAlignment = Alignment.Center) {
                                    AsyncImage(
                                        model = imageModel,
                                        contentDescription = "festival poster",
                                        modifier = Modifier.size(360.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                    if (painter.state is AsyncImagePainter.State.Loading) {
                                        CircularProgressIndicator()
                                    }
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                            }
                            Text(
                                text = boardDetail.content ?: "내용이 없어요",
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
                                    modifier = Modifier.clickable(
                                        // TODO enabled = userUiState.role == "User"
                                    ) { boardApiViewModel.postLike(userUiState.token, eventId) },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier.size(20.dp),
                                        imageVector = if (isLiked) {
                                            ImageVector.vectorResource(id = R.drawable.like_fill_icon)
                                        } else {
                                            ImageVector.vectorResource(id = R.drawable.like_empty_icon)
                                        },
                                        contentDescription = "like icon",
                                        tint = LikePink
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = likeCount.toString(),
                                        fontFamily = poppins,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = LikePink
                                    )
                                }
                                /*
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
                                */
                                Spacer(modifier = Modifier.width(8.dp))
                                Row(
                                    modifier = Modifier.clickable(
                                        // TODO enabled = userUiState.role == "User"
                                    ) {
                                        boardApiViewModel.postFavorite(
                                            userUiState.token,
                                            eventId
                                        )
                                    },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier.size(20.dp),
                                        imageVector = if (isFavorite) {
                                            ImageVector.vectorResource(id = R.drawable.favorite_fill_icon)
                                        } else {
                                            ImageVector.vectorResource(id = R.drawable.favorite_empty_icon)
                                        },
                                        contentDescription = "favorite count icon",
                                        tint = FavoriteYellow
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = favoriteCount.toString(),
                                        fontFamily = poppins,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = FavoriteYellow
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            /* TODO 댓글 UI
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
                            */
                        }
                    }
                    /*
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
                    */
                }
                /*
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
                */
            }
        }
    }
}