package com.example.practice.di

import com.example.practice.data.api.MovieApi
import com.example.practice.repository.MovieRepository
import com.example.practice.usecase.MovieDetailsUseCase
import com.example.practice.usecase.MovieListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class MovieModule {
    private val BASE_URL = "https://www.omdbapi.com"

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    fun provideMovieRepository(api: MovieApi) = MovieRepository(api)

    @Provides
    fun provideMovieDetailsUseCase(repository: MovieRepository) = MovieDetailsUseCase(repository)

    @Provides
    fun provideMovieListUseCase(repository: MovieRepository) = MovieListUseCase(repository)


}