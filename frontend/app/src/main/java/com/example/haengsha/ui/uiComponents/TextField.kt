package com.example.haengsha.ui.uiComponents

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.haengsha.model.network.dataModel.SearchRequest
import com.example.haengsha.ui.theme.FieldStrokeBlue
import com.example.haengsha.ui.theme.FieldStrokeRed
import com.example.haengsha.ui.theme.HaengshaGrey
import com.example.haengsha.ui.theme.PlaceholderGrey
import com.example.haengsha.ui.theme.poppins
import es.dmoral.toasty.Toasty

@Composable
fun commonTextField(
    isError: Boolean,
    placeholder: String
): String {
    var input by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier.size(width = 270.dp, height = 60.dp),
        value = input,
        onValueChange = { input = it },
        placeholder = {
            Text(
                text = placeholder,
                fontFamily = poppins,
                fontSize = 13.sp,
                fontWeight = FontWeight.Light,
                color = PlaceholderGrey,
            )
        },
        isError = isError,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = FieldStrokeBlue,
            unfocusedBorderColor = HaengshaGrey,
            errorBorderColor = FieldStrokeRed
        )
    )
    return input
}

@Composable
fun codeVerifyField(
    isError: Boolean,
    placeholder: String
): String {
    var input by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier.size(width = 270.dp, height = 60.dp),
        value = input,
        onValueChange = { if (it.length <= 6) input = it },
        placeholder = {
            Text(
                text = placeholder,
                fontFamily = poppins,
                fontSize = 13.sp,
                fontWeight = FontWeight.Light,
                color = PlaceholderGrey,
            )
        },
        isError = isError,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = FieldStrokeBlue,
            unfocusedBorderColor = HaengshaGrey,
            errorBorderColor = FieldStrokeRed
        )
    )
    return input
}

@Composable
fun suffixTextField(
    isEmptyError: Boolean,
    placeholder: String,
    suffix: String
): String {
    var input by rememberSaveable { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    isError = if (isEmptyError) {
        input.trimStart() == ""
    } else false

    OutlinedTextField(
        modifier = Modifier.size(width = 270.dp, height = 60.dp),
        value = input,
        onValueChange = { input = it },
        placeholder = {
            Text(
                text = placeholder,
                fontFamily = poppins,
                fontSize = 13.sp,
                fontWeight = FontWeight.Light,
                color = PlaceholderGrey,
            )
        },
        suffix = {
            Text(
                text = suffix,
                fontFamily = poppins,
                fontSize = 13.sp,
                fontWeight = FontWeight.Light,
                fontStyle = FontStyle.Italic,
                color = PlaceholderGrey,
            )
        },
        isError = isError,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = FieldStrokeBlue,
            unfocusedBorderColor = HaengshaGrey,
            errorBorderColor = FieldStrokeRed
        )
    )
    return input
}

@Composable
fun passwordTextField(
    isEmptyError: Boolean,
    placeholder: String = "",
): String {
    var input by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    isError = if (isEmptyError) {
        input.trimStart() == ""
    } else false

    OutlinedTextField(
        modifier = Modifier.size(width = 270.dp, height = 60.dp),
        value = input,
        onValueChange = { input = it },
        placeholder = {
            Text(
                text = placeholder,
                fontFamily = poppins,
                fontSize = 13.sp,
                fontWeight = FontWeight.Light,
                color = PlaceholderGrey
            )
        },
        isError = isError,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = FieldStrokeBlue,
            unfocusedBorderColor = HaengshaGrey,
            errorBorderColor = FieldStrokeRed
        )
    )
    return input
}

