package com.example.haengsha.ui.screens.dashBoard

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.haengsha.R
import com.example.haengsha.model.network.dataModel.BoardPostRequest
import com.example.haengsha.model.uiState.UserUiState
import com.example.haengsha.model.uiState.board.BoardPostUiState
import com.example.haengsha.model.viewModel.board.BoardApiViewModel
import com.example.haengsha.ui.theme.ButtonBlue
import com.example.haengsha.ui.theme.PlaceholderGrey
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.CheckBox
import com.example.haengsha.ui.uiComponents.ConfirmDialog
import com.example.haengsha.ui.uiComponents.customTextField
import es.dmoral.toasty.Toasty
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

@Composable
fun BoardPostScreen(
    innerPadding: PaddingValues,
    boardApiViewModel: BoardApiViewModel,
    boardNavController: NavController,
    userUiState: UserUiState
) {
    val postContext = LocalContext.current
    val boardPostUiState = boardApiViewModel.boardPostUiState
    var postTrigger by remember { mutableIntStateOf(0) }
    val authToken = "Token ${userUiState.token}"
    var eventTitle by remember { mutableStateOf("") }
    var eventDuration by remember { mutableStateOf("") }
    var eventPlace by remember { mutableStateOf("") }
    var eventTime by remember { mutableStateOf("") }
    var eventContent by remember { mutableStateOf("") }
    var eventCategory by remember { mutableIntStateOf(1) } // 행사면 1
    var postConfirmDialog by remember { mutableStateOf(false) }
    var durationStart = ""
    var durationEnd = ""
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val getImage =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { result ->
            result?.let { uri -> imageUri = uri }
        }

    var boardPostRequest: BoardPostRequest

    // TODO SDK 버전 28 이상만 가능

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Column {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(color = Color(0x00F8F8F8)),
                value = eventTitle,
                onValueChange = { eventTitle = it },
                placeholder = {
                    Text(
                        text = "행사명을 입력해주세요.",
                        fontFamily = poppins,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = PlaceholderGrey,
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0x00F8F8F8),
                    focusedContainerColor = Color(0x00F8F8F8)
                )
            )
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(1) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 25.dp, vertical = 10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "필수 정보를 입력해주세요",
                                fontFamily = poppins,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                            Row(
                                modifier = Modifier.clickable { getImage.launch("image/*") },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "사진 첨부 (선택)",
                                    fontFamily = poppins,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 12.sp
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Icon(
                                    modifier = Modifier.size(14.dp),
                                    imageVector = ImageVector.vectorResource(id = R.drawable.attach_icon),
                                    contentDescription = "attach icon",
                                    tint = Color.Black
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "주최",
                                fontFamily = poppins,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            VerticalDivider(
                                modifier = Modifier.height(16.dp),
                                thickness = 1.dp
                            )
                            customTextField(
                                placeholder = userUiState.nickname,
                                enabled = false
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "일자",
                                fontFamily = poppins,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            VerticalDivider(
                                modifier = Modifier.height(16.dp),
                                thickness = 1.dp
                            )
                            eventDuration = customTextField(
                                placeholder = "2023-11-11 ~ 2023-11-13",
                                enabled = true
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "장소",
                                fontFamily = poppins,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            VerticalDivider(
                                modifier = Modifier.height(16.dp),
                                thickness = 1.dp
                            )
                            eventPlace = customTextField(
                                placeholder = "자하연 앞",
                                enabled = true
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "시간",
                                fontFamily = poppins,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            VerticalDivider(
                                modifier = Modifier.height(16.dp),
                                thickness = 1.dp
                            )
                            eventTime = customTextField(
                                placeholder = "오후 1시 ~ 오후 6시",
                                enabled = true
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "분류",
                                fontFamily = poppins,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            VerticalDivider(
                                modifier = Modifier.height(16.dp),
                                thickness = 1.dp
                            )
                            Spacer(modifier = Modifier.width(15.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier.clickable { eventCategory = 1 }
                                    ) {
                                        CheckBox(
                                            color = if (eventCategory == 1) ButtonBlue else Color.Transparent,
                                            size = 18
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = "공연",
                                        fontFamily = poppins,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 16.sp
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(modifier = Modifier.clickable { eventCategory = 0 }) {
                                        CheckBox(
                                            color = if (eventCategory == 1) Color.Transparent else ButtonBlue,
                                            size = 18
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = "학술",
                                        fontFamily = poppins,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 16.sp
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = PlaceholderGrey
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        imageUri?.let { uri ->
                            Spacer(modifier = Modifier.height(20.dp))
                            Image(
                                painter = rememberAsyncImagePainter(uri),
                                contentDescription = "image",
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier.size(300.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "1:1 비율이 아니면 사진이 잘릴 수 있으며 1장만 첨부할 수 있습니다.",
                                fontFamily = poppins,
                                fontWeight = FontWeight.Normal,
                                fontSize = 11.sp,
                                fontStyle = FontStyle.Italic,
                                color = PlaceholderGrey
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 600.dp),
                        value = eventContent,
                        onValueChange = { eventContent = it },
                        placeholder = {
                            Text(
                                text = "행사 정보를 입력해주세요",
                                fontFamily = poppins,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Normal,
                                color = PlaceholderGrey
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Default
                        ),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0x00F8F8F8),
                            focusedContainerColor = Color(0x00F8F8F8)
                        )
                    )
                }
            }
        }
        Box(modifier = Modifier.offset(330.dp, 600.dp)) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(ButtonBlue, RoundedCornerShape(30.dp))
                    .clickable(onClick = { postConfirmDialog = true }),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(25.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.write_confirm_icon),
                    contentDescription = "event upload Button",
                    tint = Color.White
                )
            }
        }
        if (postConfirmDialog) {
            ConfirmDialog(
                onDismissRequest = { postConfirmDialog = false },
                onClick = {
                    if (eventDuration.contains("~")) {
                        val duration = eventDuration.split("~")
                        if (duration.size > 1) {
                            durationStart = duration[0].trim()
                            durationEnd = duration[1].trim()
                        }
                    }
                    boardPostRequest = buildRequestBody(
                        token = authToken,
                        image = imageUri,
                        title = eventTitle,
                        isFestival = eventCategory,
                        durationStart = durationStart,
                        durationEnd = durationEnd,
                        place = eventPlace,
                        time = eventTime,
                        content = eventContent,
                        postContext = postContext
                    )
                    postTrigger++
                    boardApiViewModel.postEvent(boardPostRequest = boardPostRequest)
                },
                text = "글을 업로드 하시겠어요?"
            )
        }
    }
    if (postTrigger > 0) {
        LaunchedEffect(key1 = boardPostUiState) {
            when (boardPostUiState) {
                is BoardPostUiState.Success -> {
                    postConfirmDialog = false
                    Toasty.success(
                        postContext,
                        "글이 업로드 되었습니다.",
                        Toasty.LENGTH_SHORT
                    ).show()
                    boardNavController.popBackStack()
                }

                is BoardPostUiState.HttpError -> {
                    Toasty.warning(
                        postContext,
                        "글 업로드에 실패했습니다.\n다시 시도해주세요.",
                        Toasty.LENGTH_SHORT
                    ).show()
                }

                is BoardPostUiState.NetworkError -> {
                    Toasty.error(
                        postContext,
                        "인터넷 연결을 확인해주세요",
                        Toasty.LENGTH_SHORT,
                        true
                    ).show()
                }

                is BoardPostUiState.Loading -> {
                    // 로딩중
                }

                else -> {
                    // 앱 오류
                }
            }
        }
    }
}

fun buildRequestBody(
    token: String,
    image: Uri?,
    title: String,
    isFestival: Int,
    durationStart: String,
    durationEnd: String,
    place: String,
    time: String,
    content: String,
    postContext: Context
): BoardPostRequest {
    val titleRequestBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
    val isFestivalRequestBody =
        isFestival.toString().toRequestBody("text/plain".toMediaTypeOrNull())
    val eventDurations =
        "[{\"event_day_1\": \"$durationStart\"}, {\"event_day_2\": \"$durationEnd\"}]"
    val durationRequestBody = eventDurations.toRequestBody("text/plain".toMediaTypeOrNull())
    val placeRequestBody = place.toRequestBody("text/plain".toMediaTypeOrNull())
    val timeRequestBody = time.toRequestBody("text/plain".toMediaTypeOrNull())
    val contentRequestBody = content.toRequestBody("text/plain".toMediaTypeOrNull())


    val bitmap = if (image === null) null else {
        ImageDecoder.decodeBitmap(
            ImageDecoder.createSource(
                postContext.contentResolver,
                image
            )
        )
    }
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val imageRequestBody = if (bitmap === null) null else {
        byteArrayOutputStream.toByteArray().toRequestBody("image/*".toMediaTypeOrNull())
    }
    val imageMultipartBody = if (imageRequestBody === null) null else {
        MultipartBody.Part.createFormData("image", "image.jpeg", imageRequestBody)
    }

    return BoardPostRequest(
        token = token,
        image = imageMultipartBody,
        title = titleRequestBody,
        isFestival = isFestivalRequestBody,
        eventDurations = durationRequestBody,
        place = placeRequestBody,
        time = timeRequestBody,
        content = contentRequestBody
    )
}