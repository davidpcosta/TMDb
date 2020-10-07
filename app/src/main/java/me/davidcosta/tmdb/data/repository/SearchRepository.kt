package me.davidcosta.tmdb.data.repository

import me.davidcosta.tmdb.data.api.Api
import me.davidcosta.tmdb.data.vo.TitleVO

class SearchRepository(private val api: Api) {

    suspend fun multiSearch(query: String): List<TitleVO> {
        val deferred = api.searchMulti(query = query)
        return deferred.await().results
            .map {
                TitleVO.fromMedia(it)
            }
    }

}
