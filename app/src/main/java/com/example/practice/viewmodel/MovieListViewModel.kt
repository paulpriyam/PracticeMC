package com.example.practice.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice.constant.Constants.API_KEY
import com.example.practice.data.model.MovieListResponse
import com.example.practice.usecase.MovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val useCase: MovieListUseCase) : ViewModel() {



    private var _movieList = MutableLiveData<MovieListResponse>()
    val movieList: LiveData<MovieListResponse> get() = _movieList

    fun searchMovies(searchTerm: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = useCase.getMovieList(API_KEY, searchTerm,1)
            if (response.isSuccessful) {
                Log.d("--->VM", response.body().toString())
                _movieList.postValue(response.body())
            }
        }
    }
}