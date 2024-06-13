package one.reevdev.autolingo.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import one.reevdev.autolingo.core.data.repository.ExerciseRepository
import one.reevdev.autolingo.core.data.repository.IExerciseRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun provideExerciseRepository(repository: ExerciseRepository): IExerciseRepository
}