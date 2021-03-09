package com.anya.dogipedia.utils.exception

interface ExceptionMessageProvider<in Exception> {

    fun getMessage(exception: Exception): String

    fun getMessage(throwable: Throwable): String
}
