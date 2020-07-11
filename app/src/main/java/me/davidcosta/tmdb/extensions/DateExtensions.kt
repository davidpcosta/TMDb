package me.davidcosta.tmdb.extensions

import java.text.SimpleDateFormat
import java.util.*

const val YEAR = "yyyy"
const val DEFAULT = "dd/MM/yyyy"

fun Date.toLongFormat(): String =
    SimpleDateFormat(DEFAULT, Locale.getDefault()).format(this)

fun Date.toYearFormat(): String =
    SimpleDateFormat(YEAR, Locale.getDefault()).format(this)

