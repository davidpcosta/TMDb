package me.davidcosta.tmdb.ui.highlight.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.davidcosta.tmdb.data.model.*
import me.davidcosta.tmdb.data.repository.TvsRepository
import me.davidcosta.tmdb.data.repository.WatchlistRepository
class HighlightTvViewModel(private val tvsRepository: TvsRepository, private val watchlistRepository: WatchlistRepository) : ViewModel() {

    val tvDetails: LiveData<Tv> = MutableLiveData()
    val credits: LiveData<Credits> = MutableLiveData()
    val isOnWatchlist: LiveData<Boolean> = MutableLiveData(false)


    fun fetchCredits(tvId: Long) {
        CoroutineScope(Dispatchers.Main).launch {
            (credits as MutableLiveData).apply {
                value = tvsRepository.credits(tvId)
            }
        }
    }

    fun tvDetails(tvId: Long) {
        CoroutineScope(Dispatchers.Main).launch {
            (tvDetails as MutableLiveData).apply {
                value = tvsRepository.tvDetails(tvId)
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

