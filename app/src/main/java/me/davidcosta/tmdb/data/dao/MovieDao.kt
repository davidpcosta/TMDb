package me.davidcosta.tmdb.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import me.davidcosta.tmdb.data.model.Media

@Dao
interface WatchlistDao {
    @Query("SELECT * FROM watchlist")
    fun getAll(): List<Media>

    @Query("SELECT * FROM watchlist WHERE pk = :pk ")
    fun findById(pk: String): Media?

    @Insert(onConflict = REPLACE)
    fun insertAll(medias: List<Media>)

    @Insert(onConflict = REPLACE)
    fun insert(media: Media)

    @Delete
    fun delete(media: Media)

    @Query("DELETE FROM watchlist")
    fun deleteAll()
}