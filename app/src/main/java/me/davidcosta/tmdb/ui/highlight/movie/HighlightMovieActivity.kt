package me.davidcosta.tmdb.ui.highlight.movie

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_highlight_movie.*
import kotlinx.android.synthetic.main.activity_highlight_movie.activity_highlight_movie_backdrop
import kotlinx.android.synthetic.main.activity_highlight_movie.activity_highlight_movie_button_add_to_watchlist
import kotlinx.android.synthetic.main.activity_highlight_movie.activity_highlight_movie_label_overview
import kotlinx.android.synthetic.main.activity_highlight_movie.activity_highlight_movie_overview
import kotlinx.android.synthetic.main.activity_highlight_movie.activity_highlight_movie_poster
import kotlinx.android.synthetic.main.activity_highlight_movie.activity_highlight_movie_more_details
import kotlinx.android.synthetic.main.activity_highlight_movie.activity_highlight_movie_vote_avarege
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.base.BaseActivity
import me.davidcosta.tmdb.data.model.Credits
import me.davidcosta.tmdb.data.model.Media
import me.davidcosta.tmdb.data.model.Movie
import me.davidcosta.tmdb.enums.Keys
import me.davidcosta.tmdb.enums.Language
import me.davidcosta.tmdb.enums.MovieStatus
import me.davidcosta.tmdb.extensions.*
import me.davidcosta.tmdb.ui.highlight.CastRailFragment
import me.davidcosta.tmdb.ui.highlight.MoreDetailsFragment


class HighlightMovieActivity : BaseActivity() {

    private lateinit var highlightMovieViewModel: HighlightMovieViewModel
    private lateinit var media: Media

    override fun resourceView() =
        R.layout.activity_highlight_movie

    override fun setupView() {

        highlightMovieViewModel = ViewModelProvider(this,
            HighlightMovieViewModelFactory(this)
        ).get(HighlightMovieViewModel::class.java)

        media = intent.getSerializableExtra(Keys.EXTRAS_MEDIA.value) as Media

        supportActionBar?.setDisplayShowTitleEnabled(false)

        activity_highlight_movie_button_add_to_watchlist.setOnClickListener {
            this.handleWatchlistButtonClicked()
        }

        highlightMovieViewModel.isOnWatchlist(media)
        highlightMovieViewModel.isOnWatchlist.observe(this, Observer {
            if (it) {
                activity_highlight_movie_button_add_to_watchlist.setIconResource(R.drawable.ic_done_24dp)
            } else {
                activity_highlight_movie_button_add_to_watchlist.setIconResource(R.drawable.ic_add_24dp)

            }
        })

        setViewData()
        fetchMovieDetails()
        fetchMovieCredits()
    }

    private fun handleWatchlistButtonClicked() {
        if (!highlightMovieViewModel.isOnWatchlist(media)) {
            highlightMovieViewModel.addToWatchlist(media)
            toast(getString(R.string.activity_highlight_message_added_to_watchlist))
        } else {
            highlightMovieViewModel.removeFromWatchlist(media)
            toast(getString(R.string.activity_highlight_message_removed_from_watchlist))
        }
    }

    private fun setViewData() {
        media.posterPath?.let { activity_highlight_movie_poster.loadPoster(applicationContext, it) }
        media.backdropPath?.let { activity_highlight_movie_backdrop.loadBackdrop(applicationContext, it) }

        if (media.overview.isNullOrBlank()) {
            activity_highlight_movie_label_overview.hide()
            activity_highlight_movie_overview.hide()
        } else {
            activity_highlight_movie_overview.show()
            activity_highlight_movie_overview.text = media.overview
        }
    }


    @SuppressLint("StringFormatMatches")
    private fun fetchMovieDetails() {
        highlightMovieViewModel.movieDetails(media.id)
        highlightMovieViewModel.movieDetails.observe(this, Observer<Movie> { movie ->
            activity_highlight_movie_vote_avarege.text = getString(
                R.string.activity_highlight_value_vote_average,
                (movie.voteAverage * 10).toInt()
            )

            val runtime = movie.runtime.toRuntime()
            activity_highlight_movie_runtime.text = getString(
                R.string.activity_highlight_value_runtime,
                runtime.first,
                runtime.second
            )
            activity_highlight_movie_year.text = movie.releaseDate.toYearFormat()

            setMoreDetailsData(movie)
        })
    }

    private fun fetchMovieCredits() {
        highlightMovieViewModel.fetchCredits(media.id)
        highlightMovieViewModel.credits.observe(this, Observer<Credits> { credits ->
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
            getString(value.toEnum<MovieStatus>().label)
        } catch(e: Exception) {
            value
        }
    }

    private fun setMoreDetailsData(movie: Movie) {
        val moreDetailsFragment = activity_highlight_movie_more_details as MoreDetailsFragment
        moreDetailsFragment.setItems(
            listOf(
                Pair(getString(R.string.fragment_more_details_label_original_title), movie.originalTitle),
                Pair(getString(R.string.fragment_more_details_label_genres), movie.genres.toStringList()),
                Pair(getString(R.string.fragment_more_details_label_release_date), movie.releaseDate.toLongFormat()),
                Pair(getString(R.string.fragment_more_details_label_original_language), getLanguage(movie.originalLanguage)),
                Pair(getString(R.string.fragment_more_details_label_status), getStatus(movie.status)),
                Pair(getString(R.string.fragment_more_details_label_budged), getString(R.string.activity_highlight_value_money, movie.budget)),
                Pair(getString(R.string.fragment_more_details_label_revenue), getString(R.string.activity_highlight_value_money, movie.revenue))
            )
        )
    }

    private fun setTopBilledCast(credits: Credits) {

        val castRailFragment = activity_highlight_movie_cast as CastRailFragment
//        val size = if (credits.cast.size > 10) 10 else credits.cast.size
//        castRailFragment.setCast(credits.cast.subList(0, size))
        castRailFragment.setCredits(credits)
    }

}