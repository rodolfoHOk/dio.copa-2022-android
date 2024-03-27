package me.dio.android.copa2022app.data.data.di

import dagger.Binds
import dagger.Module
import me.dio.android.copa2022app.data.data.repository.MatchesRepositoryImpl
import me.dio.android.copa2022app.domain.repositories.MatchesRepository

@Module
interface DataModule {

    @Binds
    fun providesMatchesRepository(impl: MatchesRepositoryImpl): MatchesRepository
}
