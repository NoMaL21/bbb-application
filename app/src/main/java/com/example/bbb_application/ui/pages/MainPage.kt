package com.example.bbb_application.ui.pages

import com.example.bbb_application.viewmodel.LoginViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MainPage(navController: NavHostController, loginViewModel: LoginViewModel) {
    val loggedInUser by loginViewModel.loggedInUser // 로그인 상태 가져오기

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .wrapContentSize(Alignment.Center), // 중앙 정렬
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 로그인한 유저 이름을 강조
        Text(
            text = "Welcome, $loggedInUser!",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary

        )
    }
}
