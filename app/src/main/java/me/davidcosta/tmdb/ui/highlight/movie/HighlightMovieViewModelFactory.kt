package me.davidcosta.tmdb.ui.highlight.movie

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import me.davidcosta.tmdb.data.api.ApiService
import me.davidcosta.tmdb.data.dao.AppDatabase
import me.davidcosta.tmdb.data.repository.MoviesRepository
import me.davidcosta.tmdb.data.repository.WatchlistRepository

@Suppress("UNCHECKED_CAST")
class HighlightMovieViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HighlightMovieViewModel::class.java)) {
            return HighlightMovieViewModel(
                moviesRepository = MoviesRepository(
                    api = ApiService.instance
                ),
                watchlistRepository = WatchlistRepository(
                    api = ApiService.instance,
                    mediaDao = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java, "database-watchlist"
                    ).allowMainThreadQueries().build().mediaDao()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