@Composable
fun passwordSetField(
    isEmptyError: Boolean,
    placeholder: String = "",
    context: Context
): String {
    var input by remember { mutableStateOf("") }
    val textField = FocusRequester()
    val pattern = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{4,10}$".toRegex()
    var isError by remember { mutableStateOf(false) }
    var isRegexError by remember { mutableStateOf(false) }

    isError = if (isEmptyError) { // 비밀번호 입력 안 하고 다음 버튼 눌러서 에러 난 후, 비밀번호를 올바르게 입력하는 경우 핸들링
        if (input.trimStart() == "") true
        else isRegexError
    } else isRegexError

    OutlinedTextField(
        modifier = Modifier
            .size(width = 270.dp, height = 60.dp)
            .focusRequester(textField),
        value = input,
        onValueChange = {
            input = it
            if (pattern.matches(input)) {
                isRegexError = false
                textField.freeFocus()
            } else {
                isRegexError = true
                textField.captureFocus()
            }
        },
        placeholder = {
            Text(
                text = placeholder,
                fontFamily = poppins,
                fontSize = 13.sp,
                fontWeight = FontWeight.Light,
                color = PlaceholderGrey
            )
        },
        isError = isError,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                if (isRegexError) {
                    Toasty.error(context, "비밀번호 형식이 맞지 않습니다", Toast.LENGTH_SHORT, true)
                        .show()
                } else defaultKeyboardAction(ImeAction.Done)
            }
        ),
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = FieldStrokeBlue,
            unfocusedBorderColor = HaengshaGrey,
            errorBorderColor = FieldStrokeRed
        )
    )
    return input
}

@Composable
fun passwordCheckTextField(
    isError: Boolean = false,
    placeholder: String = ""
): String {
    var input by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier.size(width = 270.dp, height = 60.dp),
        value = input,
        onValueChange = { input = it },
        placeholder = {
            Text(
                text = placeholder,
                fontFamily = poppins,
                fontSize = 13.sp,
                fontWeight = FontWeight.Light,
                color = PlaceholderGrey,
            )
        },
        isError = isError,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = FieldStrokeBlue,
            unfocusedBorderColor = HaengshaGrey,
            errorBorderColor = FieldStrokeRed
        )
    )
    return input
}

@Composable
fun SearchBar(
    onSubmit: (SearchRequest) -> Unit,
    userToken: String,
    isFestival: Int,
    startDate: String,
    endDate: String
) {
    var input by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    val searchRequest =
        SearchRequest("Token $userToken", input, isFestival, startDate, endDate)

    OutlinedTextField(
        modifier = Modifier
            .size(width = 340.dp, height = 60.dp),
        value = input,
        onValueChange = { input = it },
        placeholder = {
            Text(
                text = "Search",
                fontFamily = poppins,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = PlaceholderGrey,
            )
        },
        leadingIcon = {
            Icon(
                Icons.Rounded.Search,
                contentDescription = "search icon"
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSubmit(searchRequest)
                keyboardController?.hide()
            }
        ),
        singleLine = true,
        shape = RoundedCornerShape(30.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = FieldStrokeBlue,
            unfocusedBorderColor = HaengshaGrey,
        )
    )
}

@Composable
fun commentTextField(): String {
    var input by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier.size(width = 280.dp, height = 60.dp),
        value = input,
        onValueChange = { input = it },
        placeholder = {
            Text(
                text = "반응을 남겨보세요",
                fontFamily = poppins,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = PlaceholderGrey,
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        shape = RoundedCornerShape(20.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color(0x00F8F8F8)
        )
    )
    return input
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun customTextField(placeholder: String, enabled: Boolean): String {
    var input by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = input,
        onValueChange = { input = it },
        modifier = Modifier
            .indicatorLine(
                enabled = false,
                isError = false,
                interactionSource = interactionSource,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                )
            )
            .fillMaxWidth()
            .height(40.dp)
            .background(color = Color(0x00F8F8F8)),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 16.sp,
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
        ),
        decorationBox = { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = input,
                innerTextField = innerTextField,
                enabled = enabled,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                    top = 0.dp,
                    bottom = 0.dp
                ),
                placeholder = {
                    Text(
                        text = placeholder,
                        color = if (enabled) HaengshaGrey else Color.Black,
                        fontSize = 16.sp,
                        fontFamily = poppins,
                        fontWeight = FontWeight.Normal,
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color(0x00F8F8F8),
                    unfocusedContainerColor = Color(0x00F8F8F8),
                    disabledContainerColor = Color(0x00F8F8F8)
                )
            )
        }
    )
    return input
}

@Preview(showBackground = true)
@Composable
fun TextFieldPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        customTextField("자하연 앞", true)
    }
}