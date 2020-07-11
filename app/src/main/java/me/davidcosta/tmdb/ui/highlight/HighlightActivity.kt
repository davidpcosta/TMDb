package me.davidcosta.tmdb.ui.highlight

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_highlight.*
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.data.model.Cast
import me.davidcosta.tmdb.data.model.Movie
import me.davidcosta.tmdb.data.model.MovieDetails
import me.davidcosta.tmdb.enums.Language
import me.davidcosta.tmdb.enums.Status
import me.davidcosta.tmdb.extensions.*
import java.util.*


class HighlightActivity : AppCompatActivity() {

    private lateinit var highlightViewModel: HighlightViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var movie: Movie
    private var sessionId: String? = null
    private var accountId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highlight)

        highlightViewModel = ViewModelProvider(this, HighlightViewModelFactory(this)).get(HighlightViewModel::class.java)
        movie = intent.getSerializableExtra(getString(R.string.const_key_movie)) as Movie
        sharedPreferences = getSharedPreferences(getString(R.string.const_shared_preference), Context.MODE_PRIVATE)
        sessionId = sharedPreferences.getString(getString(R.string.const_key_session_id), null)
        accountId = sharedPreferences.getLong(getString(R.string.const_key_account_id), 0)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        activity_highlight_button_add_to_watchlist.setOnClickListener {
            this.handleWatchlistButtonClicked()
        }

        initComponents()
        setViewData()
        fetchMovieDetails()
        fetchMovieCast()
    }

    private fun initComponents() {
        highlightViewModel.isOnWatchlist(movie)
        highlightViewModel.isOnWatchlist.observe(this, Observer {
            if (it) {
                activity_highlight_button_add_to_watchlist.setIconResource(R.drawable.ic_done_black_24dp)
            } else {
                activity_highlight_button_add_to_watchlist.setIconResource(R.drawable.ic_add_black_24dp)

            }
        })
    }

    fun handleWatchlistButtonClicked() {
        if (highlightViewModel.isOnWatchlist.value == false) {
            highlightViewModel.addToWatchlist(accountId, sessionId, movie)
                .observe(this, Observer {
                    if (it.statusCode == 1 || it.statusCode == 12) {
                        highlightViewModel.isOnWatchlist(movie)
                        toast(getString(R.string.activity_highlight_message_added_to_watchlist))
                    }
                })
        } else {
            highlightViewModel.removeFromWatchlist(accountId, sessionId, movie)
                .observe(this, Observer {
                    if (it.statusCode == 13) {
                        highlightViewModel.isOnWatchlist(movie)
                        toast(getString(R.string.activity_highlight_message_removed_from_watchlist))
                    }
                })
        }
    }

    private fun setViewData() {
        movie.posterPath?.let { activity_highlight_poster.loadPoster(applicationContext, it) }
        movie.backdropPath?.let { activity_highlight_backdrop.loadBackdrop(applicationContext, it) }
        activity_highlight_overview.text = movie.overview
    }


    private fun fetchMovieDetails() {
        highlightViewModel.movieDetails(movie.id)
        highlightViewModel.movieDetails.observe(this, Observer<MovieDetails> {
            activity_highlight_vote_avarege.text = getString(
                R.string.activity_highlight_value_vote_average,
                (it.voteAverage * 10).toInt()
            )
            activity_highlight_runtime.text = getString(
                R.string.activity_highlight_value_runtime,
                it.runtime
            )
            activity_highlight_year.text = it.releaseDate.toYearFormat()
            activity_highlight_genres.text = it.genres.toStringList()

            activity_highlight_original_title.text = it.originalTitle
            activity_highlight_release_date.text = it.releaseDate.toLongFormat()
            activity_highlight_original_language.text = getLanguage(it.originalLanguage)
            activity_highlight_status.text = getStatus(it.status)
            activity_highlight_budget.text = getString(R.string.activity_highlight_value_money, it.budget)
            activity_highlight_revenue.text = getString(R.string.activity_highlight_value_money, it.revenue)
        })
    }

    private fun fetchMovieCast() {
//        highlightViewModel.fetchCredits(movie.id)
//        highlightViewModel.cast.observe(this, Observer<List<Cast>> {
//            cast.text = formatHtmlTextView(getString(R.string.activity_highlight_label_cast), castToStringList(it))
//        })
    }

    private fun castToStringList(castLis: List<Cast>?): String {
        val maxCast = 15
        castLis?.let { cast ->
            val newMaxCast = if (cast.size < maxCast) cast.size else maxCast
            return cast.subList(0, newMaxCast).joinToString {
                it.name
            }
        }
        return ""
    }

    private fun getLanguage(value: String): String {
        return try {
            getString(value.toEnum<Language>().label)
        } catch(e: Exception) {
            value
        }
    }

    private fun getStatus(value: String): String {
        return try {
            getString(value.toEnum<Status>().label)
        } catch(e: Exception) {
            value
        }
    }

}