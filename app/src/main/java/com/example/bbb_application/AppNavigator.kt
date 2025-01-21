import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.viewmodel.compose.viewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppScreen() {
    val navController = rememberNavController()
    var showBottomBar by remember { mutableStateOf(false) } // 하단 바 표시 여부

    // NavController의 상태 변화 감지
    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            showBottomBar = destination.route != "login" // 로그인 페이지가 아니면 표시
        }
    }

    Scaffold(
        content = { padding ->
            AppNavigator(navController = navController, modifier = Modifier.padding(padding))
        },
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController)
            }
        }
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigator(navController: NavHostController, modifier: Modifier = Modifier) {
    val loginViewModel: LoginViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable("login") { LoginPage(navController, loginViewModel = loginViewModel) }
        composable("main") { MainPage(navController, loginViewModel = loginViewModel) }
        composable("calendar") { CalendarPage(navController) }
        composable("team") { TeamPage(navController) }
        composable("task") { TaskPage(navController) }
        composable("settings") { SettingsPage(navController) }
    }
}

