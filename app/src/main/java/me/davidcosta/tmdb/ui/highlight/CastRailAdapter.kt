package me.davidcosta.tmdb.ui.highlight

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cast_rail_item.view.*
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.data.model.Cast
import me.davidcosta.tmdb.extensions.loadProfilePicture

class CastRailAdapter(
    private val applicationContext: Context
): RecyclerView.Adapter<CastRailAdapter.ViewHolder>() {

    private var inflater: LayoutInflater = LayoutInflater.from(applicationContext)
    var cast: List<Cast> = ArrayList()


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val profilePicture = view.cast_rail_profile_picture as ImageView
        val personName = view.cast_rail_person_name as TextView
        val charactetName = view.cast_rail_character_name as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.cast_rail_item, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return cast.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = cast[position]

        person.profilePath?.let {
            holder.profilePicture.loadProfilePicture(applicationContext, it)
        }
        holder.personName.text = person.name
        holder.charactetName.text = person.character
    }
}

