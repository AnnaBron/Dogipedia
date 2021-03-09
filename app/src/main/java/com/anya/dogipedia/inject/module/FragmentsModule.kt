package com.anya.dogipedia.inject.module

import androidx.fragment.app.FragmentFactory
import com.anya.dogipedia.ui.MainFragmentFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
abstract class FragmentsModule {

    @Binds
    abstract fun bindFragmentFactory(factory: MainFragmentFactory): FragmentFactory
}