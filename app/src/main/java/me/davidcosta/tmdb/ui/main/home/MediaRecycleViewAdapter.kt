package me.davidcosta.tmdb.ui.main.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.data.model.Media
import me.davidcosta.tmdb.enums.Keys
import me.davidcosta.tmdb.enums.MediaType
import me.davidcosta.tmdb.extensions.loadPoster
import me.davidcosta.tmdb.extensions.toast
import me.davidcosta.tmdb.ui.highlight.movie.HighlightMovieActivity
import me.davidcosta.tmdb.ui.highlight.tv.HighlightTvActivity

class MediaRecycleViewAdapter(
    private val applicationContext: Context
): RecyclerView.Adapter<MediaRecycleViewAdapter.ViewHolder>() {

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
            goToMedia(media)
        }

        holder.itemView.setOnLongClickListener {
            applicationContext.toast(media.nameOrTile)
            true
        }
    }

    private fun goToMedia(media: Media) {
        if (media.getMediaType == MediaType.MOVIE) {
            val intent = Intent(applicationContext, HighlightMovieActivity::class.java)
            intent.putExtra(Keys.EXTRAS_MEDIA.value, media)
            applicationContext.startActivity(intent)
        } else {
            val intent = Intent(applicationContext, HighlightTvActivity::class.java)
            intent.putExtra(Keys.EXTRAS_MEDIA.value, media)
            applicationContext.startActivity(intent)
        }
    }
}

