package com.example.practice.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MovieListResponse(
	@SerializedName("Response")
	val response: String? = null,
	val totalResults: String? = null,
	@SerializedName("Search")
	val movies: List<MovieItem> = emptyList()
)
@Keep
data class MovieItem(
	@SerializedName("Type")
	val type: String? = null,
	@SerializedName("Year")
	val year: String? = null,
	val imdbID: String? = null,
	@SerializedName("Poster")
	val poster: String? = null,
	@SerializedName("Title")
	val title: String? = null
)

