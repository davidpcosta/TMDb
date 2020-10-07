package me.davidcosta.tmdb.data.repository

import me.davidcosta.tmdb.data.vo.*
import me.davidcosta.tmdb.enums.Gender
import me.davidcosta.tmdb.enums.MediaType

class TitleRepository(private val moviesRepository: MoviesRepository, private val tvsRepository: TvsRepository) {

    suspend fun titleDetails(titleId: Long, mediaType: MediaType): TitleDetailsVO? =
        title(titleId, mediaType)?.let {
            it.credits = credits(titleId, mediaType)
            it
        }

    suspend fun title(titleId: Long, mediaType: MediaType): TitleDetailsVO? =
        when (mediaType) {
            MediaType.MOVIE -> moviesRepository.movieDetails(titleId).run {
                TitleDetailsVO.fromMovie( this )
            }

            MediaType.TV -> tvsRepository.tvDetails(titleId).run {
                TitleDetailsVO.fromTV( this )
            }

            else -> null
        }

    suspend fun credits(titleId: Long, mediaType: MediaType): CreditsVO? =
        when (mediaType) {
            MediaType.MOVIE -> moviesRepository.credits(titleId)
            MediaType.TV -> tvsRepository.credits(titleId)
            else -> null
        }?.let { credits ->
            val cast = credits.cast.map {
                PersonVO(
                    id = it.castId,
                    role = it.character,
                    name = it.name,
                    profilePath = it.profilePath,
                    gender = Gender.parse(it.gender)
                )
            }

            val crew = credits.crew.map {
                PersonVO(
                    id = it.id,
                    name = it.name,
                    profilePath = it.profilePath,
                    department = it.department,
                    job = it.job,
                    gender = Gender.parse(it.gender)
                )
            }

            CreditsVO(cast, crew)
        }
}
