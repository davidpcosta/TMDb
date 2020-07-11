package me.davidcosta.tmdb.extensions

import java.util.*

fun String.normalizeToEnumName(): String =
    this.replace(' ', '_').toUpperCase(Locale.getDefault());

inline fun <reified T : Enum<T>> String.toEnum(): T =
    enumValueOf(this.normalizeToEnumName())