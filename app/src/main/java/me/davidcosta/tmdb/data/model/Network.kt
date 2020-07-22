package me.davidcosta.tmdb.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Network(
    @SerializedName("id") var id: Long,
    @SerializedName("name") var name: String,
    @SerializedName("logo_path") var logoPath: String,
    @SerializedName("origin_country") var originCountry: String
): Serializable