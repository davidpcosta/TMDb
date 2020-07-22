package me.davidcosta.tmdb.ui.highlight

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.data.model.Media
import me.davidcosta.tmdb.enums.Keys
import me.davidcosta.tmdb.enums.MediaType
import me.davidcosta.tmdb.extensions.loadPoster
import me.davidcosta.tmdb.extensions.toast
import me.davidcosta.tmdb.ui.highlight.movie.HighlightMovieActivity
import me.davidcosta.tmdb.ui.highlight.tv.HighlightTvActivity
import org.w3c.dom.Text

class MoreDetailsAdapter(
    private val applicationContext: Context
): RecyclerView.Adapter<MoreDetailsAdapter.ViewHolder>() {

    private var inflater: LayoutInflater = LayoutInflater.from(applicationContext)
    var items: List<Pair<String, String>> = ArrayList()


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val label: TextView = view.findViewById(R.id.more_details_item_label)
        val value: TextView = view.findViewById(R.id.more_details_item_value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.more_details_item, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.label.text = item.first
        holder.value.text = item.second
    }
}

