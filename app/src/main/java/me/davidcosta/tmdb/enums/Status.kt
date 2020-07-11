package me.davidcosta.tmdb.enums

import me.davidcosta.tmdb.R

enum class Status(val label: Int) {

    RUMORED(R.string.status_rumored),
    PLANNED(R.string.status_planned),
    IN_PRODUCTION(R.string.status_in_production),
    POST_PRODUCTION(R.string.status_post_production),
    RELEASED(R.string.status_released),
    CANCELED(R.string.status_canceled)
}