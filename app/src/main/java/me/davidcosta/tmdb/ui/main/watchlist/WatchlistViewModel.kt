package me.davidcosta.tmdb.ui.main.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.davidcosta.tmdb.data.repository.WatchlistRepository
import me.davidcosta.tmdb.data.vo.TitleVO

class WatchlistViewModel(private val watchlistRepository: WatchlistRepository) : ViewModel() {

    var medias: LiveData<List<TitleVO>> = MutableLiveData()

    fun fetchWatchlist() {
        (medias as MutableLiveData).apply {
            value = watchlistRepository.watchlist()
        }
    }
}