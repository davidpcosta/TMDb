package me.davidcosta.tmdb.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.davidcosta.tmdb.data.api.ApiService
import me.davidcosta.tmdb.data.repository.MoviesRepository
import me.davidcosta.tmdb.data.repository.TvsRepository

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                moviesRepository = MoviesRepository(
                    api = ApiService.instance
                ),
                tvsRepository = TvsRepository(
                    api = ApiService.instance
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
