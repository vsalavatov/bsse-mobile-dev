package com.vadimsalavatov.mobiledev.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.vadimsalavatov.mobiledev.entity.User


@JsonClass(generateAdapter = true)
data class GetUsersResponse(
    @Json(name = "data") val data: List<User>
)