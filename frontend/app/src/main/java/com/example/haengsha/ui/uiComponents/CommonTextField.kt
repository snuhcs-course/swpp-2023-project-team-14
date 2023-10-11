package com.example.haengsha.ui.uiComponents

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.haengsha.R
import com.example.haengsha.ui.theme.poppins
import es.dmoral.toasty.Toasty

@Composable
fun commonTextField(
    isError: Boolean = false,
    placeholder: String = ""
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
                color = colorResource(id = R.color.PlaceholderGrey),
            )
        },
        isError = isError,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(id = R.color.FieldStrokeBlue),
            unfocusedBorderColor = Color(0xFFADADAD),
            errorBorderColor = colorResource(id = R.color.FieldStrokeRed)
        )
    )
    return input
}

@Composable
fun suffixTextField(
    isError: Boolean = false,
    placeholder: String = "",
    suffix: String = ""
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
                color = colorResource(id = R.color.PlaceholderGrey),
            )
        },
        suffix = {
            Text(
                text = suffix,
                fontFamily = poppins,
                fontSize = 13.sp,
                fontWeight = FontWeight.Light,
                fontStyle = FontStyle.Italic,
                color = colorResource(id = R.color.PlaceholderGrey),
            )
        },
        isError = isError,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(id = R.color.FieldStrokeBlue),
            unfocusedBorderColor = Color(0xFFADADAD),
            errorBorderColor = colorResource(id = R.color.FieldStrokeRed)
        )
    )
    return input
}

@Composable
fun passwordTextField(
    isEmptyError: Boolean = false,
    placeholder: String = "",
    context: Context
): String {
    var input by rememberSaveable { mutableStateOf("") }
    val textField = FocusRequester()
    val pattern = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{4,10}$".toRegex()
    var isError by rememberSaveable { mutableStateOf(false) }
    var isRegexError by rememberSaveable { mutableStateOf(false) }

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
                color = colorResource(id = R.color.PlaceholderGrey),
            )
        },
        isError = isError,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
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
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(id = R.color.FieldStrokeBlue),
            unfocusedBorderColor = Color(0xFFADADAD),
            errorBorderColor = colorResource(id = R.color.FieldStrokeRed)
        )
    )
    return input
}

@Composable
fun passwordCheckTextField(
    isError: Boolean = false,
    placeholder: String = ""
): String {
    var input by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .size(width = 270.dp, height = 60.dp),
        value = input,
        onValueChange = { input = it },
        placeholder = {
            Text(
                text = placeholder,
                fontFamily = poppins,
                fontSize = 13.sp,
                fontWeight = FontWeight.Light,
                color = colorResource(id = R.color.PlaceholderGrey),
            )
        },
        isError = isError,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(id = R.color.FieldStrokeBlue),
            unfocusedBorderColor = Color(0xFFADADAD),
            errorBorderColor = colorResource(id = R.color.FieldStrokeRed)
        )
    )
    return input
}

@Preview(showBackground = true)
@Composable
fun CommonTextFieldPreview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        commonTextField()
    }
}