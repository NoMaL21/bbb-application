package com.example.bbb_application.ui.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bbb_application.viewmodel.LoginViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(navController: NavHostController, loginViewModel: LoginViewModel) {
    val loggedInUser by loginViewModel.loggedInUser

    Scaffold(
        topBar = { TopAppBar(title = { Text("Settings") }) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // 내 정보 버튼
                Button(
                    onClick = { navController.navigate("mypage") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("내 정보")
                }

                HorizontalDivider(thickness = 0.5.dp)

                // admin일 때만 보이는 버튼
                if (loggedInUser == "test") {
                    Button(
                        onClick = { navController.navigate("admin") },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Admin Page")
                    }
                }

                Button(
                    onClick = {
                        loginViewModel.logout()
                        navController.navigate("login")
                              },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("로그아웃")
                }

                HorizontalDivider(thickness = 0.5.dp)
            }
        }
    )
}
