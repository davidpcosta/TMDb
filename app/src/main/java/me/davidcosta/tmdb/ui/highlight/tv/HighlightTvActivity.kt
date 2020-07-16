package me.davidcosta.tmdb.ui.highlight.tv

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_highlight_tv.*
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.data.model.Media
import me.davidcosta.tmdb.data.model.Tv
import me.davidcosta.tmdb.enums.Keys
import me.davidcosta.tmdb.enums.Language
import me.davidcosta.tmdb.enums.MovieStatus
import me.davidcosta.tmdb.enums.TvStatus
import me.davidcosta.tmdb.extensions.*


class HighlightTvActivity : AppCompatActivity() {

    private lateinit var highlightViewModel: HighlightTvViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var media: Media
    private var sessionId: String? = null
    private var accountId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highlight_tv)

        highlightViewModel = ViewModelProvider(this, HighlightTvViewModelFactory(this)).get(HighlightTvViewModel::class.java)
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
        fetchTvDetails()
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
        }
    }


    private fun fetchTvDetails() {
        highlightViewModel.tvDetails(media.id)
        highlightViewModel.tv.observe(this, Observer<Tv> {tv ->
            activity_highlight_vote_avarege.text = getString(
                R.string.activity_highlight_value_vote_average,
                (tv.voteAverage * 10).toInt()
            )

            activity_highlight_tv_season_count.text = getString(
                when (tv.numberOfSeasons) {
                    1 -> R.string.activity_highlight_tv_value_seasons_count_singular
                    else -> R.string.activity_highlight_tv_value_seasons_count_plural
                },
                tv.numberOfSeasons
            )
            activity_highlight_genres.text = tv.genres.toStringList()

            activity_highlight_original_title.text = tv.originalName
            activity_highlight_release_date.text = tv.firstAirData.toLongFormat()
            activity_highlight_original_language.text = getLanguage(tv.originalLanguage)
            activity_highlight_status.text = getStatus(tv.status)
        })
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
            getString(value.toEnum<TvStatus>().label)
        } catch(e: Exception) {
            value
        }
    }

}