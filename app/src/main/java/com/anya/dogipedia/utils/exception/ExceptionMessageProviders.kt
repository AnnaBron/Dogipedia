package com.anya.dogipedia.utils.exception

import com.anya.dogipedia.data.exception.api.ApiException

interface ExceptionMessageProviders {

    interface Api : ExceptionMessageProvider<ApiException>

}
