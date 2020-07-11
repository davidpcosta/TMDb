package me.davidcosta.tmdb.ui.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.data.model.Genre
import me.davidcosta.tmdb.extensions.hide
import me.davidcosta.tmdb.extensions.show

class GenreAdapter(
    applicationContext: Context,
    private val homeViewModel: HomeViewModel,
    private val lifecycleOwner: LifecycleOwner
): RecyclerView.Adapter<GenreAdapter.ViewHolder>() {

    private lateinit var viewManager: LinearLayoutManager
    private lateinit var movieAdapter: MovieRecycleViewAdapter
    private var inflater: LayoutInflater = LayoutInflater.from(applicationContext)
    var genres: List<Genre> = ArrayList()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val genre: TextView = view.findViewById(R.id.genre_name)
        val moviesRecycleView: RecyclerView = view.findViewById(R.id.movies_list)
        val loading: ProgressBar = view.findViewById(R.id.loading)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.genre_item, parent, false) as View
        val viewHolder = ViewHolder(view)

        viewManager = LinearLayoutManager(parent.context)
        viewManager.orientation = LinearLayoutManager.HORIZONTAL
        movieAdapter = MovieRecycleViewAdapter(parent.context)

        viewHolder.moviesRecycleView.tag = movieAdapter
        viewHolder.moviesRecycleView.apply {
            layoutManager = viewManager
            adapter = movieAdapter
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return genres.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val genre = genres[position]
        holder.genre.text = genre.name
        val adapter = holder.moviesRecycleView.tag as MovieRecycleViewAdapter

        genre.movies?.let {
            adapter.movies = it
            adapter.notifyDataSetChanged()
            return // previne carregar sempre
        }

        homeViewModel.fetchMoviesByGenre(genre.id)
        homeViewModel.movies.observe(lifecycleOwner, Observer {
            genre.movies = it
            adapter.movies = it
            adapter.notifyDataSetChanged()
            holder.loading.hide()
            holder.moviesRecycleView.show()
        })
    }
}

