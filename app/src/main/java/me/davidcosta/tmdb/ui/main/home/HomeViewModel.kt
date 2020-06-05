package me.davidcosta.tmdb.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import me.davidcosta.tmdb.data.model.Genre
import me.davidcosta.tmdb.data.model.Movie
import me.davidcosta.tmdb.data.repository.MoviesRepository

class HomeViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    lateinit var genres: LiveData<List<Genre>>
    lateinit var movies: LiveData<List<Movie>>

    fun fetchGenres() {
        genres = moviesRepository.genres()
    }

    fun fetchMoviesByGenre(genreId: Long) {
        movies = moviesRepository.moviesByGenre(genreId)
    }
}