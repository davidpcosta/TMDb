package me.davidcosta.tmdb

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.Toast
import java.util.*

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun Button.disable() {
    this.isEnabled = false
    this.isActivated = false
}

fun Button.enable() {
    this.isEnabled = true
    this.isActivated = true
}

@SuppressLint("SimpleDateFormat")
fun Date.toLongFormatString(): String {
    val formatter = java.text.SimpleDateFormat("dd 'de' MMMM 'de' yyyy")
    return formatter.format(this)
}

