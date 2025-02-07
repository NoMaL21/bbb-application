package com.example.bbb_application.api

import com.example.bbb_application.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Retrofit을 통해 API 요청을 보내는 서비스 클래스
object ApiService {

    private const val BASE_URL = "http://${BuildConfig.SERVER_IP}/api/" // 실제 API 서버의 URL로 변경

    // OkHttpClient에 Interceptor 추가
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // BODY로 설정하면 요청 URL, 헤더, 응답 바디까지 모두 로깅
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor) // Interceptor 추가
        .build()

    // Retrofit 인스턴스 생성
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // LoginApi 인터페이스 구현
    val loginApi: LoginApi = retrofit.create(LoginApi::class.java)

    // 로그인 요청 메서드
    fun login(username: String, password: String, callback: (String?) -> Unit) {
        val loginRequest = LoginRequest(username, password)

        // API 호출
        loginApi.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    callback(responseBody?.message ?: responseBody?.error)
                } else {
                    callback("Unknown error occurred.")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback("Network error: ${t.message}")
            }
        })
    }

    // UserListApi 인터페이스 구현
    val userListApi: UserListApi = retrofit.create(UserListApi::class.java)

    // 유저 리스트 요청 메서드
    fun getUserList(callback: (List<User>?) -> Unit) {
        // API 호출
        userListApi.getUserList().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    // 성공적인 응답을 받았을 때
                    callback(response.body())
                } else {
                    // 실패한 응답을 받았을 때
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                // 네트워크나 다른 오류가 발생했을 때
                callback(null)
            }
        })
    }

    private val signUpApi: SignUpApi = retrofit.create(SignUpApi::class.java)

    fun signUp(
        username: String,
        password: String,
        department: String,
        name: String,
        callback: (Boolean) -> Unit
    ) {
        val request = SignUpRequest(username, password, department, name)
        signUpApi.signUp(request).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                callback(response.isSuccessful)
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback(false)
            }
        })
    }

    // taskListApi 인터페이스 구현
    val taskListApi: TaskListApi = retrofit.create(TaskListApi::class.java)

    // task 리스트 요청 메서드
    fun getTaskListBydate(date: String, callback: (List<Task>?) -> Unit) {
        // API 호출
        taskListApi.getTaskListBydate(date).enqueue(object : Callback<List<Task>> {
            override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                if (response.isSuccessful) {
                    // 성공적인 응답을 받았을 때
                    callback(response.body())
                } else {
                    // 실패한 응답을 받았을 때
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Task>>, t: Throwable) {
                // 네트워크나 다른 오류가 발생했을 때
                callback(null)
            }
        })
    }

    fun getTaskListByusername(username: String, callback: (List<Task>?) -> Unit) {
        // API 호출
        val TaskbynameRequest = TaskbynameRequest(username)

        taskListApi.getTaskListByusername(TaskbynameRequest).enqueue(object : Callback<List<Task>> {
            override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                if (response.isSuccessful) {
                    // 성공적인 응답을 받았을 때
                    callback(response.body())
                } else {
                    // 실패한 응답을 받았을 때
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Task>>, t: Throwable) {
                // 네트워크나 다른 오류가 발생했을 때
                callback(null)
            }
        })
    }

    // AdminApi 인터페이스 구현
    val adminApi: AdminApi = retrofit.create(AdminApi::class.java)

    // 로그인 요청 메서드
    fun adminapprove(username: String, callback: (String?) -> Unit) {
        val adminApproveRequest = AdminApproveRequest(username)

        // API 호출
        adminApi.adminapprove(adminApproveRequest).enqueue(object : Callback<AdminApproveResponse> {
            override fun onResponse(call: Call<AdminApproveResponse>, response: Response<AdminApproveResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    callback(responseBody?.message ?: responseBody?.error)
                } else {
                    callback("Unknown error occurred.")
                }
            }

            override fun onFailure(call: Call<AdminApproveResponse>, t: Throwable) {
                callback("Network error: ${t.message}")
            }
        })
    }

    val taskApi: TaskApi = retrofit.create(TaskApi::class.java)
    // 작업 삭제 API 호출
    suspend fun deleteTask(taskId: Int): Response<TaskResponse> {
        return taskApi.deleteTask(taskId)
    }

    // 작업 수정 API 호출
    suspend fun updateTask(taskId: Int, taskRequest: TaskRequest): Response<TaskResponse> {
        return taskApi.updateTask(taskId, taskRequest)
    }

}
