package com.anya.dogipedia.inject.module


import com.anya.dogipedia.data.exception.ExceptionMappers
import com.anya.dogipedia.data.exception.api.ApiExceptionMapper
import com.anya.dogipedia.utils.exception.ExceptionMessageProviders
import com.anya.dogipedia.utils.exception.api.ApiExceptionMessageProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class ExceptionModule {

    @Binds
    abstract fun bindApiExceptionMapper(
        apiExceptionMapper: ApiExceptionMapper
    ): ExceptionMappers.Api

    @Binds
    abstract fun bindApiExceptionMessageProvider(
        apiExceptionMessageProvider: ApiExceptionMessageProvider
    ): ExceptionMessageProviders.Api

}
