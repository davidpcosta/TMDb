package me.davidcosta.tmdb.data.repository

import me.davidcosta.tmdb.data.api.Api
import me.davidcosta.tmdb.data.model.*
import retrofit2.await

class TvsRepository(private val api: Api) {

    suspend fun genres(): List<Genre> {
        val deferred = api.tvGenres()
        return deferred.await().genres
    }

    suspend fun tvDetails(tvId: Long): Tv {
        val deferred = api.tvDetails(tvId)
        return deferred.await()
    }

    suspend fun credits(tvId: Long): Credits {
        val deferred = api.tvsCredits(tvId)
        return deferred.await()
    }

    suspend fun popularTvs(): List<Media> {
        val deferred = api.tvsPopular()
        return deferred.await().results.map { it.lazyInit() }
    }

}
