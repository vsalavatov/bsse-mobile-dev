package com.vadimsalavatov.mobiledev.data.network

import com.vadimsalavatov.mobiledev.data.network.response.GetUsersResponse
import retrofit2.http.GET

interface Api {
    @GET("users")
    suspend fun getUsers(): GetUsersResponse
}
