package com.example.bbb_application.ui.pages
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun fetchTasksForMember(username: String?): List<Map<String, String>> {
    return listOf(
        mapOf("status_date" to "commitTest", "current_status" to "Working "),
        mapOf("status_date" to "2024-01-23", "current_status" to "캠페인 보고 작성 중"),
        mapOf("status_date" to "2024-01-30", "current_status" to "회의 준비")
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun MemberPage(username: String?) {

    val tasks = remember { mutableStateOf(emptyList<Map<String, String>>()) }

    // 더미 데이터를 가져오는 로직
    LaunchedEffect(username) {
        tasks.value = fetchTasksForMember(username)
        println("Loaded tasks: ${tasks.value}") // 첫 번째 값 확인
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("$username 의 일정들") }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                if (tasks.value.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "$username 은 일정이 없습니다.", style = MaterialTheme.typography.bodyLarge)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        item {
                            // Header Row
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "일자",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "할일",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.weight(2f)
                                )
                            }
                            HorizontalDivider(thickness = 0.5.dp)
                        }
                        items(tasks.value) { task ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = task["status_date"] ?: "",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = task["current_status"] ?: "",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.weight(2f)
                                )
                            }
                            HorizontalDivider(thickness = 0.5.dp)
                        }
                    }
                }
            }
        }
    )
}