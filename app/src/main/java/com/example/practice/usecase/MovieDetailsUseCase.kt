package com.example.practice.usecase

import com.example.practice.repository.MovieRepository
import javax.inject.Inject

class MovieDetailsUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend fun getMovieById(apiKey: String, id: String) = repository.getMovieById(apiKey, id)
}