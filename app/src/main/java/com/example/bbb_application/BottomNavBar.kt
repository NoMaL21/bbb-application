package com.example.bbb_application

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavBar(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navController.navigate("calendar") }) {
                    Text("C", color = Color.Black)
                }
                IconButton(onClick = { navController.navigate("team") }) {
                    Text("P", color = Color.Black)
                }
                IconButton(onClick = { navController.navigate("task") }) {
                    Text("T", color = Color.Black)
                }
                IconButton(onClick = { navController.navigate("settings") }) {
                    Text("S", color = Color.Black)
                }
            }
        }
    ) {
        // Content here will depend on the selected tab
    }
}

@Composable
fun PreviewCustomBottomNavBar() {
    BottomNavBar(navController = rememberNavController())
}
