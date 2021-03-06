package com.anya.dogipedia.inject.module

import com.anya.dogipedia.BuildConfig
import com.anya.dogipedia.data.service.ApiService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.migration.OptionalInject
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideRetrofit(okhttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(BuildConfig.BASE_API_URL)
            client(okhttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
            addConverterFactory(MoshiConverterFactory.create(moshi))
        }.build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
