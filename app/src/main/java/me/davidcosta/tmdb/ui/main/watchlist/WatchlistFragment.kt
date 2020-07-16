package me.davidcosta.tmdb.ui.main.watchlist

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.data.model.Media
import me.davidcosta.tmdb.enums.Keys
import me.davidcosta.tmdb.ui.highlight.movie.HighlightMovieActivity


class WatchlistFragment : Fragment() {

    private lateinit var watchlistViewModel: WatchlistViewModel
    private lateinit var watchlistGrid: GridView
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sessionId: String
    private var accountId: Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_main_fragment_watchlist, container, false)

        sharedPreferences = requireActivity().getSharedPreferences(Keys.PREFERENCES_USER_LOGIN.value, Context.MODE_PRIVATE)
        watchlistViewModel = ViewModelProvider(this, WatchlistViewModelFactory(requireActivity().applicationContext)).get(WatchlistViewModel::class.java)
        movieAdapter = MovieAdapter(requireActivity().applicationContext)
        sessionId = sharedPreferences.getString(Keys.PREFERENCES_SESSION_ID.value, "")!!
        accountId = sharedPreferences.getLong(Keys.PREFERENCES_ACCOUNT_ID.value, 0)

        // Grid view
        watchlistGrid = view.findViewById<GridView>(R.id.watchlist).apply {
            adapter = movieAdapter
            onItemClickListener =  AdapterView.OnItemClickListener { _, _, position, _ ->
                goToMedia(this.adapter.getItem(position) as Media)
            }
        }

        observeWatchlist()
        return view
    }

    override fun onResume() {
        super.onResume()
        watchlistViewModel.updateFromLocalWatchlist()
    }

    private fun observeWatchlist() {
        watchlistViewModel.fetchWatchlist(accountId, sessionId)
        watchlistViewModel.medias.observe(viewLifecycleOwner, Observer {
            movieAdapter.medias = it.sortedBy { media -> media.name }
            movieAdapter.notifyDataSetChanged()
        })
    }

    private fun goToMedia(media: Media) {
        val intent = Intent(requireActivity(), HighlightMovieActivity::class.java)
        intent.putExtra(Keys.EXTRAS_MEDIA.value, media)
        requireActivity().startActivity(intent)
    }
}
