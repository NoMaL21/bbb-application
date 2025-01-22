package com.example.bbb_application.ui.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarPage(navController: NavHostController) {
    val currentMonth = YearMonth.now()
    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfMonth = currentMonth.atDay(1).dayOfWeek.value % 7
    val today = LocalDate.now()
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calendar") },
                modifier = Modifier.background(MaterialTheme.colorScheme.primary)
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Calendar grid
                CalendarGrid(
                    daysInMonth = daysInMonth,
                    firstDayOfMonth = firstDayOfMonth,
                    today = today,
                    onDateClick = { date ->
                        selectedDate = date
                    }
                )

                // Show dialog for selected date
                if (selectedDate != null) {
                    val dateToNavigate = selectedDate // 로컬 변수로 값 복사
                    DetailDialog(
                        date = dateToNavigate!!,
                        onDismiss = { selectedDate = null },
                        onMoreDetails = {
                            navController.navigate("details/${dateToNavigate}") // 안전하게 전달
                            selectedDate = null // 값 초기화
                        }
                    )
                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarGrid(
    daysInMonth: Int,
    firstDayOfMonth: Int,
    today: LocalDate,
    onDateClick: (LocalDate) -> Unit
) {
    Column {
        val currentMonth = YearMonth.now()
        val rows = (daysInMonth + firstDayOfMonth) / 7 + 1

        // Weekday headers
        Row(modifier = Modifier.fillMaxWidth()) {
            listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Days in month
        for (row in 0 until rows) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (col in 0..6) {
                    val day = row * 7 + col - firstDayOfMonth + 1
                    if (day in 1..daysInMonth) {
                        val date = currentMonth.atDay(day)
                        val isToday = date == today
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(4.dp)
                                .clickable { onDateClick(date) }
                                .background(
                                    if (isToday) MaterialTheme.colorScheme.primary else Color.Transparent,
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = day.toString(),
                                color = if (isToday) Color.White else Color.Black,
                                fontSize = 16.sp
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailDialog(
    date: LocalDate,
    onDismiss: () -> Unit,
    onMoreDetails: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Details for ${date.format(DateTimeFormatter.ofPattern("MMM d, yyyy"))}") },
        text = { Text("You can view or edit details for this date.") },
        confirmButton = {
            TextButton(onClick = { onMoreDetails() }) {
                Text("More Details")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Close")
            }
        }
    )
}
