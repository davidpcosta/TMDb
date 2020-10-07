package me.davidcosta.tmdb.ui.highlight

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import me.davidcosta.tmdb.data.api.ApiService
import me.davidcosta.tmdb.data.dao.AppDatabase
import me.davidcosta.tmdb.data.dao.RoomDatabaseFactory
import me.davidcosta.tmdb.data.repository.MoviesRepository
import me.davidcosta.tmdb.data.repository.TitleRepository
import me.davidcosta.tmdb.data.repository.TvsRepository
import me.davidcosta.tmdb.data.repository.WatchlistRepository

@Suppress("UNCHECKED_CAST")
class HighlightViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HighlightViewModel::class.java)) {
            return HighlightViewModel(
                titleRepository = TitleRepository(
                    moviesRepository = MoviesRepository(ApiService.instance),
                    tvsRepository = TvsRepository(ApiService.instance)
                ),
                watchlistRepository = WatchlistRepository(
                    api = ApiService.instance,
                    watchlistDao = RoomDatabaseFactory.getInstance(context).watchlistDat()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
