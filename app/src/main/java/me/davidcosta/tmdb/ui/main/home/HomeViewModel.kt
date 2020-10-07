package me.davidcosta.tmdb.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.davidcosta.tmdb.data.repository.HomeRepository
import me.davidcosta.tmdb.data.vo.TitleVO

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val mUpcomingMovies: MutableLiveData<List<TitleVO>> = MutableLiveData()
    private val mLatestMovies: MutableLiveData<List<TitleVO>> = MutableLiveData()
    private val mTrending: MutableLiveData<List<TitleVO>> = MutableLiveData()
    private val mPopularMovies: MutableLiveData<List<TitleVO>> = MutableLiveData()
    private val mPopularTvs: MutableLiveData<List<TitleVO>> = MutableLiveData()

    val upcomingMovies: LiveData<List<TitleVO>>
        get() = mUpcomingMovies

    val latestMovies: LiveData<List<TitleVO>>
        get() = mLatestMovies

    val trending: LiveData<List<TitleVO>>
        get() = mTrending

    val popularMovies: LiveData<List<TitleVO>>
        get() = mPopularMovies

    val popularTvs: LiveData<List<TitleVO>>
        get() = mPopularTvs

    fun fetchTrending() {
        CoroutineScope(Dispatchers.Main).launch {
            mTrending.apply {
                value = homeRepository.trending()
            }
        }
    }

    fun fetchUpcomingMovies() {
        CoroutineScope(Dispatchers.Main).launch {
            mUpcomingMovies.apply {
                value = homeRepository.upcomingMovies()
            }
        }
    }

    fun fetchLatestMovies() {
        CoroutineScope(Dispatchers.Main).launch {
            mLatestMovies.apply {
                value = homeRepository.latestMovies()
            }
        }
    }

    fun fetchPopularMovies() {
        CoroutineScope(Dispatchers.Main).launch {
            mPopularMovies.apply {
                value = homeRepository.popularMovies()
            }
        }
    }

    fun fetchPopularTvs() {
        CoroutineScope(Dispatchers.Main).launch {
            mPopularTvs.apply {
                value = homeRepository.popularTvs()
            }
        }
    }
}