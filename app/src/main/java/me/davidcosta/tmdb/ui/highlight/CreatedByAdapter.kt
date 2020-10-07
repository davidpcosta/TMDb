package me.davidcosta.tmdb.ui.highlight

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.data.vo.PersonVO

class CreatedByAdapter(applicationContext: Context): BaseAdapter() {

    private var inflater: LayoutInflater = LayoutInflater.from(applicationContext)
    var credits: List<PersonVO> = ArrayList()

    override fun getCount(): Int {
        return credits.size
    }

    override fun getItem(i: Int): PersonVO {
        return credits[i]
    }

    override fun getItemId(i: Int): Long {
        return credits[i].id
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View?
        val viewHolder: ViewHolder
        val personVO = getItem(position)

        if (convertView == null) {
            view = inflater.inflate(R.layout.created_by_item, parent, false)
            viewHolder =
                ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.name.text = personVO.name
        viewHolder.jobRole.text = personVO.job

        return view
    }

    private class ViewHolder(view: View?) {
        val name = view?.findViewById(R.id.created_by_item_name) as TextView
        val jobRole = view?.findViewById(R.id.created_by_item_job_role) as TextView
    }
}

