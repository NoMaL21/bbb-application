package com.example.bbb_application.ui.pages

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
fun SignUpPage(navController: NavHostController) {
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue("")) }
    var department by remember { mutableStateOf(TextFieldValue("")) }
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Sign Up", style = MaterialTheme.typography.headlineMedium)

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

        Spacer(modifier = Modifier.height(16.dp))

        // Confirm Password Field
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = department,
            onValueChange = { department = it },
            label = { Text("department") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("name") },
            modifier = Modifier.fillMaxWidth()
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

        // Sign Up Button
        Button(
            onClick = {
                if (password.text != confirmPassword.text) {
                    errorMessage = "Passwords do not match"
                    return@Button
                }

                isLoading = true

                ApiService.signUp(
                    username = username.text,
                    password = password.text,
                    department = department.text,
                    name = name.text
                ) { success ->
                    isLoading = false
                    if (success) {
                        navController.navigate("login")
                    } else {
                        errorMessage = "Failed to sign up. Please try again."
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
            Text(if (isLoading) "Signing Up..." else "Sign Up")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 이미 계정이 있다면 로그인 페이지로 이동
        TextButton(
            onClick = {
                navController.navigate("login") // 로그인 페이지로 이동
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Already have an account? Log In", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
