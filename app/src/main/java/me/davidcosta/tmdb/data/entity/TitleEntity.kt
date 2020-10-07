package me.davidcosta.tmdb.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist")
data class TitleEntity (
    @PrimaryKey
    val pk: String,
    val id: Long,
    val mediaType: String,
    val title: String,
    val originalTitle: String,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val overview: String?
)