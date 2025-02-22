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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val useCase: MovieDetailsUseCase) :
    ViewModel() {


    private var _movie = MutableStateFlow<MovieDetailResponse?>(null)
    val movie: StateFlow<MovieDetailResponse?> get() = _movie.asStateFlow()

    fun getMovieById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = useCase.getMovieById(API_KEY, id)
            if (response.isSuccessful) {
                _movie.emit(response.body())
            }
        }
    }
}