package me.davidcosta.tmdb.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Credit(
    @SerializedName("id") val id: Long,
    @SerializedName("credit_id") val creditId: String,
    @SerializedName("name") val name: String,
    @SerializedName("profile_path") val profilePath: String,
    @SerializedName("gender") val gender: Int
) :Serializable
