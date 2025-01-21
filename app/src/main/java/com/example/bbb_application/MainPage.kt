package com.example.bbb_application

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Main Page") })
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Welcome to Main Page!", style = MaterialTheme.typography.headlineMedium)

                    Spacer(modifier = Modifier.height(24.dp))

                    // Button to navigate to CalendarPage
                    Button(onClick = { navController.navigate("calendar") }) {
                        Text("Go to Calendar")
                    }
                }
            }
        }
    )
}
