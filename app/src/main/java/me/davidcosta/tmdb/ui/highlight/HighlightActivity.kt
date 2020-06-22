package me.davidcosta.tmdb.ui.highlight

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation
import me.davidcosta.tmdb.BuildConfig
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.data.model.Movie
import me.davidcosta.tmdb.databinding.ActivityHighlightBinding
import me.davidcosta.tmdb.toast


class HighlightActivity : AppCompatActivity() {

    private lateinit var highlightViewModel: HighlightViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var movie: Movie
    private var sessionId: String? = null
    private var accountId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityHighlightBinding>(this, R.layout.activity_highlight).apply {
            this.activity = this@HighlightActivity
        }

        highlightViewModel = ViewModelProvider(this, HighlightViewModelFactory(this)).get(HighlightViewModel::class.java)
        movie = intent.getSerializableExtra(getString(R.string.const_key_movie)) as Movie
        sharedPreferences = getSharedPreferences(getString(R.string.const_shared_preference), Context.MODE_PRIVATE)
        sessionId = sharedPreferences.getString(getString(R.string.const_key_session_id), null)
        accountId = sharedPreferences.getLong(getString(R.string.const_key_account_id), 0)

        initComponents()
        setViewData()
    }

    private fun initComponents() {
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = SectionsPagerAdapter(movie,this, supportFragmentManager)

        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        val watchlistButton: ImageButton = findViewById(R.id.add_to_watchlist_button)
        highlightViewModel.isOnWatchlist(movie)
        highlightViewModel.isOnWatchlist.observe(this, Observer {
            if (it) {
                watchlistButton.setImageResource(R.drawable.ic_done_black_24dp)
            } else {
                watchlistButton.setImageResource(R.drawable.ic_add_black_24dp)
            }
        })
    }

    fun handleWatchlistButtonClicked() {
        if (highlightViewModel.isOnWatchlist.value == false) {
            highlightViewModel.addToWatchlist(accountId, sessionId, movie)
                .observe(this, Observer {
                    if (it.statusCode == 1 || it.statusCode == 12) {
                        highlightViewModel.isOnWatchlist(movie)
                        toast(getString(R.string.highlight_message_watchlist_added))
                    }
                })
        } else {
            highlightViewModel.removeFromWatchlist(accountId, sessionId, movie)
                .observe(this, Observer {
                    if (it.statusCode == 13) {
                        highlightViewModel.isOnWatchlist(movie)
                        toast(getString(R.string.highlight_message_watchlist_removed))
                    }
                })
        }
    }

    fun handlePlayButtonClicked() {
        toast("Não implementado :(")
    }

    private fun setViewData() {
        val moviePoster: ImageView = findViewById(R.id.movie_poster)
        Picasso.with(applicationContext)
            .load(BuildConfig.TMDB_IMAGE_URL + movie.posterPath)
            .into(moviePoster)

        val movieBackdrop: ImageView = findViewById(R.id.movie_backdrop)
        Picasso.with(applicationContext)
            .load(BuildConfig.TMDB_IMAGE_URL + movie.backdropPath)
            .transform(BlurTransformation(applicationContext, 5, 1))
            .into(movieBackdrop)

        val movieTitle: TextView = findViewById(R.id.movie_title)
        movieTitle.text = movie.title

        val movieOverview: TextView = findViewById(R.id.movie_overview)
        movieOverview.text = movie.overview
    }

}