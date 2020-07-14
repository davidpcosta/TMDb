package me.davidcosta.tmdb.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import me.davidcosta.tmdb.data.model.Genre
import me.davidcosta.tmdb.data.model.Movie
import me.davidcosta.tmdb.data.model.Media
import me.davidcosta.tmdb.data.repository.MoviesRepository

class HomeViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    lateinit var genres: LiveData<List<Genre>>
    lateinit var movies: LiveData<List<Movie>>
    lateinit var popularMovies: LiveData<List<Media>>
    lateinit var popularTvs: LiveData<List<Media>>

    fun fetchGenres() {
        genres = moviesRepository.genres()
    }

    fun fetchMoviesByGenre(genreId: Long) {
        movies = moviesRepository.moviesByGenre(genreId)
    }

    fun fetchPopularMovies() {
        popularMovies = moviesRepository.popularMovies()
    }

    fun fetchPopularTvs() {
        popularTvs = moviesRepository.popularTvs()
    }
}