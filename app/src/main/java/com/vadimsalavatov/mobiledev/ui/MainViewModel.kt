package com.vadimsalavatov.mobiledev.ui

import com.vadimsalavatov.mobiledev.repository.AuthRepository
import com.vadimsalavatov.mobiledev.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


class MainViewModel : BaseViewModel() {
    val isAuthorizedFlow: Flow<Boolean> = AuthRepository.isAuthorizedFlow
}