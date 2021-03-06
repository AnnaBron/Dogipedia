package com.anya.dogipedia.inject.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anya.dogipedia.inject.viewmodel.ViewModelFactory
import com.anya.dogipedia.ui.breeds.BreedsViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent


@Module
@InstallIn(FragmentComponent::class)
abstract class ViewModelModule {

    @Binds
    abstract fun bindBreedsViewModel(
        breedsViewModel: BreedsViewModel
    ): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
