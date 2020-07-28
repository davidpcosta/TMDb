package me.davidcosta.tmdb.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Credits (
    @SerializedName("cast") val cast: List<Cast>,
    @SerializedName("crew") val crew: List<Credit>
) : Serializable