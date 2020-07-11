package me.davidcosta.tmdb.extensions

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation
import me.davidcosta.tmdb.BuildConfig
import me.davidcosta.tmdb.R

fun ImageView.loadPoster(context: Context, path: String) =
    Picasso.with(context)
        .load(BuildConfig.TMDB_IMAGE_URL + path)
        .placeholder(R.drawable.movie_poster_placeholder)
        .into(this)

fun ImageView.loadBackdrop(context: Context, path: String) =
    Picasso.with(context)
        .load(BuildConfig.TMDB_IMAGE_URL + path)
        .transform(BlurTransformation(context, 8, 4))
        .into(this)