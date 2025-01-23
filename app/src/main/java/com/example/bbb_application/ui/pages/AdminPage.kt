package com.example.bbb_application.ui.pages

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bbb_application.api.ApiService
import com.example.bbb_application.api.User
import com.example.bbb_application.viewmodel.LoginViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPage(navController: NavHostController, loginViewModel: LoginViewModel) {
    val loggedInUser by loginViewModel.loggedInUser

    // API 호출 및 결과 상태
    var users by remember { mutableStateOf<List<User>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) } // 승인 성공 메시지 상태

    // 데이터 새로 고침 함수
    val refreshUsers = {
        isLoading = true
        ApiService.getUserList { response ->
            if (response != null) {
                users = response.map { User(it.username, it.department, it.name, it.password, it.can_login) }
            } else {
                errorMessage = "Failed to load team members"
            }
            isLoading = false
        }
    }

    // 로그인된 유저 정보가 있을 때만 API 호출
    LaunchedEffect(loggedInUser) {
        if (loggedInUser == "test") {
            try {
                Log.d("AdminPage", "Logged in user: $loggedInUser")
                // ApiService.getUserList() 호출
                ApiService.getUserList { response ->
                    if (response != null) {
                        // 유저 리스트를 받아서 팀 멤버로 설정
                        users = response.map { User(it.username, it.department, it.name, it.password, it.can_login) } // department 추가
                        Log.d("AdminPage", "members: $users")
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
        topBar = { TopAppBar(title = { Text("Admin's Page") }) },
        content = { paddingValues ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // 승인 성공 메시지 표시
                successMessage?.let {
                    Text(it, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(bottom = 16.dp))
                }
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
                        WaitingMemberList(
                            users = users,
                            navController = navController,
                            onRefresh = refreshUsers,
                            onMessage = { successMessage = it }
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun WaitingMemberList(
    users: List<User>,
    navController: NavHostController,
    onRefresh: () -> Unit,
    onMessage: (String) -> Unit
    )
    {
        // can_login이 false인 유저만 필터링
        val waitingMembers = users.filter { !it.can_login }

        LazyColumn {
            itemsIndexed(waitingMembers) { index, member ->  // itemsIndexed로 변경
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = member.username)

                    // 승인/거절 버튼
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                // 승인 처리 로직
                                approveUser(member.username, onRefresh, onMessage)
                            },
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text("Approve")
                        }
                        Button(
                            onClick = {
                                // 거절 처리 로직
                                rejectUser(member.username)
                            }
                        ) {
                            Text("Reject")
                        }
                    }
                }
                HorizontalDivider(thickness = 0.5.dp) // 구분선
            }
        }
}

fun approveUser(username: String, onRefresh: () -> Unit, onMessage: (String) -> Unit) {
    ApiService.adminapprove(username) { result ->
        if (result != null) {
            Log.d("AdminPage", "User $username approved: $result")
            onMessage("User $username has been approved!")
            onRefresh() // 목록 새로 고침
        } else {
            Log.e("AdminPage", "Failed to approve user $username")
            onMessage("Failed to approve user $username.")
        }
    }
}

fun rejectUser(username: Any) {

}
