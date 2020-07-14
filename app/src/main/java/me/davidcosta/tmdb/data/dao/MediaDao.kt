package me.davidcosta.tmdb.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import me.davidcosta.tmdb.data.model.Media

@Dao
interface MediaDao {
    @Query("SELECT * FROM media")
    fun getAll(): List<Media>

    @Query("SELECT * FROM media WHERE id = :id ")
    fun findById(id: Long): Media?

    @Insert(onConflict = REPLACE)
    fun insertAll(medias: List<Media>)

    @Insert(onConflict = REPLACE)
    fun insert(media: Media)

    @Delete
    fun delete(media: Media)

    @Query("DELETE FROM media")
    fun deleteAll()
}