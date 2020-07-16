package me.davidcosta.tmdb.enums

import me.davidcosta.tmdb.R

enum class TvStatus(val label: Int) {
    RETURNING_SERIES(R.string.tv_status_returning_series),
    ENDED(R.string.tv_status_ended)
}