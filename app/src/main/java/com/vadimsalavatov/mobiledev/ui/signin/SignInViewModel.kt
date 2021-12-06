package com.vadimsalavatov.mobiledev.ui.signin

import androidx.lifecycle.viewModelScope
import com.vadimsalavatov.mobiledev.repository.AuthRepository
import com.vadimsalavatov.mobiledev.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SignInViewModel : BaseViewModel() {
    fun signIn() {
        viewModelScope.launch {
            AuthRepository.signIn()
        }
    }
}