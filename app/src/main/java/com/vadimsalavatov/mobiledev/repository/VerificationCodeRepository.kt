package com.vadimsalavatov.mobiledev.repository

import com.haroldadmin.cnradapter.NetworkResponse
import com.vadimsalavatov.mobiledev.data.network.Api
import com.vadimsalavatov.mobiledev.data.network.response.VerificationTokenResponse
import com.vadimsalavatov.mobiledev.data.network.response.error.SendRegistrationVerificationCodeErrorResponse
import com.vadimsalavatov.mobiledev.data.network.response.error.VerifyRegistrationCodeErrorResponse
import javax.inject.Inject

class VerificationCodeRepository @Inject constructor(
    private val api: Api
) {

    suspend fun sendRegistrationVerificationCodeToEmail(
        email: String
    ): NetworkResponse<Unit, SendRegistrationVerificationCodeErrorResponse> {
        return api.sendRegistrationVerificationCode(email)
    }

    suspend fun verifyEmailRegistrationCode(
        code: String,
        email: String
    ): NetworkResponse<VerificationTokenResponse, VerifyRegistrationCodeErrorResponse> {
        return api.verifyRegistrationCode(code, email)
    }
}
