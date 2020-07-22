package me.davidcosta.tmdb.ui.highlight.tv

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import me.davidcosta.tmdb.data.api.ApiService
import me.davidcosta.tmdb.data.dao.AppDatabase
import me.davidcosta.tmdb.data.repository.MoviesRepository
import me.davidcosta.tmdb.data.repository.TvsRepository
import me.davidcosta.tmdb.data.repository.WatchlistRepository
import me.davidcosta.tmdb.enums.Keys

@Suppress("UNCHECKED_CAST")
class HighlightTvViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HighlightTvViewModel::class.java)) {
            return HighlightTvViewModel(
                    tvsRepository = TvsRepository(
                        api = ApiService.instance
                    ),
                    watchlistRepository = WatchlistRepository(
                        api = ApiService.instance,
                        watchlistDao = Room.databaseBuilder(
                            context,
                            AppDatabase::class.java, Keys.ROOM_DATABASE_NAME.value
                        ).allowMainThreadQueries().build().watchlistDat()
                    )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
