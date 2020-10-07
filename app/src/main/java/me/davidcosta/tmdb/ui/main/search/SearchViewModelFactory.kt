package me.davidcosta.tmdb.ui.main.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import me.davidcosta.tmdb.data.api.ApiService
import me.davidcosta.tmdb.data.dao.AppDatabase
import me.davidcosta.tmdb.data.repository.SearchRepository
import me.davidcosta.tmdb.data.repository.WatchlistRepository

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(
                    searchRepository = SearchRepository(
                        api = ApiService.instance
                    )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
