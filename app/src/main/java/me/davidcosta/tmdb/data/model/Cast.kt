package me.davidcosta.tmdb.data.model

import com.google.gson.annotations.SerializedName

data class Cast (
    @SerializedName("character") val character: String,
    @SerializedName("name") val name: String,
    @SerializedName("order") val order: Int
)