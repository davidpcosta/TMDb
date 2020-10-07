package me.davidcosta.tmdb.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import me.davidcosta.tmdb.data.entity.TitleEntity

@Dao
interface WatchlistDao {
    @Query("SELECT * FROM watchlist")
    fun getAll(): List<TitleEntity>

    @Query("SELECT * FROM watchlist WHERE pk = :pk ")
    fun findById(pk: String): TitleEntity?

    @Insert(onConflict = REPLACE)
    fun insertAll(entities: List<TitleEntity>)

    @Insert(onConflict = REPLACE)
    fun insert(entity: TitleEntity)

    @Delete
    fun delete(entity: TitleEntity)

    @Query("DELETE FROM watchlist")
    fun deleteAll()
}