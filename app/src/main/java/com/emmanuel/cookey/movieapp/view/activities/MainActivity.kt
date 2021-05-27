package com.emmanuel.cookey.movieapp.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.emmanuel.cookey.movieapp.BaseActivity
import com.emmanuel.cookey.movieapp.R
import com.emmanuel.cookey.movieapp.databinding.MainActivityBinding
import com.emmanuel.cookey.movieapp.data.model.Movie
import com.emmanuel.cookey.movieapp.util.Resource
import com.emmanuel.cookey.movieapp.util.action
import com.emmanuel.cookey.movieapp.util.snack
import com.emmanuel.cookey.movieapp.view.adapters.MovieListAdapter
import com.emmanuel.cookey.movieapp.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }

    private lateinit var binding: MainActivityBinding

    private val viewModel: MainViewModel by viewModels()


    override fun getToolbarInstance(): Toolbar? {
        return toolbar
    }

    private val adapterMovie = MovieListAdapter(mutableListOf()) { movie -> gotoDetailActivity(movie) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.onManualRefresh()
        }

        initializedRecyclerview()

        populateRecyclerView()

        monitorEventForError()

    }


    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }


    private fun initializedRecyclerview(){

        binding.moviesRecyclerView.apply {
            adapter = adapterMovie
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            setHasFixedSize(true)
        }

    }

    private fun populateRecyclerView(){


        lifecycleScope.launchWhenStarted{
            viewModel.movies.collect {

                val result = it ?: return@collect

                result.data?.let { listOfMovie ->
                    adapterMovie.setMovies(listOfMovie) {
                        if (viewModel.pendingScrollToTopAfterRefresh) {
                            binding.moviesRecyclerView.scrollToPosition(0)
                            viewModel.pendingScrollToTopAfterRefresh = false
                        }
                    }
                }

                binding.swipeRefreshLayout.isRefreshing = result is Resource.Loading && result.data.isNullOrEmpty()
                binding.moviesRecyclerView.isVisible = !result.data.isNullOrEmpty()
                //binding.progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
                binding.textViewError.isVisible = result.error != null && result.data.isNullOrEmpty()

                binding.textViewError.text = getString(
                    R.string.could_not_refresh,
                    result.error?.localizedMessage
                        ?: getString(R.string.unknown_error_occurred)
                )


            }
        }

    }


    private fun monitorEventForError() {

        lifecycleScope.launchWhenStarted {
            viewModel.events.collect { event ->
                when (event) {
                    is MainViewModel.Event.ShowErrorMessage  -> {

                        val error = getString(R.string.could_not_refresh,
                            event.error.localizedMessage ?:
                            getString(R.string.unknown_error_occurred)
                        )

                        mainLayout.snack((error), Snackbar.LENGTH_LONG) {

                        }

                    }
                }
            }
        }

    }


    private fun gotoDetailActivity(movie: Movie) {
        val intent = Intent(this@MainActivity, DetailsActivity::class.java)
        intent.putExtra(INTENT_LIST_KEY, movie)
        startActivity(intent)


    }




    companion object {
        const val INTENT_LIST_KEY = "movie"
    }
}