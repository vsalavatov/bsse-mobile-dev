package com.vadimsalavatov.mobiledev.ui.emailconfirmation

import androidx.lifecycle.viewModelScope
import com.vadimsalavatov.mobiledev.repository.AuthRepository
import com.vadimsalavatov.mobiledev.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class EmailConfirmationViewModel : BaseViewModel() {
    fun confirmCode(
        firstname: String,
        lastname: String,
        nickname: String,
        email: String,
        password: String,
        code: String
    ) {
        viewModelScope.launch {
            try {
                // Timber.d("FORM DATA: $firstname $lastname $nickname $email $password $code")
                AuthRepository.signUp(
                    firstname,
                    lastname,
                    nickname,
                    email,
                    password,
                    code
                )
            } catch (error: Exception) {
            }
        }
    }
}