package me.davidcosta.tmdb.ui.highlight

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.data.model.Movie

class SectionsPagerAdapter(private val movie: Movie, private val context: Context, fm: FragmentManager) :
FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val TAB_TITLES = arrayOf(
        R.string.activity_highlight_tab_similar,
        R.string.activity_highlight_tab_details
    )

    override fun getItem(position: Int): Fragment {

        return PlaceholderFragment.newInstance(movie,position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }
}