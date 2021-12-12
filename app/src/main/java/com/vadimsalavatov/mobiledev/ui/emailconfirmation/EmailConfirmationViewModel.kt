package com.vadimsalavatov.mobiledev.ui.emailconfirmation

import androidx.lifecycle.viewModelScope
import com.vadimsalavatov.mobiledev.interactor.AuthInteractor
import com.vadimsalavatov.mobiledev.repository.AuthRepository
import com.vadimsalavatov.mobiledev.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailConfirmationViewModel @Inject constructor(private val authInteractor: AuthInteractor) : BaseViewModel() {
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
                authInteractor.signUp(
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