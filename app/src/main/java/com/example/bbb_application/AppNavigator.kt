import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
fun AppNavigator(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = "login", modifier = modifier) {
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






