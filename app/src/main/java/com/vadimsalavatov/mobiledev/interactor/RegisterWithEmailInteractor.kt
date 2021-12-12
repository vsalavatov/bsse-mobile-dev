package com.vadimsalavatov.mobiledev.interactor

import android.os.CountDownTimer
import com.haroldadmin.cnradapter.NetworkResponse
import com.vadimsalavatov.mobiledev.data.network.response.VerificationTokenResponse
import com.vadimsalavatov.mobiledev.data.network.response.error.SendRegistrationVerificationCodeErrorResponse
import com.vadimsalavatov.mobiledev.data.network.response.error.VerifyRegistrationCodeErrorResponse
import com.vadimsalavatov.mobiledev.repository.VerificationCodeRepository
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

class RegisterWithEmailInteractor @Inject constructor(
    // private val authRepository: AuthRepository,
    private val verificationCodeRepository: VerificationCodeRepository
) {

    init {
        Timber.d("register with email interactor init")
    }

    private val resendTimerFlow = MutableStateFlow<Long>(0)

    private var resendVerificationCodeTimer: CountDownTimer? = null

    fun resendTimerFlow(): Flow<Long> = resendTimerFlow.asStateFlow()

    suspend fun sendVerificationCode(email: String): NetworkResponse<Unit, SendRegistrationVerificationCodeErrorResponse> {
        return sendVerificationCodeInternal(email)
    }

    private suspend fun sendVerificationCodeInternal(email: String): NetworkResponse<Unit, SendRegistrationVerificationCodeErrorResponse> {
        return verificationCodeRepository.sendRegistrationVerificationCodeToEmail(email)
    }

    suspend fun verifyEmail(
        email: String,
        code: String
    ): NetworkResponse<VerificationTokenResponse, VerifyRegistrationCodeErrorResponse> {
        return verificationCodeRepository.verifyEmailRegistrationCode(code, email)
    }
}