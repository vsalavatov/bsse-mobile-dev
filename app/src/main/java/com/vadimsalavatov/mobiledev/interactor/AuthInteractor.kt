package com.vadimsalavatov.mobiledev.interactor

import com.haroldadmin.cnradapter.NetworkResponse
import com.vadimsalavatov.mobiledev.data.network.response.error.CreateProfileErrorResponse
import com.vadimsalavatov.mobiledev.data.network.response.error.SignInWithEmailErrorResponse
import com.vadimsalavatov.mobiledev.entity.AuthTokens
import com.vadimsalavatov.mobiledev.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend fun isAuthorized(): Flow<Boolean> =
        authRepository.isAuthorizedFlow()

    suspend fun signInWithEmail(
        email: String,
        password: String
    ): NetworkResponse<AuthTokens, SignInWithEmailErrorResponse> {
        val response = authRepository.generateAuthTokensByEmail(email, password)
        when (response) {
            is NetworkResponse.Success -> {
                authRepository.saveAuthTokens(response.body)
            }
            is NetworkResponse.Error -> {
                Timber.e(response.error)
            }
        }
        return response
    }

    suspend fun signUpWithEmailAndPersonalInfoAndVerificationCode(
        email: String,
        firstName: String,
        lastName: String,
        userName: String,
        password: String,
        code: String
    ): NetworkResponse<AuthTokens, CreateProfileErrorResponse> {
        val response = authRepository.generateAuthTokensByEmailAndPersonalInfo(email, code, firstName, lastName, userName, password)
        when (response) {
            is NetworkResponse.Success -> {
                authRepository.saveAuthTokens(response.body)
            }
            is NetworkResponse.Error -> {
                Timber.e(response.error)
            }
        }
        return response
    }

    suspend fun logout() {
        authRepository.saveAuthTokens(null)
    }
}