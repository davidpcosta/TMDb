package me.davidcosta.tmdb.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.ui.main.MainActivity
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.setDisplayShowTitleEnabled(false)


        Observable.timer(800, TimeUnit.MILLISECONDS)
            .map { goToMain() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}