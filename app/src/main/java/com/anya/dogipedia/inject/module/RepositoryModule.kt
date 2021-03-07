package com.anya.dogipedia.inject.module

import com.anya.dogipedia.data.domain.DogsRepository
import com.anya.dogipedia.data.domain.Repositories
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindDogsRepository(dogsRepository: DogsRepository): Repositories.DogsRepository
}
