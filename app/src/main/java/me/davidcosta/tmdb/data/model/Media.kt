package me.davidcosta.tmdb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import me.davidcosta.tmdb.enums.MediaType
import me.davidcosta.tmdb.extensions.toEnum
import java.io.Serializable


data class Media(
    var pk: String,
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String?,
    @SerializedName("original_name") val originalName: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("overview") val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("media_type") val mediaType: String?
) : Serializable {

    @Deprecated("do not use")
    fun lazyInit(): Media {
        defineMediaType()
        definePrimaryKey()
        return this
    }

    @Deprecated("do not use")
    @Transient
    private var mMediaType: MediaType? = null

    @Deprecated("do not use")
    val getMediaType: MediaType
        get() {
            defineMediaType()
            return this.mMediaType!!
        }

    @Deprecated("do not use")
    val nameOrTile: String
        get() = when(getMediaType) {
            MediaType.MOVIE -> title ?: "-"
            else -> name ?: "-"
        }

    @Deprecated("do not use")
    val originalNameOrOriginalTile: String
        get() = when(getMediaType) {
            MediaType.MOVIE -> originalTitle ?: "-"
            else -> originalName ?: "-"
        }

    @Deprecated("do not use")
    private fun defineMediaType() {
        mMediaType?.let {
            return
        }
        mediaType?.let {
            mMediaType = it.toEnum<MediaType>()
            return
        }
        title?.let {
            mMediaType = MediaType.MOVIE
            return
        }
        name?.let {
            mMediaType = MediaType.TV
            return
        }
    }

    @Deprecated("do not use")
    private fun definePrimaryKey() {
        pk = "${mMediaType?.name}-$id"
    }
}