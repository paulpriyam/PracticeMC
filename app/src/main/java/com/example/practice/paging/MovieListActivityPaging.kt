package com.example.practice.paging

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice.databinding.ActivityMovieListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListActivityPaging : AppCompatActivity() {

    private lateinit var binding: ActivityMovieListBinding
    private val viewModel: MovieListPagingViewModel by viewModels()
    val searchScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private lateinit var pagingAdapter: MovieListPagingDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pagingAdapter = MovieListPagingDataAdapter()
        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(this@MovieListActivityPaging)
            adapter = pagingAdapter
        }
        binding.svSearch.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.setSearchTerm(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.setSearchTerm(newText)
                }
                return true
            }

        })
        observe()
        setAdapterLoadListener()
    }


    private fun observe() {
        viewModel.searchResult.observe(this) {
            lifecycleScope.launch {
                pagingAdapter.submitData(it)
            }
        }
    }

    private fun setAdapterLoadListener() {
        pagingAdapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> {
                    binding.pbProgress.visibility = View.VISIBLE
                    binding.rvMovies.visibility = View.GONE
                    binding.grpError.visibility = View.GONE
                }

                is LoadState.NotLoading -> {
                    binding.pbProgress.visibility = View.GONE
                    if (pagingAdapter.itemCount == 0) {
                        binding.rvMovies.visibility = View.GONE
                        binding.grpError.visibility = View.VISIBLE
                        binding.tvError.text = "NO Items Found.."
                    } else {
                        binding.rvMovies.visibility = View.VISIBLE
                        binding.grpError.visibility = View.GONE
                    }
                }

                is LoadState.Error -> {
                    binding.pbProgress.visibility = View.GONE
                    binding.rvMovies.visibility = View.GONE
                    binding.grpError.visibility = View.VISIBLE
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        searchScope.cancel()
    }
}
