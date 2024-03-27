package me.dio.android.copa2022app.data.remote.di

import dagger.Binds
import dagger.Module
import me.dio.android.copa2022app.data.data.source.MatchesDataSource
import me.dio.android.copa2022app.data.remote.source.MatchDataSourceRemote

@Module
interface RemoteModule {

    @Binds
    fun providesMatchDataSourceRemote(impl: MatchDataSourceRemote): MatchesDataSource.Remote
}
