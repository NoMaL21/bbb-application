package com.example.bbb_application.ui.pages

import com.example.bbb_application.viewmodel.LoginViewModel
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                        teamMembers = response.map { TeamMember(it.username, it.department) } // department 추가
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
        topBar = { TopAppBar(title = { Text("Company Members") }) },
        content = { paddingValues ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
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
                        DepartmentWiseTeamMemberList(teamMembers, navController)
                    }
                }
            }
        }
    )
}

@Composable
fun DepartmentWiseTeamMemberList(teamMembers: List<TeamMember>, navController: NavHostController) {
    // department별로 팀 멤버를 그룹화하고 정렬
    val departmentMap = teamMembers.groupBy { it.department }.toSortedMap()

    LazyColumn {
        departmentMap.forEach { (department, members) ->
            item {
                // 부서 헤더
                Text(
                    text = department,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                )
                // 팀 멤버 리스트
                members.forEach { member ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp, horizontal = 16.dp)
                            .clickable { navController.navigate("memberpage/${member.username}")},

                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = member.username)
                    }
                }
                HorizontalDivider(thickness = 0.5.dp) // 구분선
            }
        }
    }
}


// 팀 멤버 데이터 모델
data class TeamMember(
    val username: String,
    val department: String
)
