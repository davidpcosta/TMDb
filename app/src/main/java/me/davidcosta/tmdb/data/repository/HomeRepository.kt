package me.davidcosta.tmdb.data.repository

import me.davidcosta.tmdb.data.api.Api
import me.davidcosta.tmdb.data.api.Params
import me.davidcosta.tmdb.data.model.Media
import me.davidcosta.tmdb.data.vo.TitleVO
import me.davidcosta.tmdb.extensions.toJsonFormat
import java.util.*

private const val UPCOMING_DAYS_COUNT = 21
private const val LATEST_DAYS_COUNT = -21

class HomeRepository(private val api: Api){

    suspend fun trending(): List<TitleVO> {
        val deferred = api.trending()
        return deferred.await().results
            .filter {
                it.mediaType == "movie" || it.mediaType == "tv"
            }
            .map {
                TitleVO.fromMedia(it)
            }
    }

    suspend fun upcomingMovies(): List<TitleVO> {
        val startDate = Calendar.getInstance().apply {
            add(Calendar.DATE, 1)
        }
        val endDate = Calendar.getInstance().apply {
            add(Calendar.DATE, UPCOMING_DAYS_COUNT)
        }

        val deferred = api.moviesDiscover(
            primaryReleaseDateStart = startDate.time.toJsonFormat(),
            primaryReleaseDateEnd = endDate.time.toJsonFormat(),
            withReleaseType = intArrayOf(Params.ReleaseType.Theatrical.value),
            sortBy = Params.SortBy.PopularityDesc.value
        )
        return deferred.await().results.map {
            TitleVO.fromMediaMovie(it)
        }
    }

    suspend fun latestMovies(): List<TitleVO> {
        val startDate = Calendar.getInstance().apply {
            add(Calendar.DATE, LATEST_DAYS_COUNT)
        }
        val endDate = Calendar.getInstance()
        val deferred = api.moviesDiscover(
            primaryReleaseDateStart = startDate.time.toJsonFormat(),
            primaryReleaseDateEnd = endDate.time.toJsonFormat(),
            withReleaseType = intArrayOf(Params.ReleaseType.Theatrical.value),
            sortBy = Params.SortBy.PopularityDesc.value
        )
        return deferred.await().results.map {
            TitleVO.fromMediaMovie(it)
        }
    }

    suspend fun popularTvs(): List<TitleVO> {
        val deferred = api.tvsPopular()
        return deferred.await().results.map {
            TitleVO.fromMediaTV(it)
        }
    }

    suspend fun popularMovies(): List<TitleVO> {
        val deferred = api.moviesPopular()
        return deferred.await().results.map {
            TitleVO.fromMediaMovie(it)
        }
    }

}
