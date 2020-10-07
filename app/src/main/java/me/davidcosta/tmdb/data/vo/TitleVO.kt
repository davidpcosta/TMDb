package me.davidcosta.tmdb.data.vo

import me.davidcosta.tmdb.data.entity.TitleEntity
import me.davidcosta.tmdb.data.model.Media
import me.davidcosta.tmdb.enums.MediaType
import java.io.Serializable

data class TitleVO (
    var id: Long,
    var mediaType: MediaType,
    var title: String,
    var originalTitle: String,
    var posterPath: String?,
    var backdropPath: String?,
    var voteAverage: Double,
    var overview: String?
): Serializable {
    companion object {
        fun fromMediaMovie(media: Media) =
            TitleVO(
                id = media.id,
                mediaType = MediaType.MOVIE,
                title = media.title ?: "-",
                originalTitle = media.originalTitle ?: "-",
                posterPath = media.posterPath,
                backdropPath = media.backdropPath,
                voteAverage = media.voteAverage,
                overview = media.overview
            )

        fun fromMediaTV(media: Media) =
            TitleVO(
                id = media.id,
                mediaType = MediaType.TV,
                title = media.name ?: "-",
                originalTitle = media.originalName ?: "-",
                posterPath = media.posterPath,
                backdropPath = media.backdropPath,
                voteAverage = media.voteAverage,
                overview = media.overview
            )

        fun fromMedia(media: Media) =
            when (media.mediaType) {
                "movie" -> TitleVO.fromMediaMovie(media)
                else -> TitleVO.fromMediaTV(media)
            }

        fun fromEntity(titleEntity: TitleEntity) =
            TitleVO(
                id = titleEntity.id,
                mediaType = MediaType.parse(titleEntity.mediaType),
                title = titleEntity.title,
                originalTitle = titleEntity.originalTitle,
                posterPath = titleEntity.posterPath,
                backdropPath = titleEntity.backdropPath,
                voteAverage = titleEntity.voteAverage,
                overview = titleEntity.overview
            )
    }

    fun toTitleEntity(): TitleEntity =
        TitleEntity(
            pk = "${this.mediaType.name}-$id",
            id = this.id,
            mediaType = this.mediaType.name,
            title = this.title,
            originalTitle = this.originalTitle,
            posterPath = this.posterPath,
            backdropPath = this.backdropPath,
            voteAverage = this.voteAverage,
            overview = this.overview
        )
}