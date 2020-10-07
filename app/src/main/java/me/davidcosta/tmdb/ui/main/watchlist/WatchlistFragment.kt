package me.davidcosta.tmdb.ui.main.watchlist

import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.base.BaseFragment
import me.davidcosta.tmdb.data.model.Media
import me.davidcosta.tmdb.data.vo.TitleVO
import me.davidcosta.tmdb.enums.Keys
import me.davidcosta.tmdb.extensions.toast
import me.davidcosta.tmdb.ui.highlight.HighlightActivity

class WatchlistFragment : BaseFragment() {

    private lateinit var watchlistViewModel: WatchlistViewModel
    private lateinit var watchlistGrid: GridView
    private lateinit var movieAdapter: MovieAdapter

    override fun resourceView() =
        R.layout.fragment_watchlist

    override fun onResume() {
        super.onResume()
        watchlistViewModel.fetchWatchlist()
    }

    override fun setupView(view: View) {
        watchlistViewModel = ViewModelProvider(this, WatchlistViewModelFactory(requireActivity().applicationContext)).get(WatchlistViewModel::class.java)
        movieAdapter = MovieAdapter(requireActivity().applicationContext)
        watchlistGrid = view.findViewById<GridView>(R.id.watchlist).apply {
            adapter = movieAdapter
            onItemClickListener =  AdapterView.OnItemClickListener { _, _, position, _ ->
                goToHighlight(adapter.getItem(position) as TitleVO)
            }
            onItemLongClickListener = AdapterView.OnItemLongClickListener { _, _, position, _ ->
                val media = adapter.getItem(position) as Media
                requireActivity().toast(media.nameOrTile)
                true
            }
        }

        observeWatchlist()
    }

    private fun observeWatchlist() {
        watchlistViewModel.medias.observe(viewLifecycleOwner, Observer {
            movieAdapter.titles = it.sortedBy { title -> title.title }
            movieAdapter.notifyDataSetChanged()
        })
    }

    private fun goToHighlight(titleVO: TitleVO) {
        val intent = Intent(requireContext(), HighlightActivity::class.java)
        intent.putExtra(Keys.EXTRAS_MEDIA.value, titleVO)
        requireActivity().startActivity(intent)
    }
}
