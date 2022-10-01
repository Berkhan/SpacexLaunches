package com.berkhan.spacex.di.usecase

import com.berkhan.spacex.domain.repository.LaunchRepository
import com.berkhan.spacex.domain.usecase.GetLaunchListUseCase
import com.berkhan.spacex.domain.usecase.GetLaunchUseCase
import com.berkhan.spacex.domain.usecase.LaunchUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideLaunchUseCases(repository: LaunchRepository): LaunchUseCases = LaunchUseCases(
        getLaunchListUseCase = GetLaunchListUseCase(repository),
        getLaunchUseCase = GetLaunchUseCase(repository)
    )
}