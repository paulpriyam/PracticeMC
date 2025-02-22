package com.example.practice.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.practice.constant.Constants
import com.example.practice.data.model.MovieItem
import com.example.practice.repository.MovieRepository

class MovieListPagingSource(
    private val repository: MovieRepository,
    private val searchTerm: String
) : PagingSource<Int, MovieItem>() {
    override fun getRefreshKey(state: PagingState<Int, MovieItem>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1) ?: state.closestPageToPosition(
                position
            )?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {
        return try {
            val currPage = params.key ?: 1
            val response =
                repository.getMovieList(Constants.API_KEY, searchTerm, page = currPage)
            LoadResult.Page(
                data = response.body()?.movies ?: emptyList(),
                prevKey = if (currPage == 1) null else currPage.minus(1),
                nextKey = if (response.body()?.movies.isNullOrEmpty()) null else currPage.plus(1)
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}