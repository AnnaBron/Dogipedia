package com.anya.dogipedia.utils.exception.api

import android.content.Context
import com.anya.dogipedia.R
import com.anya.dogipedia.data.exception.api.ApiException
import com.anya.dogipedia.inject.qualifier.AppContext
import com.anya.dogipedia.utils.exception.ExceptionMessageProviders
import javax.inject.Inject

class ApiExceptionMessageProvider
@Inject constructor(@AppContext private val context: Context) : ExceptionMessageProviders.Api {

    override fun getMessage(exception: ApiException): String {
        var messageResId = R.string.api_error_general
        if (exception is ApiException.NotFoundException) {
            messageResId = R.string.api_error_not_found
        }
        if (exception is ApiException.ServerException) {
            messageResId = R.string.api_error_server_unavailable
        }
        if (exception is ApiException.NetworkException) {
            messageResId = R.string.api_error_network
        }
        return context.getString(messageResId)
    }

    override fun getMessage(throwable: Throwable): String {
        if (throwable is ApiException) {
            return getMessage(throwable)
        }
        return context.getString(R.string.api_error_general)
    }
}
