package me.davidcosta.tmdb.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import me.davidcosta.tmdb.data.model.Media

@Database(entities = [Media::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mediaDao(): MediaDao
}
