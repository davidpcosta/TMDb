package me.davidcosta.tmdb.ui.highlight

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.davidcosta.tmdb.data.model.Cast
import me.davidcosta.tmdb.data.model.Movie
import me.davidcosta.tmdb.data.model.MovieDetails
import me.davidcosta.tmdb.data.model.WatchlistOperationResponse
import me.davidcosta.tmdb.data.repository.MoviesRepository
import me.davidcosta.tmdb.data.repository.WatchlistRepository

class HighlightViewModel(private val moviesRepository: MoviesRepository, private val watchlistRepository: WatchlistRepository) : ViewModel() {

    lateinit var similarMovies: LiveData<List<Movie>>
    lateinit var movieDetails: LiveData<MovieDetails>
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

    fun addToWatchlist(accountId: Long, sessionId: String?, movie: Movie): LiveData<WatchlistOperationResponse> {
        return watchlistRepository.addToWatchlist(accountId, sessionId, movie)
    }

    fun removeFromWatchlist(accountId: Long, sessionId: String?, movie: Movie): LiveData<WatchlistOperationResponse> {
        return watchlistRepository.removeFromWatchlist(accountId, sessionId, movie)
    }

    fun isOnWatchlist(movie: Movie) {
        isOnWatchlist as MutableLiveData
        isOnWatchlist.value = watchlistRepository.isInWatchlist(movie)
    }
}