package com.example.bbb_application.ui.pages

import android.app.DatePickerDialog
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.bbb_application.viewmodel.TaskViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bbb_application.viewmodel.LoginViewModel
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TaskPage(navController: NavHostController, loginViewModel: LoginViewModel = viewModel()) {
    val textState = remember { mutableStateOf(TextFieldValue()) }
    val selectedDate = remember { mutableStateOf("날짜를 선택해주세요") }
    val selectedTask = remember { mutableStateOf("작업을 선택해주세요") }
    val taskViewModel: TaskViewModel = viewModel()
    val loggedInUser by loginViewModel.loggedInUser

    // collectAsState로 상태 구독
    val taskAddStatus by taskViewModel.taskAddStatus.collectAsState()

    var showDatePickerDialog by remember { mutableStateOf(false) }

    // DatePickerDialog가 보여지는지 여부
    if (showDatePickerDialog) {
        DatePickerDialogComponent(
            selectedDate = selectedDate, // selectedDate를 매개변수로 전달
            onDateSelected = { year, month, day ->
                showDatePickerDialog = false
            },
            onDismissRequest = { showDatePickerDialog = false }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task Management") },
                modifier = Modifier.padding(top = 24.dp)
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, top = paddingValues.calculateTopPadding())
            ) {
                // 날짜 선택 버튼
                Button(
                    onClick = {
                        showDatePickerDialog = true // 날짜 선택 버튼 클릭 시 다이얼로그 열기
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                ) {
                    Text(selectedDate.value)  // 선택된 날짜를 버튼에 표시
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 작업 상태 선택 버튼
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatusPanel(
                        statusText = "작업 중",
                        isSelected = selectedTask.value == "작업 중",
                        onClick = {
                            selectedTask.value = "작업 중"
                            println("${loggedInUser}, ${selectedTask.value}, ${selectedDate.value}" )
                            if (!loggedInUser.isNullOrEmpty()) {
                                // 작업을 저장하는 로직 구현
                                //taskViewModel.addTask(loggedInUser!!, selectedTask.value, selectedDate.value, "15:30:00")
                            } else {
                                println("사용자가 로그인하지 않았습니다.")
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )

                    StatusPanel(
                        statusText = "외출(휴식)",
                        isSelected = selectedTask.value == "외출(휴식)",
                        onClick = {
                            selectedTask.value = "외출(휴식)"
                            if (!loggedInUser.isNullOrEmpty()) {
                                // 작업을 저장하는 로직 구현
                                //taskViewModel.addTask(loggedInUser!!, selectedTask.value, selectedDate.value, "15:30:00")

                            } else {
                                println("사용자가 로그인하지 않았습니다.")
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatusPanel(
                        statusText = "출장",
                        isSelected = selectedTask.value == "출장",
                        onClick = {
                            selectedTask.value = "출장"
                            if (!loggedInUser.isNullOrEmpty()) {
                                // 작업을 저장하는 로직 구현
                                //taskViewModel.addTask(loggedInUser!!, selectedTask.value, selectedDate.value, "15:30:00")

                            } else {
                                println("사용자가 로그인하지 않았습니다.")
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )

                    StatusPanel(
                        statusText = "휴가",
                        isSelected = selectedTask.value == "휴가",
                        onClick = {
                            selectedTask.value = "휴가"
                            if (!loggedInUser.isNullOrEmpty()) {
                                // 작업을 저장하는 로직 구현
                                //taskViewModel.addTask(loggedInUser!!, selectedTask.value, selectedDate.value, "15:30:00")

                            } else {
                                println("사용자가 로그인하지 않았습니다.")
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 작업 추가 버튼
                Button(
                    onClick = {

                        val taskStatus = selectedTask.value
                        val taskDate = selectedDate.value

                        val taskTime = "15:30:00" // 예시 시간

                        // 작업을 DB에 저장
                        taskViewModel.addTask(loggedInUser!!, taskStatus, taskDate, taskTime)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                ) {
                    Text("작업 추가", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 작업 추가 상태 표시
                if (!taskAddStatus.isNullOrEmpty()) {
                    Text(
                        text = taskAddStatus ?: "",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    )
}

@Composable
fun StatusPanel(
    statusText: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier // modifier 파라미터 추가
) {
    Card(
        modifier = modifier // modifier 전달
            .height(100.dp)
            .padding(8.dp)
            .clickable(onClick = onClick),  // 클릭 이벤트를 Modifier에 추가
        shape = RoundedCornerShape(12.dp),
        elevation = if (isSelected) CardDefaults.cardElevation(8.dp) else CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color.White else Color.LightGray
        ),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = statusText,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isSelected) Color.Black else Color.White
            )
        }
    }
}


// 작업 저장 함수
@Composable
fun DatePickerDialogComponent(
    selectedDate: MutableState<String>, // selectedDate를 매개변수로 추가
    onDateSelected: (Int, Int, Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val context = LocalContext.current // **여기서 호출**
    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            // 선택된 날짜를 'yyyy-MM-dd' 형식으로 설정
            selectedDate.value = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
            onDateSelected(selectedYear, selectedMonth, selectedDay)
        },
        year, month, day
    )

    LaunchedEffect(context) {
        datePickerDialog.show() // DatePickerDialog를 Composable 환경 안에서 보여줍니다.
    }

    DisposableEffect(Unit) {
        onDismissRequest
        onDispose {}
    }
}