package com.vadimsalavatov.mobiledev.interactor

import android.os.CountDownTimer
import com.haroldadmin.cnradapter.NetworkResponse
import com.vadimsalavatov.mobiledev.data.network.response.VerificationTokenResponse
import com.vadimsalavatov.mobiledev.data.network.response.error.SendRegistrationVerificationCodeErrorResponse
import com.vadimsalavatov.mobiledev.data.network.response.error.VerifyRegistrationCodeErrorResponse
import com.vadimsalavatov.mobiledev.repository.AuthRepository
import com.vadimsalavatov.mobiledev.repository.VerificationCodeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class RegisterWithEmailInteractor @Inject constructor(
    private val authRepository: AuthRepository,
    private val verificationCodeRepository: VerificationCodeRepository
) {

    private val emailFlow = MutableStateFlow<String?>(null)

    private val resendTimerFlow = MutableStateFlow<Long>(0)

    private var verificationToken: String? = null

    private var resendVerificationCodeTimer: CountDownTimer? = null

    fun emailFlow(): Flow<String?> = emailFlow.asStateFlow()

    fun resendTimerFlow(): Flow<Long> = resendTimerFlow.asStateFlow()

    suspend fun sendVerificationCode(email: String): NetworkResponse<Unit, SendRegistrationVerificationCodeErrorResponse> {
        emailFlow.emit(email)
        return sendVerificationCodeInternal(email)
    }

    private fun sendVerificationCodeInternal(email: String): NetworkResponse<Unit, SendRegistrationVerificationCodeErrorResponse> {
        
    }

    suspend fun verifyEmail(code: String): NetworkResponse<VerificationTokenResponse, VerifyRegistrationCodeErrorResponse> {
        val response = verificationCodeRepository.verifyEmailRegistrationCode(code, )
    }


}