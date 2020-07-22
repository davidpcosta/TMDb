package me.davidcosta.tmdb.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.davidcosta.tmdb.data.model.Media
import me.davidcosta.tmdb.data.repository.MoviesRepository
import me.davidcosta.tmdb.data.repository.TvsRepository

class HomeViewModel(
    private val moviesRepository: MoviesRepository,
    private val tvsRepository: TvsRepository
) : ViewModel() {

    private val mUpcomingMovies: MutableLiveData<List<Media>> = MutableLiveData()
    private val mTrending: MutableLiveData<List<Media>> = MutableLiveData()
    private val mPopularMovies: MutableLiveData<List<Media>> = MutableLiveData()
    private val mPopularTvs: MutableLiveData<List<Media>> = MutableLiveData()

    val upcomingMovies: LiveData<List<Media>>
        get() = mUpcomingMovies

    val trending: LiveData<List<Media>>
        get() = mTrending

    val popularMovies: LiveData<List<Media>>
        get() = mPopularMovies

    val popularTvs: LiveData<List<Media>>
        get() = mPopularTvs

    fun fetchTrending() {
        CoroutineScope(Dispatchers.Main).launch {
            mTrending.apply {
                value = moviesRepository.trending()
            }
        }
    }

    fun fetchUpcomingMovies() {
        CoroutineScope(Dispatchers.Main).launch {
            mUpcomingMovies.apply {
                value = moviesRepository.upcoming()
            }
        }
    }

    fun fetchPopularMovies() {
        CoroutineScope(Dispatchers.Main).launch {
            mPopularMovies.apply {
                value = moviesRepository.popularMovies()
            }
        }
    }

    fun fetchPopularTvs() {
        CoroutineScope(Dispatchers.Main).launch {
            mPopularTvs.apply {
                value = tvsRepository.popularTvs()
            }
        }
    }
}