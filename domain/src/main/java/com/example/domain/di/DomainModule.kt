package com.example.domain.di

import com.example.data.di.DataModule
import com.example.data.repository.Repository
import com.example.domain.useCases.UseCase
import com.example.domain.useCases.UseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module(
    includes = [
        DataModule::class,
        DispatcherModule::class
    ]
)
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Singleton
    @Provides
    fun provideUseCase(
        repository: Repository,
        @IODispatcher
        dispatcher: CoroutineDispatcher
    ): UseCase = UseCaseImpl(
        repository = repository,
        dispatcher = dispatcher
    )
}