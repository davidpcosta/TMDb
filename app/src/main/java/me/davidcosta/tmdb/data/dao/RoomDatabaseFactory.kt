package me.davidcosta.tmdb.data.dao

import android.content.Context
import androidx.room.Room
import me.davidcosta.tmdb.enums.Keys


class RoomDatabaseFactory {
    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: Room.databaseBuilder(
                context,
                AppDatabase::class.java, Keys.ROOM_DATABASE_NAME.value
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}