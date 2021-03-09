package com.anya.dogipedia.data.exception

import com.anya.dogipedia.data.exception.api.ApiException

interface ExceptionMappers {
    interface Api : ExceptionMapper<ApiException>
}
