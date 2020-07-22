package me.davidcosta.tmdb.ui.highlight.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.davidcosta.tmdb.data.model.*
import me.davidcosta.tmdb.data.repository.MoviesRepository
import me.davidcosta.tmdb.data.repository.WatchlistRepository

class HighlightMovieViewModel(private val moviesRepository: MoviesRepository, private val watchlistRepository: WatchlistRepository) : ViewModel() {

    val similarMovies: LiveData<List<Media>> = MutableLiveData<List<Media>>()
    val movieDetails: LiveData<Movie> = MutableLiveData<Movie>()
    val credits: LiveData<Credits> = MutableLiveData<Credits>()
    val isOnWatchlist: LiveData<Boolean> = MutableLiveData(false)

    fun fetchSimilarMovies(movieId: Long) {
        CoroutineScope(Dispatchers.Main).launch {
            (similarMovies as MutableLiveData).apply {
                value = moviesRepository.similarMovies(movieId)
            }
        }
    }

    fun fetchCredits(movieId: Long) {
        CoroutineScope(Dispatchers.Main).launch {
            (credits as MutableLiveData).apply {
                value = moviesRepository.credits(movieId)
            }
        }
    }

    fun movieDetails(movieId: Long) {
        CoroutineScope(Dispatchers.Main).launch {
            (movieDetails as MutableLiveData).apply {
                value = moviesRepository.movieDetails(movieId)
            }
        }
    }

    fun addToWatchlist(media: Media) {
        watchlistRepository.addToWatchlist(media)
        (isOnWatchlist as MutableLiveData).apply {
            value = true
        }
    }



    fun removeFromWatchlist(media: Media) {
        watchlistRepository.removeFromWatchlist(media)
        (isOnWatchlist as MutableLiveData).apply {
            value = false
        }
    }


    fun isOnWatchlist(media: Media): Boolean {
        val isOnList = watchlistRepository.isOnWatchlist(media)
        (isOnWatchlist as MutableLiveData).apply {
            value = isOnList
        }
        return isOnList
    }

}