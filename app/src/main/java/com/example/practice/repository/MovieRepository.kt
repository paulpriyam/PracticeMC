package com.example.practice.repository

import com.example.practice.data.api.MovieApi
import javax.inject.Inject

class MovieRepository @Inject constructor(private val api: MovieApi) {

    suspend fun getMovieList(apiKey: String, searchTerm: String, page: Int) =
        api.getMovieList(apiKey, searchTerm, page = page)


    suspend fun getMovieById(apiKey: String, id: String) = api.getMovieById(apiKey, id)
}