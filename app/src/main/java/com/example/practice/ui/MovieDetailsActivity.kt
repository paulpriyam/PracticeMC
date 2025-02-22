package com.example.practice.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.practice.databinding.ActivityMovieDetailBinding
import com.example.practice.viewmodel.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private val viewModel: MovieDetailsViewModel by viewModels()
    private val id by lazy { intent?.getStringExtra("ID").orEmpty() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getMovieDetails()
        observe()
    }

    private fun getMovieDetails() {
        viewModel.getMovieById(id)
    }

    private fun observe() {
        viewModel.movie.observe(this) {
            binding.apply {
                Glide.with(this@MovieDetailsActivity)
                    .load(it.poster.orEmpty())
                    .into(ivImage)
                tvTitleValue.text = it.title
                tvIDValue.text = it.imdbID
                tvYearValue.text = it.year
                tvTypeValue.text = it.genre
            }
        }
    }
}