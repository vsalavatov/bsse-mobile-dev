package com.vadimsalavatov.mobiledev.util

import android.content.Context
import android.widget.Toast
import com.haroldadmin.cnradapter.NetworkResponse
import com.vadimsalavatov.mobiledev.R

fun <T : Any> NetworkResponse.ServerError<T>.showAsToast(context: Context) {
    Toast.makeText(context, "${context.resources.getString(R.string.error_server_error_text)}: ${this.body}", Toast.LENGTH_LONG).show()
}

fun NetworkResponse.NetworkError.showAsToast(context: Context) {
    Toast.makeText(context, "${context.resources.getString(R.string.error_network_error_text)}: ${this.error}", Toast.LENGTH_LONG).show()
}

fun NetworkResponse.UnknownError.showAsToast(context: Context) {
    Toast.makeText(context, "${context.resources.getString(R.string.error_unknown_error_text)}: $this", Toast.LENGTH_LONG).show()
}