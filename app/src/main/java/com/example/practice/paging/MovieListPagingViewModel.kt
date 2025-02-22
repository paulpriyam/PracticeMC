package com.example.practice.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.practice.data.model.MovieItem
import com.example.practice.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class MovieListPagingViewModel @Inject constructor(private val repository: MovieRepository) :
    ViewModel() {

    val _searchTerm = MutableStateFlow("")


    val searchResult: Flow<PagingData<MovieItem>> = _searchTerm
        .debounce(500L)
        .distinctUntilChanged()
        .flatMapLatest {
            Pager(
                config = PagingConfig(
                    pageSize = 10,
                    initialLoadSize = 20,
                    enablePlaceholders = false,
                    prefetchDistance = 2
                ),
                pagingSourceFactory = { MovieListPagingSource(repository, it) }
            ).flow
        }.cachedIn(viewModelScope)

    fun setSearchTerm(term: String) {
        _searchTerm.value = term
    }

}