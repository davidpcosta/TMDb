package me.davidcosta.tmdb.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.davidcosta.tmdb.data.api.Api
import me.davidcosta.tmdb.data.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRepository(private val api: Api) {

    fun genres(): LiveData<List<Genre>> {
        val genres = MutableLiveData<List<Genre>>()

        api.movieGenres().enqueue(object: Callback<Genres> {
            override fun onResponse(call: Call<Genres>, response: Response<Genres>) {
                response.body()?.let {
                    genres.value = it.genres
                }
            }
            override fun onFailure(call: Call<Genres>, t: Throwable) {
                throw t
            }
        })

        return genres
    }

    fun moviesByGenre(genreId: Long): LiveData<List<Media>> {
        val result = MutableLiveData<List<Media>>()

        api.moviesByGenre(genreId).enqueue(object: Callback<PagedResult<Media>> {
            override fun onResponse(call: Call<PagedResult<Media>>, response: Response<PagedResult<Media>>) {
                response.body()?.let {
                    result.value = it.results
                }
            }
            override fun onFailure(call: Call<PagedResult<Media>>, t: Throwable) {
                throw t
            }
        })

        return result
    }

    fun similarMovies(movieId: Long): LiveData<List<Media>> {
        val result = MutableLiveData<List<Media>>()

        api.similarMovies(movieId).enqueue(object: Callback<PagedResult<Media>> {
            override fun onResponse(call: Call<PagedResult<Media>>, response: Response<PagedResult<Media>>) {
                response.body()?.let {
                    result.value = it.results
                }
            }
            override fun onFailure(call: Call<PagedResult<Media>>, t: Throwable) {
                throw t
            }
        })

        return result
    }

    fun credits(movieId: Long): LiveData<List<Cast>> {
        val credits = MutableLiveData<List<Cast>>()

        api.movieCredits(movieId).enqueue(object: Callback<Credits> {
            override fun onResponse(call: Call<Credits>, response: Response<Credits>) {
                response.body()?.let {
                    credits.value = it.cast
                }
            }
            override fun onFailure(call: Call<Credits>, t: Throwable) {
                throw t
            }
        })

        return credits
    }

    fun movieDetails(movieId: Long): LiveData<Movie> {
        val movieDetails = MutableLiveData<Movie>()

        api.movieDetails(movieId).enqueue(object: Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                response.body()?.let {
                    movieDetails.value = response.body()
                }
            }
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                throw t
            }
        })

        return movieDetails
    }

    fun popularMovies(): LiveData<List<Media>> {
        val result = MutableLiveData<List<Media>>()

        api.moviesPopular().enqueue(object: Callback<PagedResult<Media>> {
            override fun onResponse(call: Call<PagedResult<Media>>, response: Response<PagedResult<Media>>) {
                response.body()?.let {
                    result.value = it.results
                }
            }
            override fun onFailure(call: Call<PagedResult<Media>>, t: Throwable) {
                throw t
            }
        })

        return result
    }
}
