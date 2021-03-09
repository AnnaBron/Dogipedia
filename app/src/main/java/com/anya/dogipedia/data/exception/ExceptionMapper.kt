package com.anya.dogipedia.data.exception

interface ExceptionMapper<out Exception : Throwable> {

    fun map(throwable: Throwable): Exception
}
