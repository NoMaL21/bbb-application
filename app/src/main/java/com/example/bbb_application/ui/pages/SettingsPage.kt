package com.example.bbb_application.ui.pages

import android.annotation.SuppressLint
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.example.bbb_application.viewmodel.LoginViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(navController: NavHostController, loginViewModel: LoginViewModel) {
    val loggedInUser by loginViewModel.loggedInUser

    Scaffold(
        topBar = { TopAppBar(title = { Text("Settings") }) },
        content = {
            Text("Settings Page", style = MaterialTheme.typography.headlineMedium)
        }
    )
}
