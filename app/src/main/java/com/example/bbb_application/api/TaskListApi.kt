package com.example.bbb_application.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// 로그인 요청을 위한 데이터 모델
data class TaskLisBydatetRequest(val date: String)

data class TaskbynameRequest(
    val username: String
)

data class Task(
    val status_id: String,
    val username: String,
    val current_status: String,
    val status_date: String,
    val status_time: String
)

data class TaskListResponse(val users: List<Task>)

// API 인터페이스
interface TaskListApi {
    @GET("user_status/filter/{date}/") // Task 리스트 API의 엔드포인트
    fun getTaskListBydate(@Path("date") date: String): Call<List<Task>> // 사용자 목록을 List<Task> 형태로 반환

    @POST("user_status/by-username/") // Task 리스트 API의 엔드포인트
    fun getTaskListByusername(@Body request: TaskbynameRequest): Call<List<Task>> // 사용자 목록을 List<Task> 형태로 반환
}
