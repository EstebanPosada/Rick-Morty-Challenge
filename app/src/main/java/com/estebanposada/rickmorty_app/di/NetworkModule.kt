package com.estebanposada.rickmorty_app.di

import com.estebanposada.data.remote.ApiService
import com.estebanposada.rickmorty_app.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()


    @Singleton
    @Provides
    fun provideHttpLoginInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun provideClient(interceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder().addInterceptor(interceptor).build()

    @Singleton
    @Provides
    fun provideCharacterApi(client: OkHttpClient, factory: GsonConverterFactory): ApiService =
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(client).addConverterFactory(factory).build().create()
}
