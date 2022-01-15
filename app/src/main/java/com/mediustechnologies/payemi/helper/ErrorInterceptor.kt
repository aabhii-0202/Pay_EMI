package com.mediustechnologies.payemi.helper


import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.mediustechnologies.payemi.commons.utils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ErrorInterceptor(val activityContext: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request: Request = chain.request()
        val response = chain.proceed(request)
        when (response.code) {
            utils.RESPONSE_SUCCESS -> {

            }
            utils.UNAUTH -> {
                //Show UnauthorizedError Message
                utils.refreshToken(activityContext)


            }

            502 -> {
                //Show Forbidden Message
                Log.e("tag","API not working Bad Gateway");
            }

            utils.NOT_FOUND -> {
                //Show NotFound Message
            }

            utils.INTERNAL_SERVER_ERROR -> {
                Log.e("tag","API not working");
            }

            // ... and so on

        }
        return response
    }
}