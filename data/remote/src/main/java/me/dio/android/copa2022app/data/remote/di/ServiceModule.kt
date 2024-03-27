package me.dio.android.copa2022app.data.remote.di

import dagger.Module
import dagger.Provides
import me.dio.android.copa2022app.data.remote.services.MatchesServices
import retrofit2.Retrofit
import retrofit2.create

@Module
class ServiceModule {

    @Provides
    fun providesAuthService(retrofit: Retrofit) = retrofit.create<MatchesServices>()
}
