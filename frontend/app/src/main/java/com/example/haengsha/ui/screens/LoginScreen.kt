package com.example.haengsha.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.haengsha.Routes
import com.example.haengsha.ui.theme.ButtonBlue
import com.example.haengsha.ui.theme.poppins

@Composable
fun LoginScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp)
        ) {
            Text(
                text = "No Account?",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = poppins,
                    fontWeight = FontWeight.Light
                ),
            )
            ClickableText(
                text = AnnotatedString("회원가입하기"),
                onClick = { navController.navigate(Routes.SignUp.route) },
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = poppins,
                    textDecoration = TextDecoration.Underline,
                    color = ButtonBlue
                )
            )
        }
    }
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val userEmail = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }
        val shapeRoundCornerUnified = RoundedCornerShape(20)
        val stylePlaceHolders =
            TextStyle(fontSize = 13.sp, fontFamily = poppins, fontWeight = FontWeight.Light)
        val colorPlaceHolder = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            cursorColor = Color.Blue,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedLabelColor = Color.Black,
            unfocusedPlaceholderColor = Color.Transparent,
        )

        Spacer(modifier = Modifier.height(60.dp))
        Text(text = "Welcome to", style = TextStyle(fontSize = 24.sp, fontFamily = poppins))
        Text(
            text = "행샤",
            style = TextStyle(
                fontSize = 30.sp,
                fontFamily = poppins,
                fontWeight = FontWeight.Bold,
                color = ButtonBlue
            )
        )
        Spacer(modifier = Modifier.height(30.dp))

        Text(text = "SNU Email을 입력하세요.", style = TextStyle(fontSize = 14.sp, fontFamily = poppins))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .border(
                    BorderStroke(width = 0.1.dp, color = Color.Black),
                    shape = shapeRoundCornerUnified)
                .width(300.dp)
        ) {
            TextField(
                label = { Text(text = "SNU Email", style = stylePlaceHolders) },
                colors = colorPlaceHolder,
                value = userEmail.value,
                onValueChange = { userEmail.value = it },
                modifier = Modifier.weight(1f)

            )
            Text(
                text = "@snu.ac.kr",
                style = stylePlaceHolders,
                modifier = Modifier.padding(end = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "비밀번호를 입력하세요.", style = TextStyle(fontSize = 14.sp, fontFamily = poppins))

        TextField(
            label = { Text(text = "Password", style = stylePlaceHolders) },
            colors = colorPlaceHolder,
            modifier = Modifier.border(
                BorderStroke(width = 0.1.dp, color = Color.Black),
                shape = shapeRoundCornerUnified,)
                .width(300.dp),
            value = password.value,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { password.value = it })

        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = { navController.navigate(Routes.Home.route) },
                shape = shapeRoundCornerUnified,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(ButtonBlue),
            ) {
                Text(
                    text = "로그인하기",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = poppins,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        ClickableText(
            text = AnnotatedString("아이디/비밀번호 찾기"),
            onClick = { navController.navigate(Routes.ForgotPassword.route) },
            style = TextStyle(fontSize = 11.sp, fontFamily = poppins, color = ButtonBlue)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(rememberNavController())
}