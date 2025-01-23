package com.example.bbb_application.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PUT

// 어드민 승인 요청을 위한 데이터 모델
data class AdminApproveRequest(val username: String)

// 어드민 승인 응답 데이터 모델
data class AdminApproveResponse(val message: String? = null, val error: String? = null)

// API 인터페이스
interface AdminApi {
    @PUT("admin/approve/") // 로그인 API의 엔드포인트
    fun adminapprove(@Body adminApproveRequest: AdminApproveRequest): Call<AdminApproveResponse>
}