package me.dio.android.copa2022app.data.remote.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    private val url = "https://digitalinnovationone.github.io/copa-2022-android/"
//    private val url = "https://digitalinnovationone.github.io/copa-2022-android/error/" // snackbar test

    @Provides
    @Singleton
    fun providesGsonBuilder(): Gson =
        GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(gson: Gson): Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(url)

    @Provides
    @Singleton
    fun providesOkHttpBuilder(
        interceptor: Interceptor
    ) = OkHttpClient.Builder().addInterceptor(interceptor)

    @Provides
    @Singleton
    fun providesRetrofit(
        retrofitBuilder: Retrofit.Builder,
        okHttpClientBuilder: OkHttpClient.Builder
    ): Retrofit = retrofitBuilder.client(okHttpClientBuilder.build()).build()

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}
