package me.dio.android.copa2022app.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.dio.android.copa2022app.data.data.di.DataModule
import me.dio.android.copa2022app.data.local.di.LocalModule
import me.dio.android.copa2022app.data.remote.di.NetworkModule
import me.dio.android.copa2022app.data.remote.di.RemoteModule
import me.dio.android.copa2022app.data.remote.di.ServiceModule

@Module(
    includes = [
        DataModule::class,
        LocalModule::class,
        RemoteModule::class,
        NetworkModule::class,
        ServiceModule::class
    ]
)
@InstallIn(SingletonComponent::class)
abstract class ApplicationModule {

    @Binds
    abstract fun bindContext(application: Application): Context
}
