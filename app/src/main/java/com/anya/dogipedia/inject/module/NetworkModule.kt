package com.anya.dogipedia.inject.module


import com.anya.dogipedia.BuildConfig
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        private const val NETWORK_TIMEOUT_SECONDS = 30L
        private const val TIMBER_NETWORK_TAG = "api_call"
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message -> Timber.tag(TIMBER_NETWORK_TAG).d(message) }
            .apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Provides
    @Singleton
    fun provideOkHttp(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                connectTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                readTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                writeTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                if (BuildConfig.DEBUG) {
                    addInterceptor(loggingInterceptor)
                }
            }.build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

}