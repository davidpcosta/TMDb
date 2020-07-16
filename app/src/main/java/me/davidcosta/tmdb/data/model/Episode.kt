package me.davidcosta.tmdb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

@Entity
data class Episode(
    @PrimaryKey @SerializedName("id") var id: Long,
    @SerializedName("show_id") var showId: Long,
    @SerializedName("name") var name: String,
    @SerializedName("still_path") var stillPath: String,
    @SerializedName("overview") var overview: String,
    @SerializedName("episode_number") var episodeNumber: Int,
    @SerializedName("season_number") var seasonNumber: Int,
    @SerializedName("air_date") var airDate: Date,
    @SerializedName("production_code") var productionCode: String,
    @SerializedName("vote_average") var voteAverage: Double,
    @SerializedName("vote_count") var voteCount: Long
): Serializable