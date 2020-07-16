package me.davidcosta.tmdb.ui.highlight.movie

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_highlight_movie.*
import kotlinx.android.synthetic.main.activity_highlight_movie.activity_highlight_backdrop
import kotlinx.android.synthetic.main.activity_highlight_movie.activity_highlight_button_add_to_watchlist
import kotlinx.android.synthetic.main.activity_highlight_movie.activity_highlight_genres
import kotlinx.android.synthetic.main.activity_highlight_movie.activity_highlight_label_overview
import kotlinx.android.synthetic.main.activity_highlight_movie.activity_highlight_original_language
import kotlinx.android.synthetic.main.activity_highlight_movie.activity_highlight_original_title
import kotlinx.android.synthetic.main.activity_highlight_movie.activity_highlight_overview
import kotlinx.android.synthetic.main.activity_highlight_movie.activity_highlight_poster
import kotlinx.android.synthetic.main.activity_highlight_movie.activity_highlight_release_date
import kotlinx.android.synthetic.main.activity_highlight_movie.activity_highlight_status
import kotlinx.android.synthetic.main.activity_highlight_movie.activity_highlight_vote_avarege
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.data.model.Movie
import me.davidcosta.tmdb.data.model.Media
import me.davidcosta.tmdb.enums.Keys
import me.davidcosta.tmdb.enums.Language
import me.davidcosta.tmdb.enums.MovieStatus
import me.davidcosta.tmdb.extensions.*


class HighlightMovieActivity : AppCompatActivity() {

    private lateinit var highlightViewModel: HighlightMovieViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var media: Media
    private var sessionId: String? = null
    private var accountId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highlight_movie)

        highlightViewModel = ViewModelProvider(this,
            HighlightMovieViewModelFactory(this)
        ).get(HighlightMovieViewModel::class.java)
        media = intent.getSerializableExtra(Keys.EXTRAS_MEDIA.value) as Media
        sharedPreferences = getSharedPreferences(Keys.PREFERENCES_USER_LOGIN.value, Context.MODE_PRIVATE)
        sessionId = sharedPreferences.getString(Keys.PREFERENCES_ACCOUNT_ID.value, null)
        accountId = sharedPreferences.getLong(Keys.PREFERENCES_SESSION_ID.value, 0)

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
        highlightViewModel.isOnWatchlist(media)
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
            highlightViewModel.addToWatchlist(accountId, sessionId, media)
                .observe(this, Observer {
                    if (it.statusCode == 1 || it.statusCode == 12) {
                        highlightViewModel.isOnWatchlist(media)
                        toast(getString(R.string.activity_highlight_message_added_to_watchlist))
                    }
                })
        } else {
            highlightViewModel.removeFromWatchlist(accountId, sessionId, media)
                .observe(this, Observer {
                    if (it.statusCode == 13) {
                        highlightViewModel.isOnWatchlist(media)
                        toast(getString(R.string.activity_highlight_message_removed_from_watchlist))
                    }
                })
        }
    }

    private fun setViewData() {
        media.posterPath?.let { activity_highlight_poster.loadPoster(applicationContext, it) }
        media.backdropPath?.let { activity_highlight_backdrop.loadBackdrop(applicationContext, it) }

        if (media.overview.isNullOrBlank()) {
            activity_highlight_label_overview.hide()
            activity_highlight_overview.hide()
        } else {
            activity_highlight_overview.show()
            activity_highlight_overview.text = media.overview
        }    }


    private fun fetchMovieDetails() {
        highlightViewModel.movieDetails(media.id)
        highlightViewModel.movieDetails.observe(this, Observer<Movie> {
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
        // TODO(Implement)
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
            getString(value.toEnum<MovieStatus>().label)
        } catch(e: Exception) {
            value
        }
    }

}