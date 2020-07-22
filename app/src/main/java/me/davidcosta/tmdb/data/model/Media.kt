package me.davidcosta.tmdb.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import me.davidcosta.tmdb.enums.MediaType
import me.davidcosta.tmdb.extensions.toEnum
import java.io.Serializable

@Entity(tableName = "watchlist")
data class Media(
    @PrimaryKey var pk: String,
    @SerializedName("id") var id: Long,
    @SerializedName("name") var name: String?,
    @SerializedName("original_name") var originalName: String?,
    @SerializedName("title") var title: String?,
    @SerializedName("original_title") var originalTitle: String?,
    @SerializedName("backdrop_path") var backdropPath: String?,
    @SerializedName("overview") var overview: String?,
    @SerializedName("poster_path") var posterPath: String?,
    @SerializedName("media_type") var mediaType: String?
) : Serializable {

    fun lazyInit(): Media {
        defineMediaType()
        definePrimaryKey()
        return this
    }

    @Transient
    private var mMediaType: MediaType? = null

    val getMediaType: MediaType
        get() {
            defineMediaType()
            return this.mMediaType!!
        }

    val nameOrTile: String
        get() = when(getMediaType) {
            MediaType.MOVIE -> title ?: "-"
            else -> name ?: "-"
        }

    val originalNameOrOriginalTile: String
        get() = when(getMediaType) {
            MediaType.MOVIE -> originalTitle ?: "-"
            else -> originalName ?: "-"
        }

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

    private fun definePrimaryKey() {
        pk = "${mMediaType?.name}-$id"
    }
}