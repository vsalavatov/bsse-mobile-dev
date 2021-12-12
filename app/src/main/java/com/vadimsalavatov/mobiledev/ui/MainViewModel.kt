package com.vadimsalavatov.mobiledev.ui

import com.vadimsalavatov.mobiledev.repository.AuthRepository
import com.vadimsalavatov.mobiledev.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val authRepository: AuthRepository) : BaseViewModel() {
    suspend fun isAuthorizedFlow(): Flow<Boolean> = authRepository.isAuthorizedFlow()
}