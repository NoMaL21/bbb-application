import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// 로그인 요청을 위한 데이터 모델
data class LoginRequest(val username: String, val password: String)

// 로그인 응답 데이터 모델
data class LoginResponse(val message: String? = null, val error: String? = null)

// API 인터페이스
interface LoginApi {
    @POST("login/") // 로그인 API의 엔드포인트
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}
