package me.davidcosta.tmdb.ui.main.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.davidcosta.tmdb.data.model.Media
import me.davidcosta.tmdb.data.repository.WatchlistRepository

class WatchlistViewModel(private val watchlistRepository: WatchlistRepository) : ViewModel() {

    lateinit var medias: LiveData<List<Media>>

    fun fetchWatchlist(accountId: Long, sessionId: String) {
        medias = watchlistRepository.watchlist(accountId, sessionId)
    }

    fun updateFromLocalWatchlist() {
        medias.let {
            it as MutableLiveData
            it.value = watchlistRepository.localWatchlist()
        }
    }
}