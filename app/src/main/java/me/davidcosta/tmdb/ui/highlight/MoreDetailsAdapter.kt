package me.davidcosta.tmdb.ui.highlight

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.davidcosta.tmdb.R

class MoreDetailsAdapter(applicationContext: Context):
    RecyclerView.Adapter<MoreDetailsAdapter.ViewHolder>() {

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

