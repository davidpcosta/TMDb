package me.davidcosta.tmdb.ui.highlight.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.davidcosta.tmdb.data.model.*
import me.davidcosta.tmdb.data.repository.MoviesRepository
import me.davidcosta.tmdb.data.repository.WatchlistRepository

class HighlightMovieViewModel(private val moviesRepository: MoviesRepository, private val watchlistRepository: WatchlistRepository) : ViewModel() {

    lateinit var similarMovies: LiveData<List<Media>>
    lateinit var movieDetails: LiveData<Movie>
    lateinit var cast: LiveData<List<Cast>>
    val isOnWatchlist: LiveData<Boolean> = MutableLiveData(false)

    fun fetchSimilarMovies(movieId: Long) {
        similarMovies = moviesRepository.similarMovies(movieId)
    }

    fun fetchCredits(movieId: Long) {
        cast = moviesRepository.credits(movieId)
    }

    fun movieDetails(movieId: Long) {
        movieDetails = moviesRepository.movieDetails(movieId)
    }

    fun addToWatchlist(accountId: Long, sessionId: String?, media: Media): LiveData<WatchlistOperationResponse> {
        return watchlistRepository.addToWatchlist(accountId, sessionId, media)
    }

    fun removeFromWatchlist(accountId: Long, sessionId: String?, media: Media): LiveData<WatchlistOperationResponse> {
        return watchlistRepository.removeFromWatchlist(accountId, sessionId, media)
    }

    fun isOnWatchlist(media: Media) {
        isOnWatchlist as MutableLiveData
        isOnWatchlist.value = watchlistRepository.isInWatchlist(media)
    }
}