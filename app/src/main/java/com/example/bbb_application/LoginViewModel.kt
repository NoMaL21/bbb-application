import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class LoginViewModel : ViewModel() {
    // 로그인한 유저 이름을 상태로 관리
    private val _loggedInUser = mutableStateOf<String?>(null)
    val loggedInUser: State<String?> = _loggedInUser

    // 로그인 처리 함수
    fun login(username: String, password: String) {
        if (username == "test" && password == "1234") {
            _loggedInUser.value = username // 로그인 성공 시 유저 이름 저장
        }
    }
}