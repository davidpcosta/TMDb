package me.davidcosta.tmdb.extensions

import android.content.Context
import androidx.core.content.ContextCompat
import me.davidcosta.tmdb.R

fun toFormattedRuntime(runtime: Int, context: Context): String  = run {
    val hours = (runtime / 60)
    val minutes = runtime - (hours * 60)
    when (hours) {
        0 -> context.getString(
            R.string.activity_highlight_value_runtime_minutes,
            minutes
        )
        else -> when (minutes) {
            0 -> context.getString(
                R.string.activity_highlight_value_runtime_hours,
                hours
            )
            else -> context.getString(
                R.string.activity_highlight_value_runtime,
                hours,
                minutes
            )
        }
    }
}

fun toVoteAverageColorRank(voteAverage: Double, context: Context) =
    ContextCompat.getColor(
        context,
        when ((voteAverage * 10).toInt()) {
            in 70..100 -> R.color.tmdb_vote_average_high
            in 50..69 -> R.color.tmdb_vote_average_medium
            else -> R.color.tmdb_vote_average_low
        }
    )
