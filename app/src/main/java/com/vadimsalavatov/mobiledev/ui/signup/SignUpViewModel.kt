package com.vadimsalavatov.mobiledev.ui.signup

import androidx.lifecycle.viewModelScope
import com.vadimsalavatov.mobiledev.repository.AuthRepository
import com.vadimsalavatov.mobiledev.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SignUpViewModel : BaseViewModel() {
    fun signUp() {
        viewModelScope.launch {
            AuthRepository.signIn(TODO(), TODO())
        }
    }
}