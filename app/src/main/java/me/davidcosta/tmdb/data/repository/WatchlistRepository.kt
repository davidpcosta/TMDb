package me.davidcosta.tmdb.data.repository

import me.davidcosta.tmdb.data.api.Api
import me.davidcosta.tmdb.data.dao.WatchlistDao
import me.davidcosta.tmdb.data.model.*

class WatchlistRepository(private val api: Api, private val watchlistDao: WatchlistDao) {

    fun watchlist(): List<Media> {
        return watchlistDao.getAll()
    }

    fun addToWatchlist(media: Media) {
        watchlistDao.insert(media)
    }

    fun removeFromWatchlist(media: Media) {
        watchlistDao.delete(media)
    }

    fun isOnWatchlist(media: Media): Boolean {
        return watchlistDao.findById(media.pk) != null
    }

}
