package me.davidcosta.tmdb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity
data class TV (
    @PrimaryKey @SerializedName("id") var id: Long,
    @SerializedName("name") var name: String,
    @SerializedName("original_name") var originalName: String,
    @SerializedName("backdrop_path") var backdropPath: String?,
    @SerializedName("overview") var overview: String?,
    @SerializedName("poster_path") var posterPath: String?
): Serializable