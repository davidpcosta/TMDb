package me.davidcosta.tmdb.ui.main.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import me.davidcosta.tmdb.BuildConfig
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.data.model.Media
import me.davidcosta.tmdb.extensions.loadPoster
import me.davidcosta.tmdb.extensions.toast
import me.davidcosta.tmdb.ui.highlight.HighlightActivity

class MovieRecycleViewAdapter(
    private val applicationContext: Context
): RecyclerView.Adapter<MovieRecycleViewAdapter.ViewHolder>() {

    private var inflater: LayoutInflater = LayoutInflater.from(applicationContext)
    var medias: List<Media> = ArrayList()


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val poster: ImageView = view.findViewById(R.id.poster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.movie_item_home, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return medias.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val media = medias[position]
        holder.poster.contentDescription = media.nameOrTile

        media.posterPath?.let {
            holder.poster.loadPoster(applicationContext, it)
        }

        holder.itemView.setOnClickListener {
            goToMovie(media)
        }

        holder.itemView.setOnLongClickListener {
            applicationContext.toast(media.nameOrTile)
            true
        }
    }

    private fun goToMovie(media: Media) {
        val intent = Intent(applicationContext, HighlightActivity::class.java)
        intent.putExtra(applicationContext.getString(R.string.const_key_media), media)
        applicationContext.startActivity(intent)
    }
}

