package me.davidcosta.tmdb.extensions


fun Int.toRuntime(): Pair<Int, Int> {
    val hours = (this / 60) as Int
    val minutes = this - (hours * 60)
    return Pair(hours, minutes)
}