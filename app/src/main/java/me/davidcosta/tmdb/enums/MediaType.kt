package me.davidcosta.tmdb.enums

enum class MediaType {
    TV,
    MOVIE,
    PERSON;

    companion object {
        fun parse(mediaType: String): MediaType =
            when (mediaType.toLowerCase()) {
                "tv" -> TV
                "movie" -> MOVIE
                else -> PERSON
            }
    }
}