package me.davidcosta.tmdb.ui.highlight

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.credit_list_item.view.*
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.data.vo.PersonVO
import me.davidcosta.tmdb.extensions.loadProfilePicture

class CreditListAdapter(
    private val applicationContext: Context
): RecyclerView.Adapter<CreditListAdapter.ViewHolder>() {

    private var inflater: LayoutInflater = LayoutInflater.from(applicationContext)
    var persons: List<PersonVO> = ArrayList()


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val profilePicture = view.credit_list_profile_picture as ImageView
        val personName = view.credit_list_person_name as TextView
        val personRole = view.credit_list_person_role as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.credit_list_item, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return persons.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = persons[position]

        holder.personName.text = person.name
        holder.personRole.text = person.role
        person.profilePath?.let {
            holder.profilePicture.loadProfilePicture(applicationContext, it)
            return
        }
        holder.profilePicture.setImageResource(R.drawable.avatar_placeholder)
    }
}

