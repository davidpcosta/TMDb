package me.davidcosta.tmdb.enums

import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.extensions.normalizeToEnumName
import me.davidcosta.tmdb.extensions.toEnum

enum class TitleStatus(val label: Int) {
    // MOVIE
    RUMORED(R.string.movie_status_rumored),
    PLANNED(R.string.movie_status_planned),
    IN_PRODUCTION(R.string.movie_status_in_production),
    POST_PRODUCTION(R.string.movie_status_post_production),
    RELEASED(R.string.movie_status_released),
    CANCELED(R.string.movie_status_canceled),

    // TV
    RETURNING_SERIES(R.string.tv_status_returning_series),
    ENDED(R.string.tv_status_ended);

    companion object {
        fun parse(value: String): TitleStatus {
            return enumValueOf(value.normalizeToEnumName())
        }
    }
}