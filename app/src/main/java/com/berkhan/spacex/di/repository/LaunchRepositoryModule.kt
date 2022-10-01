package com.berkhan.spacex.di.repository

import com.berkhan.spacex.data.repository.LaunchRepositoryImpl
import com.berkhan.spacex.domain.repository.LaunchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class LaunchRepositoryModule {
    @Binds
    @ViewModelScoped
    abstract fun bindLaunchRepository(launchRepositoryImpl: LaunchRepositoryImpl): LaunchRepository
}