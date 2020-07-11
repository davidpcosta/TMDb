package me.davidcosta.tmdb.extensions

import android.widget.Button

fun Button.disable() {
    this.isEnabled = false
    this.isActivated = false
}

fun Button.enable() {
    this.isEnabled = true
    this.isActivated = true
}