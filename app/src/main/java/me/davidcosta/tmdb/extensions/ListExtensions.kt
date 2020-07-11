package me.davidcosta.tmdb.extensions

import me.davidcosta.tmdb.data.model.Genre

const val LIST_SEPARATOR = ", "

fun List<Genre>.toStringList(): String {
    this.let { genre ->
        return genre.joinToString (
            separator = LIST_SEPARATOR,
            transform = { it.name }
        )
    }
}


