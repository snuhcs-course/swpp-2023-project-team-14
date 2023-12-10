package com.example.haengsha.ui.screens.board

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.haengsha.R
import com.example.haengsha.model.network.dataModel.BoardPostRequest
import com.example.haengsha.model.uiState.UserUiState
import com.example.haengsha.model.uiState.board.BoardPostApiUiState
import com.example.haengsha.model.viewModel.board.BoardApiViewModel
import com.example.haengsha.model.viewModel.board.BoardViewModel
import com.example.haengsha.ui.theme.ButtonBlue
import com.example.haengsha.ui.theme.HaengshaBlue
import com.example.haengsha.ui.theme.PlaceholderGrey
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.BoardDatePickerDialog
import com.example.haengsha.ui.uiComponents.CheckBox
import com.example.haengsha.ui.uiComponents.ConfirmDialog
import com.example.haengsha.ui.uiComponents.CustomCircularProgressIndicator
import com.example.haengsha.ui.uiComponents.CustomHorizontalDivider
import com.example.haengsha.ui.uiComponents.CustomVerticalDivider
import com.example.haengsha.ui.uiComponents.customLargeTextField
import com.example.haengsha.ui.uiComponents.customSingleLineTextField
import es.dmoral.toasty.Toasty
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

@Composable
fun BoardPostScreen(
    innerPadding: PaddingValues,
    boardApiViewModel: BoardApiViewModel,
    boardViewModel: BoardViewModel,
    boardNavController: NavController,
    userUiState: UserUiState
) {
    val configuration = LocalConfiguration.current
    val deviceWidth = configuration.screenWidthDp.dp
    val deviceHeight = configuration.screenHeightDp.dp
    val postContext = LocalContext.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val boardPostUiState = boardViewModel.boardPostUiState.collectAsState()
    val boardPostApiUiState = boardApiViewModel.boardPostApiUiState
    var isPost by remember { mutableStateOf(false) }
    var postConfirmDialog by remember { mutableStateOf(false) }
    var exitConfirmDialog by remember { mutableStateOf(false) }
    var startDatePick by rememberSaveable { mutableStateOf(false) }
    var endDatePick by remember { mutableStateOf(false) }

    val authToken = "Token ${userUiState.token}"
    var eventTitle by remember { mutableStateOf("") }
    var eventPlace by remember { mutableStateOf("") }
    var eventTime by remember { mutableStateOf("") }
    var eventContent by remember { mutableStateOf("") }
    var eventCategory by remember { mutableIntStateOf(1) } // 행사면 1
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val getImage =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { result ->
            result?.let { uri -> imageUri = uri }
        }
    var boardPostRequest: BoardPostRequest

    BackHandler(enabled = !exitConfirmDialog, onBack = { exitConfirmDialog = true })
    BackHandler(enabled = isPost, onBack = { /*prevent close loading bar and duplicated upload*/ })

    LaunchedEffect(Unit) { boardViewModel.resetBoardPostUiState() }

    // TODO SDK 버전 28 이상만 가능
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                })
            }
    ) {
        Column {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(color = Color(0x00F8F8F8)),
                value = eventTitle,
                onValueChange = { eventTitle = it },
                textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold),
                placeholder = {
                    Text(
                        text = "행사명을 입력해주세요.",
                        fontFamily = poppins,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = PlaceholderGrey,
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.moveFocus(FocusDirection.Down) }
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
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Row(
                                modifier = Modifier
                                    .clickable {
                                        if (imageUri !== null) imageUri = null
                                        else getImage.launch("image/*")
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = if (imageUri == null) "사진 첨부" else "사진 지우기",
                                    modifier = Modifier.padding(top = 4.dp),
                                    fontFamily = poppins,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Icon(
                                    modifier = Modifier.size(16.dp),
                                    imageVector = ImageVector.vectorResource(id = R.drawable.attach_icon),
                                    contentDescription = "attach icon",
                                    tint = Color.Black
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "주최",
                                modifier = Modifier.padding(top = 3.dp),
                                fontFamily = poppins,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            CustomVerticalDivider(height = 20, color = PlaceholderGrey)
                            customSingleLineTextField(
                                placeholder = userUiState.nickname,
                                enabled = false,
                                keyboardActions = {}
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
                                modifier = Modifier.padding(top = 3.dp),
                                fontFamily = poppins,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            CustomVerticalDivider(height = 20, color = PlaceholderGrey)
                            Spacer(modifier = Modifier.width(15.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = boardPostUiState.value.startDate.ifEmpty { "시작일 (필수)" },
                                    modifier = Modifier
                                        .border(
                                            width = 1.dp,
                                            color = HaengshaBlue,
                                            shape = RoundedCornerShape(6.dp)
                                        )
                                        .clickable { startDatePick = true }
                                        .padding(start = 8.dp, end = 8.dp, top = 3.dp),
                                    fontFamily = poppins,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = HaengshaBlue
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = "~",
                                    fontFamily = poppins,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = HaengshaBlue
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = boardPostUiState.value.endDate.ifEmpty { "종료일 (선택)" },
                                    modifier = Modifier
                                        .border(
                                            width = 1.dp,
                                            color = HaengshaBlue,
                                            shape = RoundedCornerShape(6.dp)
                                        )
                                        .clickable { endDatePick = true }
                                        .padding(start = 8.dp, end = 8.dp, top = 3.dp),
                                    fontFamily = poppins,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = HaengshaBlue
                                )
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "장소",
                                modifier = Modifier.padding(top = 3.dp),
                                fontFamily = poppins,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            CustomVerticalDivider(height = 20, color = PlaceholderGrey)
                            eventPlace = customSingleLineTextField(
                                placeholder = "행사 장소",
                                enabled = true,
                                keyboardActions = { focusManager.moveFocus(FocusDirection.Down) }
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
                                modifier = Modifier.padding(top = 3.dp),
                                fontFamily = poppins,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            CustomVerticalDivider(height = 20, color = PlaceholderGrey)
                            eventTime = customSingleLineTextField(
                                placeholder = "시작 시간 (~ 종료 시간)",
                                enabled = true,
                                keyboardActions = { focusManager.clearFocus() }
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
                                modifier = Modifier.padding(top = 3.dp),
                                fontFamily = poppins,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            CustomVerticalDivider(height = 20, color = PlaceholderGrey)
                            Spacer(modifier = Modifier.width(15.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .clickable { eventCategory = 1 }
                                            .testTag("festivalCheckBox")
                                    ) {
                                        CheckBox(
                                            color = if (eventCategory == 1) ButtonBlue else Color.Transparent,
                                            size = 18
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = "공연",
                                        modifier = Modifier.padding(top = 3.dp),
                                        fontFamily = poppins,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 16.sp
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(modifier = Modifier
                                        .clickable { eventCategory = 0 }
                                        .testTag("academicCheckBox")
                                    ) {
                                        CheckBox(
                                            color = if (eventCategory == 1) Color.Transparent else ButtonBlue,
                                            size = 18
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = "학술",
                                        modifier = Modifier.padding(top = 3.dp),
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
                    CustomHorizontalDivider(color = PlaceholderGrey)
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        imageUri?.let { uri ->
                            Spacer(modifier = Modifier.height(20.dp))
                            Image(
                                painter = rememberAsyncImagePainter(uri),
                                contentDescription = "image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(300.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "(사진은 1:1 비율로 잘리게 되며, 1장만 첨부할 수 있습니다)",
                                fontFamily = poppins,
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp,
                                fontStyle = FontStyle.Italic,
                                color = PlaceholderGrey
                            )
                        }
                    }
                    eventContent = customLargeTextField(
                        placeholder = "행사 정보를 입력해주세요",
                        height = deviceHeight / 2f
                    )
                }
            }
        }
        Box(modifier = Modifier.offset(deviceWidth - 80.dp, deviceHeight - 190.dp)) {
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
                    var errorMessage = checkPostFormat(
                        eventTitle = eventTitle,
                        eventStartDate = boardPostUiState.value.startDate,
                        eventEndDate = boardPostUiState.value.endDate,
                        eventPlace = eventPlace,
                        eventTime = eventTime,
                        eventContent = eventContent
                    )
                    if (errorMessage == "날짜 같음") {
                        boardViewModel.updatePostEndDate(boardPostUiState.value.startDate)
                        errorMessage = ""
                    }
                    if (errorMessage.isEmpty()) {
                        boardPostRequest = buildRequestBody(
                            token = authToken,
                            image = imageUri,
                            title = eventTitle,
                            isFestival = eventCategory,
                            durationStart = boardViewModel.boardPostUiState.value.startDate,
                            durationEnd = boardViewModel.boardPostUiState.value.endDate,
                            place = eventPlace,
                            time = eventTime,
                            content = eventContent,
                            postContext = postContext
                        )
                        isPost = true
                        boardApiViewModel.postEvent(boardPostRequest = boardPostRequest)
                    } else {
                        Toasty.warning(postContext, errorMessage, Toasty.LENGTH_SHORT).show()
                        postConfirmDialog = false
                    }
                },
                text = "글을 업로드 하시겠어요?"
            )
        }
    }

    when (boardPostApiUiState) {
        is BoardPostApiUiState.Success -> {
            postConfirmDialog = false
            Toasty.success(
                postContext,
                "글이 업로드 되었습니다.",
                Toasty.LENGTH_SHORT
            ).show()
            isPost = false
            boardApiViewModel.resetBoardPostApiUiState()
            boardNavController.popBackStack()
        }

        is BoardPostApiUiState.HttpError -> {
            Toasty.warning(
                postContext,
                "글 업로드에 실패했습니다.\n다시 시도해주세요.",
                Toasty.LENGTH_SHORT
            ).show()
            isPost = false
            boardApiViewModel.resetBoardPostApiUiState()
        }

        is BoardPostApiUiState.NetworkError -> {
            Toasty.error(
                postContext,
                "인터넷 연결을 확인해주세요",
                Toasty.LENGTH_SHORT,
                true
            ).show()
            isPost = false
            boardApiViewModel.resetBoardPostApiUiState()
        }

        is BoardPostApiUiState.Loading -> {
            if (isPost) {
                Dialog(onDismissRequest = { isPost = false }) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CustomCircularProgressIndicator()
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "업로드 하는 중...",
                            fontFamily = poppins,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = HaengshaBlue
                        )
                    }
                }
            }
        }

        is BoardPostApiUiState.Error -> {
            Toasty.error(
                postContext,
                "알 수 없는 문제가 발생했습니다.\n메일로 문의해주세요.",
                Toasty.LENGTH_SHORT,
                true
            ).show()
            isPost = false
            boardApiViewModel.resetBoardPostApiUiState()
        }
    }

    if (exitConfirmDialog) {
        ConfirmDialog(
            onDismissRequest = { exitConfirmDialog = false },
            onClick = {
                exitConfirmDialog = false
                boardNavController.popBackStack()
            },
            text = "현재 화면을 나가시겠어요?\n변경사항이 저장되지 않을 수 있습니다."
        )
    }

    if (startDatePick) {
        BoardDatePickerDialog(
            onDismissRequest = { startDatePick = false },
            boardViewModel = boardViewModel,
            type = "startDate",
            usage = "post"
        )
    }

    if (endDatePick) {
        BoardDatePickerDialog(
            onDismissRequest = { endDatePick = false },
            boardViewModel = boardViewModel,
            type = "endDate",
            usage = "post"
        )
    }
}

private fun buildRequestBody(
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

private fun checkPostFormat(
    eventTitle: String,
    eventStartDate: String,
    eventEndDate: String,
    eventPlace: String,
    eventTime: String,
    eventContent: String
): String {
    val possibleRegexPattern =
        "^[0-9a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣+×÷=/_<>\\[\\]!@#\$%^&*()\\-'\":;?`~\\\\|{}€£¥₩♤♡◇♧☆▪︎¤《》¡¿°•○●□■.,‽±』」〕】『「〔【№₽٪‰‐—–♠♥◆♣★\\s\\n]+$".toRegex()

    if (eventTitle.startsWith(" ") || eventTitle.endsWith(" ")) {
        return "제목의 앞뒤 공백을 제거해주세요."
    } else if (eventTitle.length !in 2..20) {
        return "제목은 2자 이상 40자 이하로 입력해주세요."
    } else if (!possibleRegexPattern.matches(eventTitle)) {
        return "제목은 한글, 영어, 숫자, 특수문자로만 입력해주세요.\n아직 이모지는 사용할 수 없습니다."
    }

    val startDate = if (eventStartDate.isEmpty()) {
        return "시작일을 입력해주세요"
    } else eventStartDate.split("-")
    val endDate = if (eventEndDate.isEmpty()) startDate else eventEndDate.split("-")
    if (startDate[0].toInt() > endDate[0].toInt()) {
        return "시작일이 종료일보다 늦습니다."
    } else if (startDate[0].toInt() == endDate[0].toInt()) {
        if (startDate[1].toInt() > endDate[1].toInt()) {
            return "시작일이 종료일보다 늦습니다."
        } else if (startDate[1].toInt() == endDate[1].toInt()) {
            if (startDate[2].toInt() > endDate[2].toInt()) {
                return "시작일이 종료일보다 늦습니다."
            }
        }
    }

    if (eventPlace.startsWith(" ") || eventPlace.endsWith(" ")) {
        return "장소의 앞뒤 공백을 제거해주세요."
    } else if (eventPlace.length !in 2..20) {
        return "장소는 2자 이상 20자 이하로 입력해주세요."
    } else if (!possibleRegexPattern.matches(eventPlace)) {
        return "장소는 한글, 영어, 숫자, 특수문자로만 입력해주세요."
    }

    if (eventTime.startsWith(" ") || eventTime.endsWith(" ")) {
        return "시간의 앞뒤 공백을 제거해주세요."
    } else if (eventTime.length !in 2..20) {
        return "시간은 2자 이상 20자 이하로 입력해주세요."
    } else if (!possibleRegexPattern.matches(eventTime)) {
        return "시간은 한글, 영어, 숫자, 특수문자로만 입력해주세요."
    }

    if (eventContent.trim().isEmpty()) {
        return "내용을 입력해주세요."
    } else if (eventContent.length !in 2..2000) {
        return "내용은 2자 이상 2000자 이하로 입력해주세요."
    } else if (!possibleRegexPattern.matches(eventContent)) {
        return "내용은 한글, 영어, 숫자, 특수문자로만 입력해주세요.\n아직 이모지는 사용할 수 없습니다."
    }

    return if (endDate == startDate) "날짜 같음" else ""
}