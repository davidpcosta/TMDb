package me.davidcosta.tmdb.enums

import me.davidcosta.tmdb.R

enum class MovieStatus(val label: Int) {
    RUMORED(R.string.movie_status_rumored),
    PLANNED(R.string.movie_status_planned),
    IN_PRODUCTION(R.string.movie_status_in_production),
    POST_PRODUCTION(R.string.movie_status_post_production),
    RELEASED(R.string.movie_status_released),
    CANCELED(R.string.movie_status_canceled)
}