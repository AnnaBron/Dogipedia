package com.anya.dogipedia.data.exception.api

import com.anya.dogipedia.data.exception.ExceptionMappers
import com.anya.dogipedia.data.exception.api.ApiException.*
import com.anya.dogipedia.data.model.api.error.ApiError
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ApiExceptionMapper
@Inject constructor(
    private val moshi: Moshi
) : ExceptionMappers.Api {

    override fun map(throwable: Throwable): ApiException {
        var exception: ApiException = UnknownException(throwable.message)
        if (throwable is ConnectException || throwable is SocketTimeoutException || throwable is UnknownHostException) {
            exception = ServerException()
        }
        if (throwable is HttpException) {
            if (throwable.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                exception = NotFoundException()
            } else getExceptionFromResponse(throwable).let {
                exception = it
            }
        }
        return exception
    }

    private fun getExceptionFromResponse(httpException: HttpException): ApiException {
        val responseBody = httpException.response()?.errorBody()?.string()
        responseBody?.let { errorResponse ->
            val apiError = moshi.adapter(ApiError::class.java).fromJson(errorResponse)
            return when (apiError?.code) {
                404 -> NotFoundException()
                else -> UnknownException(apiError?.message)
            }
        }
        return UnknownException(null)
    }
}