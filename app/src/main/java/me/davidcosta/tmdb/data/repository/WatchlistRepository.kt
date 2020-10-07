package me.davidcosta.tmdb.data.repository

import me.davidcosta.tmdb.data.api.Api
import me.davidcosta.tmdb.data.dao.WatchlistDao
import me.davidcosta.tmdb.data.entity.TitleEntity
import me.davidcosta.tmdb.data.vo.TitleVO

class WatchlistRepository(private val api: Api, private val watchlistDao: WatchlistDao) {

    fun watchlist(): List<TitleVO> {
        return watchlistDao.getAll()
            .map {
                TitleVO.fromEntity(it)
            }
    }

    fun addToWatchlist(titleEntity: TitleEntity) {
        watchlistDao.insert(titleEntity)
    }

    fun removeFromWatchlist(titleEntity: TitleEntity) {
        watchlistDao.delete(titleEntity)
    }

    fun isOnWatchlist(titleEntity: TitleEntity): Boolean {
        return  watchlistDao.findById(titleEntity.pk) != null
    }

}
