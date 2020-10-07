package me.davidcosta.tmdb.extensions

import java.util.*

fun String.normalizeToEnumName(): String =
    this.replace(' ', '_').toUpperCase(Locale.getDefault());

inline fun <reified T : Enum<T>> String.toEnum(): T =
    enumValueOf(this.normalizeToEnumName())

fun String.cropText(charactersCount: Int): String {
    if (this.length >= charactersCount) {
        return this.substring(0, charactersCount) + "..."
    }
    return this
}
