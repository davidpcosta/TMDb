package me.davidcosta.tmdb.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import me.davidcosta.tmdb.data.model.Media
import me.davidcosta.tmdb.data.model.Movie

@Database(entities = [Movie::class, Media::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun mediaDao(): MediaDao
}
