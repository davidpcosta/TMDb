package me.davidcosta.tmdb.ui.main.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.davidcosta.tmdb.data.model.Media
import me.davidcosta.tmdb.data.repository.WatchlistRepository

class WatchlistViewModel(private val watchlistRepository: WatchlistRepository) : ViewModel() {

    var medias: LiveData<List<Media>> = MutableLiveData<List<Media>>()

    fun fetchWatchlist() {
        (medias as MutableLiveData).apply {
            value = watchlistRepository.watchlist()
        }
    }
}