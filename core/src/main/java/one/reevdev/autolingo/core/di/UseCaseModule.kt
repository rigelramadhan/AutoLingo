package one.reevdev.autolingo.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import one.reevdev.autolingo.core.domain.usecase.ExerciseInteractor
import one.reevdev.autolingo.core.domain.usecase.ExerciseUseCase

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun provideExerciseUseCase(useCaseImpl: ExerciseInteractor): ExerciseUseCase
}