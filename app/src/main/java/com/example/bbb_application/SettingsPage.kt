package com.example.bbb_application

import android.annotation.SuppressLint
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(navController: NavHostController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Settings") }) },
        content = {
            Text("Settings Page", style = MaterialTheme.typography.headlineMedium)
        }
    )
}
