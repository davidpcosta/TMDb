package me.davidcosta.tmdb.ui.credits

import kotlinx.android.synthetic.main.activity_credits.*
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.base.BaseActivity
import me.davidcosta.tmdb.data.vo.CreditsVO
import me.davidcosta.tmdb.data.vo.TitleVO
import me.davidcosta.tmdb.enums.Keys

class CreditsActivity : BaseActivity() {
    override fun resourceView() =
        R.layout.activity_credits

    override fun setupView() {

        val media = intent.getSerializableExtra(Keys.EXTRAS_MEDIA.value) as TitleVO
        val credits = intent.getSerializableExtra(Keys.EXTRAS_CREDITS.value) as CreditsVO

        val sectionsPagerAdapter =
            SectionsPagerAdapter(
                this,
                credits
            )

        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        supportActionBar?.title = media.title
    }
}