package com.anya.dogipedia.data.service.interceptors

import com.anya.dogipedia.data.exception.api.ApiExceptionMapper
import com.anya.dogipedia.data.model.api.error.ApiError
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class ExceptionInterceptor @Inject constructor(
    private val exceptionMapper: ApiExceptionMapper,
    private val moshi: Moshi

): Interceptor {
    @Throws(Exception::class, HttpException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        try {
            val response = chain.proceed(request)
            val bodyString = response.body!!.string()
            if (!response.isSuccessful){
                 moshi.adapter(ApiError::class.java).fromJson(bodyString).let { responseObj ->
                     throw IOException(responseObj?.message)
                 }
            }
            return response.newBuilder()
                .body(bodyString.toResponseBody(response.body?.contentType()))
                .build()
        } catch (e: IOException) {
            // This catch generic errors not api response related.
            e.printStackTrace()
            throw exceptionMapper.map(e)
        }
    }
}