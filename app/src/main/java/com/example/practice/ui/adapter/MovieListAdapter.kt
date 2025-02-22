package com.example.practice.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.practice.data.model.MovieItem
import com.example.practice.databinding.LayoutMovieItemBinding

class MovieListAdapter(private val onclick: (String) -> Unit) :
    RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {
    private val movieList: ArrayList<MovieItem> = arrayListOf()

    inner class MovieViewHolder(private val binding: LayoutMovieItemBinding) :
        ViewHolder(binding.root) {
        fun bind(data: MovieItem) {
            binding.tvTitleValue.text = data.title
            binding.tvIDValue.text = data.imdbID
            binding.tvTypeValue.text = data.type
            binding.tvYearValue.text = data.year
            Glide.with(binding.ivImage.context)
                .load(data.poster)
                .into(binding.ivImage)

            binding.root.setOnClickListener {
                onclick.invoke(data.imdbID.orEmpty())
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            LayoutMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = movieList[position]
        holder.bind(item)
    }

    fun submitList(list: List<MovieItem>) {
        movieList.clear()
        movieList.addAll(list)
        notifyDataSetChanged()
    }
}