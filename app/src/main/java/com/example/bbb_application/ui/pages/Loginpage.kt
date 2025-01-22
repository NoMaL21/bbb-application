package com.example.bbb_application.ui.pages

import com.example.bbb_application.viewmodel.LoginViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bbb_application.api.ApiService

@Composable
fun LoginPage(navController: NavHostController, loginViewModel: LoginViewModel) {
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val loggedInUser by loginViewModel.loggedInUser // ViewModel에서 로그인 상태를 가져옴

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        // Username Field
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Error Message
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Login Button
        Button(
            onClick = {
                if (username.text.isEmpty() || password.text.isEmpty()) {
                    errorMessage = "Username and password cannot be empty."
                    return@Button
                }

                isLoading = true
                ApiService.login(username.text, password.text) { message ->
                    isLoading = false
                    if (message != null && message.contains("로그인 성공")) {
                        loginViewModel.setLoggedInUser(username.text)
                        navController.navigate("main") // 로그인 성공 시 main 페이지로 이동
                    } else {
                        errorMessage = message ?: "Unknown error occurred."
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .then(if (isLoading) Modifier.alpha(0.5f) else Modifier) // 로딩 중에는 버튼 비활성화
                .pointerInput(Unit) {
                    // isLoading이 true일 때 클릭을 무시
                    if (isLoading) {
                        awaitPointerEventScope {
                            awaitPointerEvent(PointerEventPass.Initial)
                        }
                    }
                }
        ) {
            Text(if (isLoading) "Logging in..." else "Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sign Up Button
        TextButton(
            onClick = {
                navController.navigate("signup") // 회원가입 페이지로 이동
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Don't have an account? Sign Up", style = MaterialTheme.typography.bodyMedium)
        }
    }
}


