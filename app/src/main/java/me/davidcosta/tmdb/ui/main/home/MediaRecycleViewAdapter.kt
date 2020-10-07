package me.davidcosta.tmdb.ui.main.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.data.vo.TitleVO
import me.davidcosta.tmdb.enums.Keys
import me.davidcosta.tmdb.extensions.loadPoster
import me.davidcosta.tmdb.extensions.toVoteAverageColorRank
import me.davidcosta.tmdb.extensions.toast
import me.davidcosta.tmdb.ui.highlight.HighlightActivity

class MediaRecycleViewAdapter(
    private val applicationContext: Context
): RecyclerView.Adapter<MediaRecycleViewAdapter.ViewHolder>() {

    private var inflater: LayoutInflater = LayoutInflater.from(applicationContext)
    var titles: List<TitleVO> = ArrayList()


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val poster: ImageView = view.findViewById(R.id.movie_item_home_poster)
        val voteAverage: TextView = view.findViewById(R.id.movie_item_home_vote_average)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.movie_item_home, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val titleVO = titles[position]
        holder.poster.contentDescription = titleVO.title

        holder.voteAverage.text = applicationContext.getString(
            R.string.activity_highlight_value_vote_average,
            (titleVO.voteAverage * 10).toInt()
        )

        holder.voteAverage.setTextColor(toVoteAverageColorRank(titleVO.voteAverage, applicationContext))

        titleVO.posterPath?.let {
            holder.poster.loadPoster(applicationContext, it)
        }

        holder.itemView.setOnClickListener {
            goToMedia(titleVO)
        }

        holder.itemView.setOnLongClickListener {
            applicationContext.toast(titleVO.title)
            true
        }
    }

    private fun goToMedia(titleVO: TitleVO) {
        val intent = Intent(applicationContext, HighlightActivity::class.java)
        intent.putExtra(Keys.EXTRAS_MEDIA.value, titleVO)
        applicationContext.startActivity(intent)
    }
}

