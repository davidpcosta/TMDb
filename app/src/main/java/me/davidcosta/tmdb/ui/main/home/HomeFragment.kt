package me.davidcosta.tmdb.ui.main.home

import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.view.*
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.base.BaseFragment
import me.davidcosta.tmdb.enums.MediaType
import me.davidcosta.tmdb.extensions.hide
import me.davidcosta.tmdb.extensions.show

class HomeFragment : BaseFragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var upcomingMoviesViewManager: LinearLayoutManager
    private lateinit var upcomingMoviesAdapter: MediaRecycleViewAdapter

    private lateinit var latestMoviesViewManager: LinearLayoutManager
    private lateinit var latestMoviesAdapter: MediaRecycleViewAdapter

    private lateinit var trendingViewManager: LinearLayoutManager
    private lateinit var trendingAdapter: MediaRecycleViewAdapter

    private lateinit var popularMoviesViewManager: LinearLayoutManager
    private lateinit var popularMoviesAdapter: MediaRecycleViewAdapter

    private lateinit var popularTvsViewManager: LinearLayoutManager
    private lateinit var popularTvsAdapter: MediaRecycleViewAdapter

    override fun resourceView() =
        R.layout.fragment_home

    override fun toolbarResourceView() =
        R.layout.toolbar_home

    override fun setupView(view: View) {

        homeViewModel = ViewModelProvider(this, HomeViewModelFactory()).get(HomeViewModel::class.java)

        // Upcoming Movies Rail
        upcomingMoviesViewManager = LinearLayoutManager(requireActivity().applicationContext).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        upcomingMoviesAdapter = MediaRecycleViewAdapter(requireContext())
        view.fragment_home_upcoming.apply {
            layoutManager = upcomingMoviesViewManager
            adapter = upcomingMoviesAdapter
        }

        // Latest Movies Rail
        latestMoviesViewManager = LinearLayoutManager(requireActivity().applicationContext).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        latestMoviesAdapter = MediaRecycleViewAdapter(requireContext())
        view.fragment_home_latest.apply {
            layoutManager = latestMoviesViewManager
            adapter = latestMoviesAdapter
        }

        // Trending Rail
        trendingViewManager = LinearLayoutManager(requireActivity().applicationContext).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        trendingAdapter = MediaRecycleViewAdapter(requireContext())
        view.fragment_home_trending.apply {
            layoutManager = trendingViewManager
            adapter = trendingAdapter
        }

        // Popular Movies Rail
        popularMoviesViewManager = LinearLayoutManager(requireActivity().applicationContext).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        popularMoviesAdapter = MediaRecycleViewAdapter(requireContext())
        view.fragment_home_popular_movies.apply {
            layoutManager = popularMoviesViewManager
            adapter = popularMoviesAdapter
        }

        // Popular TVs Rail
        popularTvsViewManager = LinearLayoutManager(requireActivity().applicationContext).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        popularTvsAdapter = MediaRecycleViewAdapter(requireContext())
        view.fragment_home_popular_tvs.apply {
            layoutManager = popularTvsViewManager
            adapter = popularTvsAdapter
        }

        fetchUpcomingMoviesAndAddObserver()
        fetchLatestMoviesAndAddObserver()
        fetchTrendingAndAddObserver()
        fetchPopularMoviesAndAddObserver()
        fetchPopularTvsAndAddObserver()
    }

    private fun fetchUpcomingMoviesAndAddObserver() {
        homeViewModel.fetchUpcomingMovies()
        homeViewModel.upcomingMovies.observe(viewLifecycleOwner, Observer { medias ->
            upcomingMoviesAdapter.titles = medias
            upcomingMoviesAdapter.notifyDataSetChanged()
            view?.fragment_home_upcoming_loading?.hide()
            view?.fragment_home_upcoming?.show()
        })
    }

    private fun fetchLatestMoviesAndAddObserver() {
        homeViewModel.fetchLatestMovies()
        homeViewModel.latestMovies.observe(viewLifecycleOwner, Observer { titles ->
            latestMoviesAdapter.titles = titles
            latestMoviesAdapter.notifyDataSetChanged()
            view?.fragment_home_latest_loading?.hide()
            view?.fragment_home_latest?.show()
        })
    }

    private fun fetchTrendingAndAddObserver() {
        homeViewModel.fetchTrending()
        homeViewModel.trending.observe(viewLifecycleOwner, Observer { titles ->
            trendingAdapter.titles = titles
            trendingAdapter.notifyDataSetChanged()
            view?.fragment_home_trending_loading?.hide()
            view?.fragment_home_trending?.show()
        })
    }

    private fun fetchPopularMoviesAndAddObserver() {
        homeViewModel.fetchPopularMovies()
        homeViewModel.popularMovies.observe(viewLifecycleOwner, Observer { movies ->
            popularMoviesAdapter.titles = movies
            popularMoviesAdapter.notifyDataSetChanged()
            view?.fragment_home_popular_movies_loading?.hide()
            view?.fragment_home_popular_movies?.show()
        })
    }

    private fun fetchPopularTvsAndAddObserver() {
        homeViewModel.fetchPopularTvs()
        homeViewModel.popularTvs.observe(viewLifecycleOwner, Observer { tvs ->
            popularTvsAdapter.titles = tvs
            popularTvsAdapter.notifyDataSetChanged()
            view?.fragment_home_popular_tvs_loading?.hide()
            view?.fragment_home_popular_tvs?.show()
        })
    }
}
