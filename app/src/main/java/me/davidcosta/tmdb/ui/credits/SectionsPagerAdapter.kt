package me.davidcosta.tmdb.ui.credits

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.credits_page.view.*
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.data.vo.CreditsVO
import me.davidcosta.tmdb.data.vo.PersonVO
import me.davidcosta.tmdb.ui.highlight.CreditListAdapter


private val TAB_TITLES = arrayOf(
    R.string.tab_cast,
    R.string.tab_crew
)

class SectionsPagerAdapter(val context: Context, val credits: CreditsVO) : PagerAdapter() {

    private lateinit var castViewManager: LinearLayoutManager
    private lateinit var castAdapter: CreditListAdapter

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount() =
        TAB_TITLES.size

    override fun getPageTitle(position: Int): CharSequence? {

        val count = if (position == 0) {
            credits.cast.size
        } else {
            credits.crew.size
        }

        return context.getString(TAB_TITLES[position], count)
    }

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.credits_page, collection, false)

        castViewManager = LinearLayoutManager(context)
        castAdapter = CreditListAdapter(context)

        view.credits_page_recyclerview_cast.apply {
            layoutManager = castViewManager
            adapter = castAdapter
        }

        if (position == 0) {
            castAdapter.persons = credits.cast
            castAdapter.notifyDataSetChanged()
        } else {
            castAdapter.persons = credits.crew.sortedBy { it.role }
            castAdapter.notifyDataSetChanged()
        }

        collection.addView(view)
        return view
    }
}