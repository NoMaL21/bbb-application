import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET

// 로그인 요청을 위한 데이터 모델
data class UserListRequest(val username: String)

data class User(
    val username: String,
    val department: String,
    val name: String,
    val password: String,
    val can_login: Boolean
)

data class UserListResponse(val users: List<User>)

// API 인터페이스
interface UserListApi {
    @GET("users") // 유저 리스트 API의 엔드포인트
    fun getUserList(): Call<List<User>> // 사용자 목록을 List<User> 형태로 반환
}
