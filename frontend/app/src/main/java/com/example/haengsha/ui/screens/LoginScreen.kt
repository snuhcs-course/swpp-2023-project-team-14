package com.example.haengsha.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.haengsha.Routes
import com.example.haengsha.ui.theme.ButtonBlue
import com.example.haengsha.ui.theme.FieldStrokeBlue
import com.example.haengsha.ui.theme.poppins
import com.example.haengsha.ui.uiComponents.CommonBlueButton
import com.example.haengsha.ui.uiComponents.MessageDialog
import com.example.haengsha.ui.uiComponents.passwordTextField
import com.example.haengsha.ui.uiComponents.suffixTextField
import es.dmoral.toasty.Toasty

@Composable
fun LoginScreen(navController: NavHostController) {
    val context = LocalContext.current;
    var emailInput: String by rememberSaveable { mutableStateOf("") }
    var passwordInput: String by rememberSaveable { mutableStateOf("") }
    var isEmailError by rememberSaveable { mutableStateOf(false) }
    var isPasswordError by rememberSaveable { mutableStateOf(false) }
    var isLoginFailedDialogVisible by rememberSaveable { mutableStateOf(false) }
    fun showLoginFailedDialog() {
        isLoginFailedDialogVisible = true
    }

    fun hideLoginFailedDialog() {
        isLoginFailedDialogVisible = false
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 90.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(1) {
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
            Spacer(modifier = Modifier.height(45.dp))
            Text(
                modifier = Modifier.width(270.dp),
                text = "SNU Email을 입력하세요.",
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            emailInput = suffixTextField(
                isError = isEmailError,
                placeholder = "SNU Email",
                suffix = "@snu.ac.kr"
            )
            Spacer(modifier = Modifier.height(10.dp))

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.width(270.dp),
                text = "비밀번호를 입력하세요.",
                fontFamily = poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            passwordInput = passwordTextField( //TODO : 불필요한 로직 없애기
                isEmptyError = isPasswordError,
                placeholder = "비밀번호",
                context = context
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .width(270.dp)
                    .height(20.dp)
                    .clickable { navController.navigate(Routes.FindPassword.route) }
            ) {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = "아이디/비밀번호 찾기",
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 11.sp,
                    textAlign = TextAlign.End,
                    color = FieldStrokeBlue
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
            CommonBlueButton(text = "로그인하기",
                onClick = {
                    if (emailInput.trimStart() == "") {
                        isEmailError = true
                        Toasty.error(
                            context,
                            "이메일을 입력해주세요",
                            Toast.LENGTH_SHORT,
                            true
                        ).show()
                    } else if (passwordInput.trimStart() == "") {
                        isPasswordError = true
                        Toasty.error(
                            context,
                            "비밀번호를 입력해주세요",
                            Toast.LENGTH_SHORT,
                            true
                        ).show()
                    } else {
                        /* TODO 로그인 로직*/
                        showLoginFailedDialog()
                        //navController.navigate(Routes.Home.route)
                    }
                })
            if (isLoginFailedDialogVisible) {
                MessageDialog(
                    onDismissRequest = { hideLoginFailedDialog() },
                    onClick = { hideLoginFailedDialog() },
                    text = "로그인 정보를 확인해주세요."
                )
            }
            Spacer(modifier = Modifier.height(45.dp))
            Box(
                modifier = Modifier
                    .width(270.dp)
                    .height(20.dp)
                    .clickable { navController.navigate(Routes.SignUp.route) }
            ) {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = AnnotatedString("회원가입하기"),
                    fontFamily = poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    textDecoration = TextDecoration.Underline,
                    color = ButtonBlue
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(rememberNavController())
}