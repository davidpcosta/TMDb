package me.davidcosta.tmdb.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.davidcosta.tmdb.generated.callback.OnClickListener

abstract class BaseAdapter<T: Any, H: RecyclerView.ViewHolder?>(val applicationContext: Context):
    RecyclerView.Adapter<H>() {
    private var inflater: LayoutInflater = LayoutInflater.from(applicationContext)

    var items: List<T> = listOf()
    var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H {
        val view: View = inflater.inflate(resourceView(), parent, false)

        return getViewHolderInstance(view)
    }

    override fun getItemCount() =
        items.size


    abstract fun resourceView(): Int
    abstract fun getViewHolderInstance(view: View): H
}