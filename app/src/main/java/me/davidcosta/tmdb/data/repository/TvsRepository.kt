package me.davidcosta.tmdb.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.davidcosta.tmdb.data.api.Api
import me.davidcosta.tmdb.data.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvsRepository(private val api: Api) {

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

    fun tvDetails(tvId: Long): LiveData<Tv> {
        val movieDetails = MutableLiveData<Tv>()

        api.tvDetails(tvId).enqueue(object: Callback<Tv> {
            override fun onResponse(call: Call<Tv>, response: Response<Tv>) {
                response.body()?.let {
                    movieDetails.value = response.body()
                }
            }
            override fun onFailure(call: Call<Tv>, t: Throwable) {
                throw t
            }
        })

        return movieDetails
    }

    fun popularTvs(): LiveData<List<Media>> {
        val result = MutableLiveData<List<Media>>()

        api.tvsPopular().enqueue(object: Callback<PagedResult<Media>> {
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
