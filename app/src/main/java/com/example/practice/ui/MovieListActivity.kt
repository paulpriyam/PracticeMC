package com.example.practice.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practice.R
import com.example.practice.databinding.ActivityMovieListBinding
import com.example.practice.ui.adapter.MovieListAdapter
import com.example.practice.viewmodel.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieListBinding
    private val viewModel: MovieListViewModel by viewModels()
    private lateinit var movieListAdapter: MovieListAdapter

    private val searchScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        movieListAdapter = MovieListAdapter {
            redirectToDetailsScreen(it)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(this@MovieListActivity)
            adapter = movieListAdapter
        }
        subscribe()
        observe()
    }

    private fun observe() {
        viewModel.movieList.observe(this) {
            Log.d("--->MLA", it.toString())
            Log.d("--->MLA", it.movies?.size.toString())
            if (!it.movies.isNullOrEmpty()) {
                movieListAdapter.submitList(it.movies)
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun subscribe() {

        val queryFlow = MutableStateFlow("")
        searchScope.launch {
            queryFlow
                .debounce(500)
                .distinctUntilChanged()
                .collectLatest {searchTerm->
                    if(searchTerm.isEmpty()){
                        movieListAdapter.submitList(emptyList())
                    }else{
                        viewModel.searchMovies(searchTerm)
                    }
                }
        }
        binding.svSearch.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchMovies(query.orEmpty())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // not required
                searchScope.launch {
                    queryFlow.debounce(300L).distinctUntilChanged()

                }
                return true
            }

        })
        binding.svSearch.setOnCloseListener {
            Log.d("--->MLA", "CLOSE")
            movieListAdapter.submitList(emptyList())
            false
        }
    }

    private fun redirectToDetailsScreen(id: String) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        val bundle = Bundle().apply {
            putString("ID", id)
        }
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        searchScope.cancel()
    }
}