package com.example.bbb_application.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

data class TaskRequest(
    val username: String,
    val current_status: String,
    val status_date: String,  // "2025-01-23"
    val status_time: String   // "15:30:00"
)

data class TaskResponse(
    val success: Boolean,
    val message: String
)

// TaskApi.kt - Retrofit API 인터페이스 정의
interface TaskApi {
    // 작업 추가 API 예시 (POST 방식)
    @POST("user_status/")
    suspend fun addTask(@Body taskRequest: TaskRequest): Response<TaskResponse>

    // 작업 목록을 가져오는 API 예시 (GET 방식)
    @GET("user_status/")
    suspend fun getTasks(): Response<List<Task>>  // 작업 목록을 List<Task>로 반환

    @GET("")
    suspend fun getTasksByUser(@Query("userId") userId: Int): Response<List<Task>>

    // 작업 삭제 API (DELETE 요청)
    @DELETE("deleteUserStatus/{UserId}/")
    fun deleteTask(@Path("taskId") taskId: Int): Response<TaskResponse>

    // 작업 수정 API (PUT 요청)
    @PUT("UpdateUserStatus/{UserId}/")
    suspend fun updateTask(@Path("taskId") taskId: Int, @Body taskRequest: TaskRequest): Response<TaskResponse>
}
