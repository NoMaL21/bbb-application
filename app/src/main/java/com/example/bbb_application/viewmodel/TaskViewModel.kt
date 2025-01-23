package com.example.bbb_application.viewmodel

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bbb_application.api.ApiService
import com.example.bbb_application.api.Task
import com.example.bbb_application.api.TaskRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale

class TaskViewModel : ViewModel() {
    // 작업 추가 상태 메세지
    private val _taskAddStatus = MutableStateFlow<String?>(null)
    val taskAddStatus: StateFlow<String?> = _taskAddStatus

    // 작업 목록 상태
    private val _taskList = MutableStateFlow<List<Task>>(emptyList())
    val taskList: StateFlow<List<Task>> = _taskList

    // 작업 추가 요청 함수
    @RequiresApi(Build.VERSION_CODES.N)
    fun addTask(name: String, status: String, date: String, time: String) {
        try {
            // 날짜를 String에서 Date로 변환
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val parsedDate = dateFormat.parse(date) ?: throw IllegalArgumentException("Invalid date format")
            val formattedDate = dateFormat.format(parsedDate)

            // 시간을 String으로 포맷
            val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            val parsedTime = timeFormat.parse(time) ?: throw IllegalArgumentException("Invalid time format")
            val formattedTime = timeFormat.format(parsedTime)

            val taskRequest = TaskRequest(name, status, formattedDate, formattedTime)

            // API 호출을 viewModelScope 내에서 launch로 감싸기
            viewModelScope.launch {
                try {
                    val response = ApiService.taskApi.addTask(taskRequest)

                    if (response.isSuccessful) {
                        _taskAddStatus.value = "작업이 추가되었습니다."
                        // 작업 추가 후 작업 목록 갱신
                        getTasks()
                    } else {
                        _taskAddStatus.value = "작업 추가 실패: ${response.message()}"
                    }
                } catch (e: Exception) {
                    _taskAddStatus.value = "네트워크 오류: ${e.message}"
                }
            }
        } catch (e: Exception) {
            _taskAddStatus.value = "날짜 또는 시간 형식 오류: ${e.message}"
        }
    }

    // 작업 목록 조회 함수
    fun getTasks() {
        viewModelScope.launch {
            try {
                val response = ApiService.taskApi.getTasks()
                if (response.isSuccessful) {
                    _taskList.value = response.body() ?: emptyList()
                } else {
                    _taskAddStatus.value = "작업 목록 가져오기 실패: ${response.message()}"
                }
            } catch (e: Exception) {
                _taskAddStatus.value = "네트워크 오류: ${e.message}"
            }
        }
    }

    // 작업 삭제 함수
    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            try {
                val response = ApiService.deleteTask(taskId)
                if (response.isSuccessful) {
                    _taskAddStatus.value = "작업이 삭제되었습니다."
                    // 작업 삭제 후 작업 목록 갱신
                    getTasks()
                } else {
                    _taskAddStatus.value = "작업 삭제 실패: ${response.message()}"
                }
            } catch (e: Exception) {
                _taskAddStatus.value = "네트워크 오류: ${e.message}"
            }
        }
    }

    // 작업 수정 함수
    fun updateTask(taskId: Int, name: String, status: String, date: String, time: String) {
        val taskRequest = TaskRequest(name, status, date, time)

        viewModelScope.launch {
            try {
                val response = ApiService.updateTask(taskId, taskRequest)
                if (response.isSuccessful) {
                    _taskAddStatus.value = "작업이 수정되었습니다."
                    // 작업 수정 후 작업 목록 갱신
                    getTasks()
                } else {
                    _taskAddStatus.value = "작업 수정 실패: ${response.message()}"
                }
            } catch (e: Exception) {
                _taskAddStatus.value = "네트워크 오류: ${e.message}"
            }
        }
    }
}
