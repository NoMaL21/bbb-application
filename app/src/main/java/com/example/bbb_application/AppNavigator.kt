import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bbb_application.CalendarPage
import com.example.bbb_application.LoginPage
import com.example.bbb_application.MainPage
import com.example.bbb_application.SettingsPage
import com.example.bbb_application.TaskPage
import com.example.bbb_application.TeamPage

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppScreen() {
    val navController = rememberNavController()

    Scaffold(
        content = { padding ->
            // content에 전달된 padding을 사용하여 화면의 여백을 설정
            AppNavigator(navController = navController, modifier = Modifier.padding(padding)) // padding 적용
        },
        bottomBar = {
            // 하단 네비게이션 버튼들 배치
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { navController.navigate("calendar") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("C")
                }
                Button(
                    onClick = { navController.navigate("team") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("T")
                }
                Button(
                    onClick = { navController.navigate("task") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("T")
                }
                Button(
                    onClick = { navController.navigate("settings") },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("S")
                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigator(navController: NavHostController, modifier: Modifier = Modifier) {
    // NavHostController에서 이동할 페이지들 정의
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier  // padding이 적용된 modifier를 사용
    ) {
        composable("login") { LoginPage(navController) }
        composable("main") { MainPage(navController) }
        composable("calendar") { CalendarPage(navController) }
        composable("team") { TeamPage(navController) }
        composable("task") { TaskPage(navController) }
        composable("settings") { SettingsPage(navController) }
        composable("details/{date}") { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date")
            DetailsPage(date = date)
        }
    }
}










