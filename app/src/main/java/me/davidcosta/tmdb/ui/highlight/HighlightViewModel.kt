package me.davidcosta.tmdb.ui.highlight

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.davidcosta.tmdb.data.model.*
import me.davidcosta.tmdb.data.repository.TitleRepository
import me.davidcosta.tmdb.data.repository.WatchlistRepository
import me.davidcosta.tmdb.data.vo.TitleDetailsVO
import me.davidcosta.tmdb.data.vo.TitleVO
import me.davidcosta.tmdb.enums.MediaType

class HighlightViewModel(private val titleRepository: TitleRepository, private val watchlistRepository: WatchlistRepository) : ViewModel() {

    val similarMovies: LiveData<List<Media>> = MutableLiveData()
    val titleDetailsDetails: LiveData<TitleDetailsVO?> = MutableLiveData()
    val credits: LiveData<Credits> = MutableLiveData()
    val isOnWatchlist: LiveData<Boolean> = MutableLiveData(false)

//    fun fetchSimilarMovies(movieId: Long) {
//        CoroutineScope(Dispatchers.Main).launch {
//            (similarMovies as MutableLiveData).apply {
//                value = moviesRepository.similarMovies(movieId)
//            }
//        }
//    }


    fun fetchTitleDetails(titleId: Long, mediaType: MediaType) {
        CoroutineScope(Dispatchers.Main).launch {
            (titleDetailsDetails as MutableLiveData).apply {
                value = titleRepository.titleDetails(titleId, mediaType)
            }
        }
    }

    fun addToWatchlist(titleVO: TitleVO) {
        watchlistRepository.addToWatchlist(titleVO.toTitleEntity())
        (isOnWatchlist as MutableLiveData).apply {
            value = true
        }
    }

    fun removeFromWatchlist(titleVO: TitleVO) {
        watchlistRepository.removeFromWatchlist(titleVO.toTitleEntity())
        (isOnWatchlist as MutableLiveData).apply {
            value = false
        }
    }


    fun isOnWatchlist(titleVO: TitleVO): Boolean {
        val isOnList = watchlistRepository.isOnWatchlist(titleVO.toTitleEntity())
        (isOnWatchlist as MutableLiveData).apply {
            value = isOnList
        }
        return isOnList
    }

}