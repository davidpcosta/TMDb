package me.davidcosta.tmdb.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    abstract fun resourceView(): Int
    abstract fun setupView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(resourceView())

        setupView()

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
        }

//        actionBar?.let {
//            it.onsele
//        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}