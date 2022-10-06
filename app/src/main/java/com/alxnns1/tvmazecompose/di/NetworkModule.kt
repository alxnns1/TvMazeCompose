package com.alxnns1.tvmazecompose.di

import android.content.Context
import com.alxnns1.tvmazecompose.network.TvMazeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration

@Module
@InstallIn(ViewModelComponent::class)
class NetworkModule {

    @Provides
    fun provideTvMazeService(@ApplicationContext appContext: Context): TvMazeService {
        val client = OkHttpClient().newBuilder().connectTimeout(Duration.ofMinutes(1))
            .cache(Cache(appContext.cacheDir, 5 * 1024 * 1024))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
        return Retrofit.Builder()
            .baseUrl("https://api.tvmaze.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(TvMazeService::class.java)
    }
}