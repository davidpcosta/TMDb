package me.davidcosta.tmdb.ui.main.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.davidcosta.tmdb.data.repository.WatchlistRepository
import me.davidcosta.tmdb.data.model.Movie

class WatchlistViewModel(private val watchlistRepository: WatchlistRepository) : ViewModel() {

    lateinit var movies: LiveData<List<Movie>>

    fun fetchWatchlist(accountId: Long, sessionId: String) {
        movies = watchlistRepository.watchlist(accountId, sessionId)
    }

    fun updateFromLocalWatchlist() {
        movies.let {
            it as MutableLiveData
            it.value = watchlistRepository.localWatchlist()
        }
    }
}