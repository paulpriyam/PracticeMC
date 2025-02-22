package com.example.practice.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practice.data.model.MovieItem
import com.example.practice.databinding.LayoutMovieItemBinding

class MovieListPagingDataAdapter :
    PagingDataAdapter<MovieItem, MovieListPagingDataAdapter.MovieItemViewHolder>(DIFF_CALLBACK) {


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieItem>() {
            override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
                return oldItem.imdbID == newItem.imdbID
            }

            override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class MovieItemViewHolder(private val binding: LayoutMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MovieItem) {
            binding.tvTitleValue.text = data.title
            binding.tvIDValue.text = data.imdbID
            binding.tvTypeValue.text = data.type
            binding.tvYearValue.text = data.year
            Glide.with(binding.ivImage.context)
                .load(data.poster)
                .into(binding.ivImage)
        }
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let{
            holder.bind(movie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val binding =
            LayoutMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieItemViewHolder(binding)
    }


}