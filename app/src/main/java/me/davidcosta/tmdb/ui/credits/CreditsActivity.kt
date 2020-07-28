package me.davidcosta.tmdb.ui.credits

import kotlinx.android.synthetic.main.activity_credits.*
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.base.BaseActivity
import me.davidcosta.tmdb.data.model.Credits
import me.davidcosta.tmdb.data.model.Media
import me.davidcosta.tmdb.enums.Keys

class CreditsActivity : BaseActivity() {
    override fun resourceView() =
        R.layout.activity_credits

    override fun setupView() {

        val media = intent.getSerializableExtra(Keys.EXTRAS_MEDIA.value) as Media
        val credits = intent.getSerializableExtra(Keys.EXTRAS_CREDITS.value) as Credits

        val sectionsPagerAdapter =
            SectionsPagerAdapter(
                this,
                credits
            )

//        val viewPager: ViewPager = findViewById(R.id.view_pager)
        view_pager.adapter = sectionsPagerAdapter

//        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(view_pager)


        supportActionBar?.title = media.nameOrTile
    }
}