package me.davidcosta.tmdb.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.davidcosta.tmdb.data.api.Api
import me.davidcosta.tmdb.data.dao.MediaDao
import me.davidcosta.tmdb.data.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WatchlistRepository(private val api: Api, private val mediaDao: MediaDao) {

    fun watchlist(accountId: Long, sessionId: String): LiveData<List<Media>> {
        val medias = MutableLiveData<List<Media>>()

        api.watchlist(accountId, sessionId).enqueue(object: Callback<PagedResult<Media>> {
            override fun onResponse(call: Call<PagedResult<Media>>, response: Response<PagedResult<Media>>) {
                response.body()?.let {
                    medias.value = it.results

                    // Refresh na watchlist para garantir sincronização com web
                    mediaDao.deleteAll()
                    mediaDao.insertAll(it.results)
                }
            }
            override fun onFailure(call: Call<PagedResult<Media>>, t: Throwable) {
                throw t
            }
        })

        return medias
    }

    fun localWatchlist(): List<Media> {
        return mediaDao.getAll()
    }

    // TODO: Create session object to keep logged in users
    fun addToWatchlist(accountId: Long, sessionId: String?, media: Media): LiveData<WatchlistOperationResponse> {
        val operationResponse = MutableLiveData<WatchlistOperationResponse>()

        if (sessionId != null) { // logged in
            val mediaWatchlist = MediaWatchlist(mediaType = "movie", mediaId = media.id, watchlist = true)
            api.addToWatchlist(accountId = accountId, sessionId = sessionId, mediaWatchlist = mediaWatchlist).enqueue(object: Callback<WatchlistOperationResponse> {
                override fun onResponse(call: Call<WatchlistOperationResponse>, response: Response<WatchlistOperationResponse>) {
                    response.body()?.let {
                        if (it.statusCode == 1 || it.statusCode == 12) {
                            mediaDao.insert(media)
                            operationResponse.value = it
                        }
                    }
                }
                override fun onFailure(call: Call<WatchlistOperationResponse>, t: Throwable) {
                    throw t
                }
            })
        }
        else {
            mediaDao.insert(media)
            // TODO: Create generic response
            operationResponse.value = WatchlistOperationResponse(1, "")
        }

        return operationResponse
    }

    // TODO: Create session object to keep logged in users
    fun removeFromWatchlist(accountId: Long, sessionId: String?, media: Media): LiveData<WatchlistOperationResponse> {
        val operationResponse = MutableLiveData<WatchlistOperationResponse>()

        if (sessionId != null) { // logged in
            val m = MediaWatchlist(mediaType = "movie", mediaId = media.id, watchlist = false)
            api.addToWatchlist(accountId = accountId, sessionId = sessionId, mediaWatchlist = m).enqueue(object: Callback<WatchlistOperationResponse> {
                override fun onResponse(call: Call<WatchlistOperationResponse>, response: Response<WatchlistOperationResponse>) {
                    response.body()?.let {
                        if (it.statusCode == 13) {
                            mediaDao.delete(media)
                            operationResponse.value = it
                        }
                    }
                }
                override fun onFailure(call: Call<WatchlistOperationResponse>, t: Throwable) {
                    throw t
                }
            })
        } else {
            mediaDao.delete(media)
            // TODO: Create generic response
            operationResponse.value = WatchlistOperationResponse(13, "")
        }

        return operationResponse
    }

    fun isInWatchlist(media: Media): Boolean {
        return mediaDao.findById(media.id) != null
    }

}
