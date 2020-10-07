package me.davidcosta.tmdb.data.repository

import me.davidcosta.tmdb.data.api.Api
import me.davidcosta.tmdb.data.api.Params
import me.davidcosta.tmdb.data.model.Credits
import me.davidcosta.tmdb.data.model.Genre
import me.davidcosta.tmdb.data.model.Media
import me.davidcosta.tmdb.data.model.Movie
import me.davidcosta.tmdb.data.vo.TitleDetailsVO
import me.davidcosta.tmdb.enums.MediaType
import me.davidcosta.tmdb.extensions.toJsonFormat
import java.util.*

class MoviesRepository(private val api: Api){

    private val UPCOMING_DAYS_COUNT = 21

    suspend fun genres(): List<Genre> {
        val deferred = api.movieGenres()
        return deferred.await().genres
    }

    suspend fun moviesByGenre(genreId: Long): List<Media> {
        val deferred = api.moviesDiscover(genreId = genreId)
        return deferred.await().results.map { it.lazyInit() }
    }

    suspend fun similarMovies(movieId: Long): List<Media> {
        val deferred = api.similarMovies(movieId)
        return deferred.await().results.map { it.lazyInit() }
    }

    suspend fun credits(movieId: Long): Credits {
        val deferred = api.movieCredits(movieId)
        return deferred.await()
    }

    suspend fun movieDetails(movieId: Long): Movie {
        val deferred = api.movieDetails(movieId)
        return deferred.await()
    }

    suspend fun titleDetails(titleId: Long, mediaType: MediaType): TitleDetailsVO =
        when (mediaType) {
            MediaType.MOVIE -> movieDetails(titleId).run {
                TitleDetailsVO.fromMovie( this )
            }
            else -> movieDetails(titleId).run {
                TitleDetailsVO.fromMovie( this )
            }
        }

    // TODO: Move to home repository
    suspend fun trending(): List<Media> {
        val deferred = api.trending()
        return deferred.await().results.map { it.lazyInit() }
    }

    suspend fun upcoming(): List<Media> {
        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance().apply {
            add(Calendar.DATE, UPCOMING_DAYS_COUNT)
        }

        val deferred = api.moviesDiscover(
            primaryReleaseDateStart = startDate.time.toJsonFormat(),
            primaryReleaseDateEnd = endDate.time.toJsonFormat(),
            withReleaseType = intArrayOf(Params.ReleaseType.Theatrical.value),
            sortBy = Params.SortBy.PopularityDesc.value
        )
        return deferred.await().results.map {  it.lazyInit() }
    }

    suspend fun popularMovies(): List<Media> {
        val deferred = api.moviesPopular()
        return deferred.await().results.map { it.lazyInit() }
    }

}
