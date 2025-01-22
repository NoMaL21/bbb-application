package com.example.bbb_application

import LoginApi
import LoginRequest
import LoginResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Retrofit을 통해 API 요청을 보내는 서비스 클래스
object ApiService {

    private const val BASE_URL = "http://${BuildConfig.SERVER_IP}" // 실제 API 서버의 URL로 변경

    // Retrofit 인스턴스 생성
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // LoginApi 인터페이스 구현
    val loginApi: LoginApi = retrofit.create(LoginApi::class.java)

    // 로그인 요청 메서드
    fun login(username: String, password: String, callback: (LoginResponse?) -> Unit) {
        val loginRequest = LoginRequest(username, password)

        // API 호출
        loginApi.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    // 성공적인 응답을 받았을 때
                    callback(response.body())
                } else {
                    // 실패한 응답을 받았을 때
                    callback(null)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // 네트워크나 다른 오류가 발생했을 때
                callback(null)
            }
        })
    }
}
