package me.davidcosta.tmdb.data.vo

import me.davidcosta.tmdb.data.model.Movie
import me.davidcosta.tmdb.data.model.Tv
import me.davidcosta.tmdb.enums.MediaType
import me.davidcosta.tmdb.enums.TitleStatus
import java.util.*

data class TitleDetailsVO(
    val id: Long,
    val mediaType: MediaType,
    val title: String,
    val originalTitle: String,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val overview: String?,
    val releaseDate: Date?,
    val originalLanguage: String,
    val genres: List<String>,
    val runtime: Int,
    val status: TitleStatus,
    val budget: Double? = null,
    val revenue: Double? = null,
    var credits: CreditsVO? = null
) {
    companion object {

        fun fromMovie(movie: Movie) = TitleDetailsVO(
            id = movie.id,
            mediaType = MediaType.MOVIE,
            title = movie.title,
            originalTitle = movie.originalTitle,
            posterPath = movie.posterPath,
            backdropPath = movie.backdropPath,
            voteAverage = movie.voteAverage,
            overview = movie.overview,
            releaseDate = movie.releaseDate,
            originalLanguage = movie.originalLanguage,
            genres = movie.genres.map { it.name },
            runtime = movie.runtime,
            status = TitleStatus.parse(movie.status),
            budget = movie.budget,
            revenue = movie.revenue
        )

        fun fromTV(tv: Tv) = TitleDetailsVO(
            id = tv.id,
            mediaType = MediaType.TV,
            title = tv.name,
            originalTitle = tv.originalName,
            posterPath = tv.posterPath,
            backdropPath = tv.backdropPath,
            voteAverage = tv.voteAverage,
            overview = tv.overview,
            releaseDate = tv.firstAirData,
            originalLanguage = tv.originalLanguage,
            genres = tv.genres?.map { it.name } ?: listOf(),
            runtime = tv.episodeRuntime.first(),
            status = TitleStatus.parse(tv.status)
        )
    }
}