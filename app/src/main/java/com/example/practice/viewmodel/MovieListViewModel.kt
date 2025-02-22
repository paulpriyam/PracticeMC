package com.example.practice.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practice.constant.Constants.API_KEY
import com.example.practice.data.model.MovieItem
import com.example.practice.data.model.MovieListResponse
import com.example.practice.usecase.MovieListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val useCase: MovieListUseCase) : ViewModel() {


    val queryFlow = MutableStateFlow("")


    val movieList: Flow<List<MovieItem>> = queryFlow
        .debounce(500L)
        .distinctUntilChanged()
        .mapLatest {
            useCase.getMovieList(API_KEY, it, 1).body()?.movies ?: emptyList()
        }.flowOn(Dispatchers.IO)
        .catch {
            emit(emptyList())
        }


    fun setSearchTerm(term: String) {
        queryFlow.value = term
    }
}