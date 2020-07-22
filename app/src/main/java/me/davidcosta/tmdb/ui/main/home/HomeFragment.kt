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
    private lateinit var viewManagerUpcoming: LinearLayoutManager
    private lateinit var viewManagerTrending: LinearLayoutManager
    private lateinit var viewManagerMovies: LinearLayoutManager
    private lateinit var viewManagerTvs: LinearLayoutManager
    private lateinit var upcomingAdapter: MediaRecycleViewAdapter
    private lateinit var trendingAdapter: MediaRecycleViewAdapter
    private lateinit var popularMoviesAdapter: MediaRecycleViewAdapter
    private lateinit var popularTvsAdapter: MediaRecycleViewAdapter

    override fun resourceView() =
        R.layout.fragment_home

    override fun setupView(view: View) {

        // Upcoming Rail
        homeViewModel = ViewModelProvider(this, HomeViewModelFactory()).get(HomeViewModel::class.java)
        viewManagerUpcoming = LinearLayoutManager(requireActivity().applicationContext).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        upcomingAdapter = MediaRecycleViewAdapter(requireContext())
        view.fragment_home_upcoming.apply {
            layoutManager = viewManagerUpcoming
            adapter = upcomingAdapter
        }

        // Trending Rail
        homeViewModel = ViewModelProvider(this, HomeViewModelFactory()).get(HomeViewModel::class.java)
        viewManagerTrending = LinearLayoutManager(requireActivity().applicationContext).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        trendingAdapter = MediaRecycleViewAdapter(requireContext())
        view.fragment_home_trending.apply {
            layoutManager = viewManagerTrending
            adapter = trendingAdapter
        }

        // Popular Movies Rail
        homeViewModel = ViewModelProvider(this, HomeViewModelFactory()).get(HomeViewModel::class.java)
        viewManagerMovies = LinearLayoutManager(requireActivity().applicationContext).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        popularMoviesAdapter = MediaRecycleViewAdapter(requireContext())
        view.fragment_home_popular_movies.apply {
            layoutManager = viewManagerMovies
            adapter = popularMoviesAdapter
        }

        // Popular TVs Rail
        viewManagerTvs = LinearLayoutManager(requireActivity().applicationContext).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        popularTvsAdapter = MediaRecycleViewAdapter(requireContext())
        view.fragment_home_popular_tvs.apply {
            layoutManager = viewManagerTvs
            adapter = popularTvsAdapter
        }

        fetchUpcomingAndAddObserver()
        fetchTrendingAndAddObserver()
        fetchPopularMoviesAndAddObserver()
        fetchPopularTvsAndAddObserver()
    }

    private fun fetchUpcomingAndAddObserver() {
        homeViewModel.fetchUpcomingMovies()
        homeViewModel.upcomingMovies.observe(viewLifecycleOwner, Observer { medias ->
            upcomingAdapter.medias = medias
                .filter { media ->
                    media.getMediaType != MediaType.PERSON
                }
            upcomingAdapter.notifyDataSetChanged()
            view?.fragment_home_upcoming_loading?.hide()
            view?.fragment_home_upcoming?.show()
        })
    }

    private fun fetchTrendingAndAddObserver() {
        homeViewModel.fetchTrending()
        homeViewModel.trending.observe(viewLifecycleOwner, Observer { medias ->
            trendingAdapter.medias = medias
                .filter { media ->
                    media.getMediaType != MediaType.PERSON
                }
            trendingAdapter.notifyDataSetChanged()
            view?.fragment_home_trending_loading?.hide()
            view?.fragment_home_trending?.show()
        })
    }

    private fun fetchPopularMoviesAndAddObserver() {
        homeViewModel.fetchPopularMovies()
        homeViewModel.popularMovies.observe(viewLifecycleOwner, Observer { movies ->
            popularMoviesAdapter.medias = movies
            popularMoviesAdapter.notifyDataSetChanged()
            view?.fragment_home_popular_movies_loading?.hide()
            view?.fragment_home_popular_movies?.show()
        })
    }

    private fun fetchPopularTvsAndAddObserver() {
        homeViewModel.fetchPopularTvs()
        homeViewModel.popularTvs.observe(viewLifecycleOwner, Observer { tvs ->
            popularTvsAdapter.medias = tvs
            popularTvsAdapter.notifyDataSetChanged()
            view?.fragment_home_popular_tvs_loading?.hide()
            view?.fragment_home_popular_tvs?.show()
        })
    }
}
