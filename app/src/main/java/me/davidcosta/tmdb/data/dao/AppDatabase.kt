package me.davidcosta.tmdb.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import me.davidcosta.tmdb.data.entity.TitleEntity

@Database(entities = [TitleEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun watchlistDat(): WatchlistDao

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("UPDATE watchlist set title = name AND originalTitle = originalName where originalName IS NOT NULL")
        }
    }

}
