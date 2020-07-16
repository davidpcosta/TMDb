package me.davidcosta.tmdb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Cast (
    @PrimaryKey @SerializedName("cast_id") val castId: Long,
    @SerializedName("character") val character: String,
    @SerializedName("name") val name: String,
    @SerializedName("order") val order: Int
)