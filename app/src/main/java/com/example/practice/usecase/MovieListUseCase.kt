package com.example.practice.usecase

import com.example.practice.repository.MovieRepository
import javax.inject.Inject

class MovieListUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend fun getMovieList(apikey: String, searchTerm: String,pageNumber:Int) =
        repository.getMovieList(apikey, searchTerm,pageNumber)
}