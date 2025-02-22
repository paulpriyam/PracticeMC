package com.example.practice.data.api

import com.example.practice.data.model.MovieDetailResponse
import com.example.practice.data.model.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("/")
    suspend fun getMovieList(
        @Query("apikey") apiKey: String,
        @Query("s") searchTerm: String,
        @Query("page") page:Int
    ): Response<MovieListResponse>

    @GET("/")
    suspend fun getMovieById(
        @Query("apikey") apiKey: String,
        @Query("i") id: String
    ): Response<MovieDetailResponse>
}