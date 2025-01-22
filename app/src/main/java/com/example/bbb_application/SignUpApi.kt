import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class SignUpRequest(
    val username: String,
    val password: String,
    val department: String,
    val name: String
)

interface SignUpApi {
    @POST("register/")
    fun signUp(@Body request: SignUpRequest): Call<Void>
}