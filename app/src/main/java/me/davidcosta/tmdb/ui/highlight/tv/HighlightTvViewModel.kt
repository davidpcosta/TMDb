package me.davidcosta.tmdb.ui.highlight.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.davidcosta.tmdb.data.model.*
import me.davidcosta.tmdb.data.repository.TvsRepository
import me.davidcosta.tmdb.data.repository.WatchlistRepository

class HighlightTvViewModel(private val tvsRepository: TvsRepository, private val watchlistRepository: WatchlistRepository) : ViewModel() {

//    lateinit var similarMovies: LiveData<List<Movie>>
    lateinit var tv: LiveData<Tv>
    lateinit var cast: LiveData<List<Cast>>
    val isOnWatchlist: LiveData<Boolean> = MutableLiveData(false)

//    fun fetchSimilarMovies(movieId: Long) {
//        similarMovies = moviesRepository.similarMovies(movieId)
//    }

//    fun fetchCredits(movieId: Long) {
//        cast = moviesRepository.credits(movieId)
//    }

    fun tvDetails(tvId: Long) {
        tv = tvsRepository.tvDetails(tvId)
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