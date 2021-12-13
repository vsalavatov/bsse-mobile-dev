package com.vadimsalavatov.mobiledev.ui.emailconfirmation

import android.os.CountDownTimer
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.vadimsalavatov.mobiledev.data.network.response.error.CreateProfileErrorResponse
import com.vadimsalavatov.mobiledev.data.network.response.error.SendRegistrationVerificationCodeErrorResponse
import com.vadimsalavatov.mobiledev.data.network.response.error.VerifyRegistrationCodeErrorResponse
import com.vadimsalavatov.mobiledev.interactor.AuthInteractor
import com.vadimsalavatov.mobiledev.interactor.RegisterWithEmailInteractor
import com.vadimsalavatov.mobiledev.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EmailConfirmationViewModel @Inject constructor(
    private val authInteractor: AuthInteractor,
    private val registerWithEmailInteractor: RegisterWithEmailInteractor
) :
    BaseViewModel() {

    private val _emailConfirmationActionStateFlow =
        MutableStateFlow<EmailConfirmationViewModel.EmailConfirmationActionState>(
            EmailConfirmationViewModel.EmailConfirmationActionState.Pending
        )

    fun emailConfirmationActionStateFlow(): Flow<EmailConfirmationViewModel.EmailConfirmationActionState> {
        return _emailConfirmationActionStateFlow.asStateFlow()
    }

    private val SEND_AGAIN_TIMEOUT_SECONDS = 15L
    private var sendAgainTimer: CountDownTimer? = null
    private val _timerStateFlow = MutableStateFlow(0L)
    val timerStateFlow = _timerStateFlow.asStateFlow()

    suspend fun sendVerificationCode(email: String) {
        _emailConfirmationActionStateFlow.emit(EmailConfirmationActionState.Loading)
        when (val response = registerWithEmailInteractor.sendVerificationCode(email)) {
            is NetworkResponse.Success -> {
                sendAgainTimer?.cancel()
                sendAgainTimer = object : CountDownTimer(SEND_AGAIN_TIMEOUT_SECONDS * 1000, 200L) {
                    override fun onTick(leftMs: Long) {
                        viewModelScope.launch {
                            _timerStateFlow.emit(leftMs)
                        }
                    }
                    override fun onFinish() {
                        viewModelScope.launch {
                            _timerStateFlow.emit(0)
                        }
                    }
                }.start()
                _emailConfirmationActionStateFlow.emit(EmailConfirmationActionState.Pending)
            }
            is NetworkResponse.ServerError -> {
                _emailConfirmationActionStateFlow.emit(
                    EmailConfirmationActionState.SendVerificationCodeError(
                        response
                    )
                )
            }
            is NetworkResponse.NetworkError -> {
                _emailConfirmationActionStateFlow.emit(
                    EmailConfirmationActionState.NetworkError(
                        response
                    )
                )
            }
            is NetworkResponse.UnknownError -> {
                _emailConfirmationActionStateFlow.emit(
                    EmailConfirmationActionState.UnknownError(
                        response
                    )
                )
            }
        }
    }

    fun signUpWithCode(
        firstname: String,
        lastname: String,
        nickname: String,
        email: String,
        password: String,
        code: String
    ) {
        viewModelScope.launch {
            _emailConfirmationActionStateFlow.emit(EmailConfirmationActionState.Loading)
            try {
                val verificationToken: String
                when (val result = registerWithEmailInteractor.verifyEmail(email, code)) {
                    is NetworkResponse.Success -> {
                        verificationToken = result.body.verificationToken
                    }
                    is NetworkResponse.ServerError -> {
                        _emailConfirmationActionStateFlow.emit(
                            EmailConfirmationActionState.EmailVerificationError(
                                result
                            )
                        )
                        return@launch
                    }
                    is NetworkResponse.NetworkError -> {
                        _emailConfirmationActionStateFlow.emit(
                            EmailConfirmationActionState.NetworkError(
                                result
                            )
                        )
                        return@launch
                    }
                    is NetworkResponse.UnknownError -> {
                        _emailConfirmationActionStateFlow.emit(
                            EmailConfirmationActionState.UnknownError(
                                result
                            )
                        )
                        return@launch
                    }
                }
                when (val result = authInteractor.signUpWithEmailAndPersonalInfoAndVerificationCode(
                    firstname,
                    lastname,
                    nickname,
                    email,
                    password,
                    verificationToken
                )) {
                    is NetworkResponse.Success -> {
                        _emailConfirmationActionStateFlow.emit(EmailConfirmationActionState.Pending)
                    }
                    is NetworkResponse.ServerError -> {
                        _emailConfirmationActionStateFlow.emit(
                            EmailConfirmationActionState.ServerError(
                                result
                            )
                        )
                    }
                    is NetworkResponse.NetworkError -> {
                        _emailConfirmationActionStateFlow.emit(
                            EmailConfirmationActionState.NetworkError(
                                result
                            )
                        )
                    }
                    is NetworkResponse.UnknownError -> {
                        _emailConfirmationActionStateFlow.emit(
                            EmailConfirmationActionState.UnknownError(
                                result
                            )
                        )
                    }
                }
            } catch (error: Throwable) {
                Timber.e(error)
                _emailConfirmationActionStateFlow.emit(
                    EmailConfirmationActionState.UnknownError(
                        NetworkResponse.UnknownError(error)
                    )
                )
            }
        }
    }

    sealed class EmailConfirmationActionState {
        object Pending : EmailConfirmationActionState()
        object Loading : EmailConfirmationActionState()
        data class ServerError(val e: NetworkResponse.ServerError<CreateProfileErrorResponse>) :
            EmailConfirmationActionState()

        data class SendVerificationCodeError(val e: NetworkResponse.ServerError<SendRegistrationVerificationCodeErrorResponse>) :
            EmailConfirmationActionState()

        data class EmailVerificationError(val e: NetworkResponse.ServerError<VerifyRegistrationCodeErrorResponse>) :
            EmailConfirmationActionState()

        data class NetworkError(val e: NetworkResponse.NetworkError) :
            EmailConfirmationActionState()

        data class UnknownError(val e: NetworkResponse.UnknownError) :
            EmailConfirmationActionState()
    }
}