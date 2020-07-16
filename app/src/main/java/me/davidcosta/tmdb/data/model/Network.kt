package me.davidcosta.tmdb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Network(
    @PrimaryKey @SerializedName("id") var id: Long,
    @SerializedName("name") var name: String,
    @SerializedName("logo_path") var logoPath: String,
    @SerializedName("origin_country") var originCountry: String
): Serializable