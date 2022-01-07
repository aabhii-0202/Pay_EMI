package com.mediustechnologies.payemi.helper

import android.util.Log
import com.mediustechnologies.payemi.commons.utils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ErrorInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request: Request = chain.request()
        val response = chain.proceed(request)
        when (response.code) {
            200 -> {

            }
            401 -> {
                //Show UnauthorizedError Message
                utils.refreshToken()
            }

            403 -> {
                //Show Forbidden Message
            }

            404 -> {
                //Show NotFound Message
            }

            // ... and so on

        }
        return response
    }
}