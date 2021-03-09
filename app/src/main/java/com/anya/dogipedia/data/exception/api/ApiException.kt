package com.anya.dogipedia.data.exception.api

import java.io.IOException

sealed class ApiException(message: String?) : IOException(message) {

    class ServerException : ApiException(null)

    class NotFoundException : ApiException(null)

    class NetworkException : ApiException(null)

    class UnknownException(message: String?) : ApiException(message)
}
