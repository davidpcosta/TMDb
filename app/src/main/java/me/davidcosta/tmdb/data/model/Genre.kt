package me.davidcosta.tmdb.data.model

import com.google.gson.annotations.SerializedName

data class Genre (
    @SerializedName("id") var id: Long,
    @SerializedName("name") var name: String
)