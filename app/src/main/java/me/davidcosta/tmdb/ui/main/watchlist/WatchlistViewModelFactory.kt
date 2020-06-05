package me.davidcosta.tmdb.ui.main.watchlist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import me.davidcosta.tmdb.data.api.ApiService
import me.davidcosta.tmdb.data.dao.AppDatabase
import me.davidcosta.tmdb.data.repository.WatchlistRepository

@Suppress("UNCHECKED_CAST")
class WatchlistViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WatchlistViewModel::class.java)) {
            return WatchlistViewModel(
                    watchlistRepository = WatchlistRepository(
                        api = ApiService.instance,
                        movieDao = Room.databaseBuilder(
                            context,
                            AppDatabase::class.java, "database-watchlist"
                        ).allowMainThreadQueries().build().movieDao()
                    )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
