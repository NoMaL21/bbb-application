package com.example.bbb_application.ui.pages

import com.example.bbb_application.viewmodel.LoginViewModel
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bbb_application.api.ApiService

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamPage(navController: NavHostController, loginViewModel: LoginViewModel = viewModel()) {
    // 로그인된 유저 정보 가져오기
    val loggedInUser by loginViewModel.loggedInUser // Flow를 collect해서 상태로 받기

    // API 호출 및 결과 상태
    var teamMembers by remember { mutableStateOf<List<TeamMember>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // 로그인된 유저 정보가 있을 때만 API 호출
    LaunchedEffect(loggedInUser) {
        if (loggedInUser != null) {
            try {
                Log.d("TeamPage", "Logged in user: $loggedInUser")
                // ApiService.getUserList() 호출
                ApiService.getUserList { response ->
                    if (response != null) {
                        // 유저 리스트를 받아서 팀 멤버로 설정
                        teamMembers = response.map { TeamMember(it.username) } // username을 TeamMember에 매핑
                        Log.d("TeamPage", "Team members: $teamMembers")
                    } else {
                        errorMessage = "Failed to load team members"
                    }
                }
            } catch (e: Exception) {
                errorMessage = "Failed to load team members: ${e.message}"
            } finally {
                isLoading = false
            }
        } else {
            errorMessage = "User is not logged in"
            isLoading = false
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Team Members") }) },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                // 로딩 중일 때
                if (isLoading) {
                    CircularProgressIndicator()
                    Text("Loading...", color = MaterialTheme.colorScheme.primary)
                } else {
                    // 에러가 있을 때
                    errorMessage?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    } ?: run {
                        // 팀 멤버 리스트가 있을 때
                        TeamMemberList(teamMembers)
                    }
                }
            }
        }
    )

}

@Composable
fun TeamMemberList(teamMembers: List<TeamMember>) {
    Column {
        if (teamMembers.isEmpty()) {
            Text("No team members found.")
        } else {
            teamMembers.forEach { member ->
                Text(text = "Name: ${member.name}")
                // 다른 팀 멤버 정보도 표시할 수 있습니다.
            }
        }
    }
}

// 실제 API 호출 함수 (예시)
suspend fun apiGetTeamMembers(loggedInUser: String): List<TeamMember> {
    // 실제 API 호출 코드로 대체 (예시로 팀 멤버 목록 반환)
    Log.d("TeamPage", "apiGet running, ${loggedInUser}")
    // 예시로 팀 멤버 목록 반환
    return listOf(
        TeamMember(name = "Alice"),
        TeamMember(name = "Bob"),
        TeamMember(name = "Charlie")
    )
}

// 팀 멤버 데이터 모델
data class TeamMember(val name: String)
