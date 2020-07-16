package me.davidcosta.tmdb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

@Entity
data class Season(
    @PrimaryKey @SerializedName("id") var id: Long,
    @SerializedName("air_date") var airDate: Date,
    @SerializedName("name") var name: String,
    @SerializedName("overview") var overview: String,
    @SerializedName("poster_path") var posterPath: String?,
    @SerializedName("episode_count") var episodeCount: Int,
    @SerializedName("season_number") var seasonNumber: Int
): Serializable