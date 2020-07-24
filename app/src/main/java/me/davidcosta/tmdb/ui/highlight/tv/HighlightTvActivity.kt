package me.davidcosta.tmdb.ui.highlight.tv

import android.widget.GridView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_highlight_tv.*
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.base.BaseActivity
import me.davidcosta.tmdb.data.model.Credits
import me.davidcosta.tmdb.data.model.Media
import me.davidcosta.tmdb.data.model.Tv
import me.davidcosta.tmdb.enums.Keys
import me.davidcosta.tmdb.enums.Language
import me.davidcosta.tmdb.enums.TvStatus
import me.davidcosta.tmdb.extensions.*
import me.davidcosta.tmdb.ui.highlight.CastRailFragment
import me.davidcosta.tmdb.ui.highlight.MoreDetailsFragment


class HighlightTvActivity : BaseActivity() {

    private lateinit var highlightViewModel: HighlightTvViewModel
    private lateinit var media: Media
    private lateinit var createdByGrid: GridView
    private lateinit var createdByAdapter: CreatedByAdapter

    override fun resourceView() =
        R.layout.activity_highlight_tv

    override fun setupView() {
        highlightViewModel = ViewModelProvider(this, HighlightTvViewModelFactory(this)).get(HighlightTvViewModel::class.java)
        media = intent.getSerializableExtra(Keys.EXTRAS_MEDIA.value) as Media

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        activity_highlight_tv_button_add_to_watchlist.setOnClickListener {
            this.handleWatchlistButtonClicked()
        }

        createdByAdapter = CreatedByAdapter(applicationContext)
        activity_highlight_tv_created_by .apply {
            adapter = createdByAdapter
        }

        highlightViewModel.isOnWatchlist(media)
        highlightViewModel.isOnWatchlist.observe(this, Observer {
            if (it) {
                activity_highlight_tv_button_add_to_watchlist.setIconResource(R.drawable.ic_done_24dp)
            } else {
                activity_highlight_tv_button_add_to_watchlist.setIconResource(R.drawable.ic_add_24dp)

            }
        })

        setViewData()
        fetchTvDetails()
        fetchTvCredits()
    }

    private fun handleWatchlistButtonClicked() {
        if (highlightViewModel.isOnWatchlist(media)) {
            highlightViewModel.removeFromWatchlist(media)
            toast(getString(R.string.activity_highlight_message_removed_from_watchlist))
        } else {
            highlightViewModel.addToWatchlist(media)
            toast(getString(R.string.activity_highlight_message_added_to_watchlist))
        }
    }

    private fun setViewData() {
        media.posterPath?.let { activity_highlight_tv_poster.loadPoster(applicationContext, it) }
        media.backdropPath?.let { activity_highlight_tv_backdrop.loadBackdrop(applicationContext, it) }

        if (media.overview.isNullOrBlank()) {
            activity_highlight_tv_label_overview.hide()
            activity_highlight_tv_overview.hide()
        } else {
            activity_highlight_tv_overview.show()
            activity_highlight_tv_overview.text = media.overview
        }
    }

    private fun fetchTvDetails() {
        highlightViewModel.tvDetails(media.id)
        highlightViewModel.tvDetails.observe(this, Observer<Tv> { tv ->
            activity_highlight_tv_vote_avarege.text = getString(
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

            tv.createdBy?.let {
                createdByAdapter.credits = it
                createdByAdapter.notifyDataSetChanged()
            }

            setMoreDetailsData(tv)
        })
    }

    private fun fetchTvCredits() {
        highlightViewModel.fetchCredits(media.id)
        highlightViewModel.credits.observe(this, Observer<Credits> { credits ->
            setTopBilledCast(credits)
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

    private fun setMoreDetailsData(tv: Tv) {
        val moreDetailsFragment = activity_highlight_tv_more_details as MoreDetailsFragment
        moreDetailsFragment.setItems(
            listOf(
                Pair(getString(R.string.fragment_more_details_label_original_title), tv.originalName),
                Pair(getString(R.string.fragment_more_details_label_genres), tv.genres.toStringList()),
                Pair(getString(R.string.fragment_more_details_label_release_date), tv.firstAirData.toLongFormat()),
                Pair(getString(R.string.fragment_more_details_label_original_language), getLanguage(tv.originalLanguage)),
                Pair(getString(R.string.fragment_more_details_label_status), getStatus(tv.status))
            )
        )
    }

    private fun setTopBilledCast(credits: Credits) {
        val castRailFragment = activity_highlight_tv_cast as CastRailFragment
        val size = if (credits.cast.size > 10) 10 else credits.cast.size
        castRailFragment.setCast(credits.cast.subList(0, size))
    }

}