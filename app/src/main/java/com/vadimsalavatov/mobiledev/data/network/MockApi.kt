package com.vadimsalavatov.mobiledev.data.network

import com.haroldadmin.cnradapter.NetworkResponse
import com.vadimsalavatov.mobiledev.data.network.request.CreateProfileRequest
import com.vadimsalavatov.mobiledev.data.network.request.RefreshAuthTokensRequest
import com.vadimsalavatov.mobiledev.data.network.request.SignInWithEmailRequest
import com.vadimsalavatov.mobiledev.data.network.response.GetUsersResponse
import com.vadimsalavatov.mobiledev.data.network.response.VerificationTokenResponse
import com.vadimsalavatov.mobiledev.data.network.response.error.*
import com.vadimsalavatov.mobiledev.entity.AuthTokens
import com.vadimsalavatov.mobiledev.entity.User

class MockApi : Api {
    private val users = mutableListOf<User>()
    private val defaultToken = AuthTokens(
        accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dnZWRJbkFzIjoiYWRtaW4iLCJpYXQiOjE0MjI3Nzk2MzgsImV4cCI6MTY0MDg3MTc3MX0.gzSraSYS8EXBxLN_oWnFSRgCzcmJmMjLiuyu5CSpyHI",
        refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dnZWRJbkFzIjoiYWRtaW4iLCJpYXQiOjE0MjI3Nzk2MzgsImV4cCI6MTY0MDg3MTc3MX0.gzSraSYS8EXBxLN_oWnFSRgCzcmJmMjLiuyu5CSpyHI",
        accessTokenExpiration = 1640871771000,
        refreshTokenExpiration = 1640871771000,
    )


    override suspend fun getUsers(): GetUsersResponse {
        return GetUsersResponse(users)
    }

    override suspend fun signInWithEmail(request: SignInWithEmailRequest): NetworkResponse<AuthTokens, SignInWithEmailErrorResponse> {
        return NetworkResponse.Success(
            defaultToken,
            code = 200
        )
    }

    override suspend fun refreshAuthTokens(request: RefreshAuthTokensRequest): NetworkResponse<AuthTokens, RefreshAuthTokensErrorResponse> {
        return NetworkResponse.Success(
            defaultToken,
            code = 200
        )
    }

    override suspend fun sendRegistrationVerificationCode(email: String): NetworkResponse<Unit, SendRegistrationVerificationCodeErrorResponse> {
        // code = 11111111
        return NetworkResponse.Success(Unit, code=200)
    }

    override suspend fun verifyRegistrationCode(
        code: String,
        email: String?
    ): NetworkResponse<VerificationTokenResponse, VerifyRegistrationCodeErrorResponse> {
        if (code == "11111111") {
            return NetworkResponse.Success(VerificationTokenResponse(code), code=200)
        }
        return NetworkResponse.ServerError(VerifyRegistrationCodeErrorResponse(listOf(Error("wrong code!"))), code=400)
    }

    override suspend fun createProfile(request: CreateProfileRequest): NetworkResponse<AuthTokens, CreateProfileErrorResponse> {
        users.add(
            User(
                "https://pixnio.com/free-images/2021/03/30/2021-03-30-11-06-07-1800x1200.jpg",
                request.userName,
                "${request.firstName} ${request.lastName}"
            )
        )
        return NetworkResponse.Success(defaultToken, code = 200)
    }
}