package com.anya.dogipedia.inject.module
//
import android.app.Application
import android.content.Context
import com.anya.dogipedia.DogsApp
import com.anya.dogipedia.inject.qualifier.AppContext
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    @AppContext
    fun provideAppContext(): Context = DogsApp.instance

}