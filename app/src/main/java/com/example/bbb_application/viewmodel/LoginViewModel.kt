package com.example.bbb_application.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class LoginViewModel : ViewModel() {
    // 로그인한 유저 이름을 상태로 관리
    private val _loggedInUser = mutableStateOf<String?>(null)
    val loggedInUser: State<String?> = _loggedInUser

    // 로그인 성공 시 loggedInUser 값을 변경하는 함수
    fun setLoggedInUser(username: String) {
        _loggedInUser.value = username
    }

    // 로그아웃 메서드
    fun logout() {
        _loggedInUser.value = null
    }
}