package com.example.practice.data.model

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
	val metascore: String? = null,
	val boxOffice: String? = null,
	val website: String? = null,
	val imdbRating: String? = null,
	val imdbVotes: String? = null,
	val ratings: List<RatingsItem?>? = null,
	val runtime: String? = null,
	val language: String? = null,
	val rated: String? = null,
	val production: String? = null,
	val released: String? = null,
	val imdbID: String? = null,
	val plot: String? = null,
	val director: String? = null,
	@SerializedName("Title")
	val title: String? = null,
	val actors: String? = null,
	val response: String? = null,
	@SerializedName("Type")
	val type: String? = null,
	val awards: String? = null,
	val dVD: String? = null,
	@SerializedName("Year")
	val year: String? = null,
	@SerializedName("Poster")
	val poster: String? = null,
	val country: String? = null,
	@SerializedName("Genre")
	val genre: String? = null,
	val writer: String? = null
)

data class RatingsItem(
	val value: String? = null,
	val source: String? = null
)

