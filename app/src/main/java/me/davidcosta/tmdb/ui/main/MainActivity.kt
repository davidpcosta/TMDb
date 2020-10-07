package me.davidcosta.tmdb.ui.main

import androidx.appcompat.app.ActionBar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun resourceView() =
        R.layout.activity_main

    override fun setupView() {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

//        // Custom toolbar
//        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
//        supportActionBar?.setCustomView(R.layout.toolbar_search)

        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_search,
                R.id.navigation_watchlist
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}
