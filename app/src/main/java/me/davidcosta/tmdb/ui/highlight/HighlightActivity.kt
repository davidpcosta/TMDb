package me.davidcosta.tmdb.ui.highlight

import android.annotation.SuppressLint
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_highlight.*
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.base.BaseActivity
import me.davidcosta.tmdb.data.vo.CreditsVO
import me.davidcosta.tmdb.data.vo.TitleDetailsVO
import me.davidcosta.tmdb.data.vo.TitleVO
import me.davidcosta.tmdb.enums.Keys
import me.davidcosta.tmdb.enums.Language
import me.davidcosta.tmdb.enums.MovieStatus
import me.davidcosta.tmdb.extensions.*

class HighlightActivity : BaseActivity() {

    private lateinit var highlightMovieViewModel: HighlightViewModel
    private lateinit var titleVO: TitleVO
    private lateinit var createdByAdapter: CreatedByAdapter


    override fun resourceView() =
        R.layout.activity_highlight

    override fun setupView() {

        highlightMovieViewModel = ViewModelProvider(this,
            HighlightViewModelFactory(this)
        ).get(HighlightViewModel::class.java)

        titleVO = intent.getSerializableExtra(Keys.EXTRAS_MEDIA.value) as TitleVO

        highlightMovieViewModel.isOnWatchlist(titleVO)
        highlightMovieViewModel.isOnWatchlist.observe(this, Observer {
            if (it) {
                activity_highlight_button_add_to_watchlist.setIconResource(R.drawable.ic_done_24dp)
            } else {
                activity_highlight_button_add_to_watchlist.setIconResource(R.drawable.ic_add_24dp)

            }
        })

        activity_highlight_button_add_to_watchlist.setOnClickListener {
            this.handleWatchlistButtonClicked()
        }

        createdByAdapter =
            CreatedByAdapter(applicationContext)
        activity_highlight_created_by .apply {
            adapter = createdByAdapter
        }

        setViewData()
        fetchTitleDetails()
    }

    private fun handleWatchlistButtonClicked() {
        if (!highlightMovieViewModel.isOnWatchlist(titleVO)) {
            highlightMovieViewModel.addToWatchlist(titleVO)
            toast(getString(R.string.activity_highlight_message_added_to_watchlist))
        } else {
            highlightMovieViewModel.removeFromWatchlist(titleVO)
            toast(getString(R.string.activity_highlight_message_removed_from_watchlist))
        }
    }

    private fun setViewData() {
        supportActionBar?.title = titleVO.title
        titleVO.posterPath?.let { activity_highlight_poster.loadPoster(applicationContext, it) }
        titleVO.backdropPath?.let { activity_highlight_backdrop.loadBackdrop(applicationContext, it) }

        if (titleVO.overview.isNullOrBlank()) {
            activity_highlight_overview.text = getString(R.string.activity_highlight_message_overview_not_available)
        } else {
            activity_highlight_overview.text = titleVO.overview
        }

        activity_highlight_vote_avarege.text = getString(
            R.string.activity_highlight_value_vote_average,
            (titleVO.voteAverage * 10).toInt()
        )

        activity_highlight_vote_avarege.setTextColor(toVoteAverageColorRank(titleVO.voteAverage, applicationContext))
    }


    @SuppressLint("StringFormatMatches")
    private fun fetchTitleDetails() {
        highlightMovieViewModel.fetchTitleDetails(titleVO.id, titleVO.mediaType)
        highlightMovieViewModel.titleDetailsDetails.observe(this, Observer { titleVO ->
            titleVO?.let {
                activity_highlight_runtime.text = toFormattedRuntime(titleVO.runtime , applicationContext)
                activity_highlight_year.text = titleVO.releaseDate?.toYearFormat() ?: "-"
                setMoreDetailsData(titleVO)

                titleVO.credits?.let { creditsVO ->
                    setTopBilledCast(creditsVO)

                    createdByAdapter.credits = creditsVO.crew.filter {
                        it.department == "Writing"
                    }
                    createdByAdapter.notifyDataSetChanged()
                }
            }
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
            getString(value.toEnum<MovieStatus>().label)
        } catch(e: Exception) {
            value
        }
    }

    private fun setMoreDetailsData(titleDetailsVO: TitleDetailsVO) =
        with(activity_highlight_more_details as MoreDetailsFragment) {
            setItems(
                listOf(
                    Pair(getString(R.string.fragment_more_details_label_original_title), titleDetailsVO.originalTitle),
                    Pair(getString(R.string.fragment_more_details_label_genres), titleDetailsVO.genres.joinToString(separator = ", ")),
                    Pair(getString(R.string.fragment_more_details_label_release_date), titleDetailsVO.releaseDate?.toLongFormat() ?: "-"),
                    Pair(getString(R.string.fragment_more_details_label_original_language), getLanguage(titleDetailsVO.originalLanguage)),
                    Pair(getString(R.string.fragment_more_details_label_status), getString(titleDetailsVO.status.label)),
                    Pair(getString(R.string.fragment_more_details_label_budged), getString(R.string.activity_highlight_value_money, titleDetailsVO.budget)),
                    Pair(getString(R.string.fragment_more_details_label_revenue), getString(R.string.activity_highlight_value_money, titleDetailsVO.revenue))
                )
            )
        }

    private fun setTopBilledCast(credits: CreditsVO) =
        with(activity_highlight_cast as CastRailFragment) {
            setCredits(credits)
        }

}