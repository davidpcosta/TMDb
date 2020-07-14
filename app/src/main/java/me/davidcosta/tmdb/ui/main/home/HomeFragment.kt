package me.davidcosta.tmdb.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main_fragment_home.view.*
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.extensions.hide
import me.davidcosta.tmdb.extensions.show

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var viewManagerMovies: LinearLayoutManager
    private lateinit var viewManagerTvs: LinearLayoutManager
    private lateinit var popularMoviesAdapter: MovieRecycleViewAdapter
    private lateinit var popularTvsAdapter: MovieRecycleViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_main_fragment_home, container, false)

        homeViewModel = ViewModelProvider(this, HomeViewModelFactory()).get(HomeViewModel::class.java)
        viewManagerMovies = LinearLayoutManager(requireActivity().applicationContext).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        viewManagerTvs = LinearLayoutManager(requireActivity().applicationContext).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }

        // Movies
        popularMoviesAdapter = MovieRecycleViewAdapter(requireContext())
        view.fragment_home_popular_movies.apply {
            layoutManager = viewManagerMovies
            adapter = popularMoviesAdapter
        }

        // TVs
        popularTvsAdapter = MovieRecycleViewAdapter(requireContext())
        view.fragment_home_popular_tvs.apply {
            layoutManager = viewManagerTvs
            adapter = popularTvsAdapter
        }

        fetchPopularMovies()
        fetchPopularTvs()
        return view
    }

    private fun fetchPopularMovies() {
        homeViewModel.fetchPopularMovies()
        homeViewModel.popularMovies.observe(viewLifecycleOwner, Observer { movies ->
            popularMoviesAdapter.medias = movies
            popularMoviesAdapter.notifyDataSetChanged()

            view?.fragment_home_popular_movies_loading?.hide()
            view?.fragment_home_popular_movies?.show()

        })
    }

    private fun fetchPopularTvs() {
        homeViewModel.fetchPopularTvs()
        homeViewModel.popularTvs.observe(viewLifecycleOwner, Observer { tvs ->
            popularTvsAdapter.medias = tvs
            popularTvsAdapter.notifyDataSetChanged()

            view?.fragment_home_popular_tvs_loading?.hide()
            view?.fragment_home_popular_tvs?.show()

        })
    }
}
