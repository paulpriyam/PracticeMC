package com.example.practice.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice.constant.Constants.API_KEY
import com.example.practice.data.model.MovieDetailResponse
import com.example.practice.usecase.MovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val useCase: MovieDetailsUseCase) : ViewModel() {



    private var _movie = MutableLiveData<MovieDetailResponse>()
    val movie: LiveData<MovieDetailResponse> get() = _movie

    fun getMovieById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = useCase.getMovieById(API_KEY, id)
            if (response.isSuccessful) {
                _movie.postValue(response.body())
            }
        }
    }
}