package com.vadimsalavatov.mobiledev.ui.signup

import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.vadimsalavatov.mobiledev.data.network.response.error.SendRegistrationVerificationCodeErrorResponse
import com.vadimsalavatov.mobiledev.interactor.RegisterWithEmailInteractor
import com.vadimsalavatov.mobiledev.repository.VerificationCodeRepository
import com.vadimsalavatov.mobiledev.ui.base.BaseViewModel
import com.vadimsalavatov.mobiledev.ui.emailconfirmation.EmailConfirmationViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() :
    BaseViewModel() {

    private val _signUpActionStateFlow = MutableStateFlow<SignUpViewModel.Event>(
        SignUpViewModel.Event.SignUpPending)

    fun signUpActionStateFlow(): Flow<SignUpViewModel.Event> {
        Timber.d("sign up view model action flow")
        return _signUpActionStateFlow.asStateFlow()
    }

    var formData: SignUpFormData? = null

    fun signUp(
        firstname: String,
        lastname: String,
        nickname: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            try {
                formData = SignUpFormData(firstname, lastname, nickname, email, password)
                _signUpActionStateFlow.emit(Event.SignUpEmailConfirmationRequired)
            } catch (error: Throwable) {
                Timber.e(error)
                _signUpActionStateFlow.emit(Event.SignUpUnknownError(NetworkResponse.UnknownError(error)))
            }
        }
    }

    sealed class Event {
        object SignUpPending: Event()
        object SignUpEmailConfirmationRequired : Event()
        data class SignUpServerError(val e: NetworkResponse.ServerError<SendRegistrationVerificationCodeErrorResponse>) :
            Event()

        data class SignUpUnknownError(val e: NetworkResponse.UnknownError) : Event()
        data class SignUpNetworkError(val e: NetworkResponse.NetworkError) : Event()
    }

    data class SignUpFormData(
        val firstname: String,
        val lastname: String,
        val nickname: String,
        val email: String,
        val password: String
    )
}