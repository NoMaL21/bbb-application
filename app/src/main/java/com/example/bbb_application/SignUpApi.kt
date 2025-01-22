import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class SignUpRequest(
    val username: String,
    val password: String,
    val department: String,
    val name: String,
    val can_login: Boolean = false
)

interface SignUpApi {
    @POST("users/")
    fun signUp(@Body request: SignUpRequest): Call<Void>
}