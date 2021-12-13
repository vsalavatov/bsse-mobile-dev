package com.vadimsalavatov.mobiledev.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object AuthRepository {
    private val _isAuthorizedFlow = MutableStateFlow(false)
    val isAuthorizedFlow = _isAuthorizedFlow.asStateFlow()

    suspend fun signIn(email: String, password: String) {
        _isAuthorizedFlow.emit(true)
    }

    suspend fun logout() {
        _isAuthorizedFlow.emit(false)
    }

    suspend fun signUp(
        firstname: String,
        lastname: String,
        nickname: String,
        email: String,
        password: String,
        code: String
    ) {
        // TODO: use API call
        _isAuthorizedFlow.emit(true)
    }
}