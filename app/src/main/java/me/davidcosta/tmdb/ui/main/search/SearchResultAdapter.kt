package me.davidcosta.tmdb.ui.main.search
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.search_result_item.view.*
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.base.BaseAdapter
import me.davidcosta.tmdb.data.vo.TitleVO
import me.davidcosta.tmdb.enums.Keys
import me.davidcosta.tmdb.extensions.cropText
import me.davidcosta.tmdb.extensions.loadProfilePicture
import me.davidcosta.tmdb.extensions.toVoteAverageColorRank
import me.davidcosta.tmdb.extensions.toast
import me.davidcosta.tmdb.ui.highlight.HighlightActivity

private const val OVERVIEW_CROPPED_SIZE = 150

class SearchResultAdapter(applicationContext: Context) :
        BaseAdapter<TitleVO, SearchResultAdapter.ViewHolder>(applicationContext) {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val poster = view.search_result_item_poster as ImageView
        val title = view.search_result_item_title as TextView
        val voteAverage = view.search_result_item_vote_average as TextView
        val overview = view.search_result_item_overview as TextView
    }

    override fun getViewHolderInstance(view: View) =
        ViewHolder(view)

    override fun resourceView() =
        R.layout.search_result_item

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.title.text = item.title
        holder.voteAverage.text = applicationContext.getString(
            R.string.activity_highlight_value_vote_average,
            (item.voteAverage * 10).toInt()
        )

        holder.voteAverage.setTextColor(toVoteAverageColorRank(item.voteAverage, applicationContext))

        holder.overview.text = item.overview?.cropText(OVERVIEW_CROPPED_SIZE)

        if (item.posterPath != null) {
            holder.poster.loadProfilePicture(applicationContext, item.posterPath!!)
        } else {
            holder.poster.setImageResource(R.drawable.movie_poster_placeholder)
        }

        holder.itemView.setOnClickListener {
            goToMedia(item)
        }

        holder.itemView.setOnLongClickListener {
            applicationContext.toast(item.title)
            true
        }
    }

    private fun goToMedia(titleVO: TitleVO) {
        val intent = Intent(applicationContext, HighlightActivity::class.java)
        intent.putExtra(Keys.EXTRAS_MEDIA.value, titleVO)
        applicationContext.startActivity(intent)
    }
}

