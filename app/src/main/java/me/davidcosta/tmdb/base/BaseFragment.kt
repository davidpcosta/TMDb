package me.davidcosta.tmdb.base

import android.content.res.Resources
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.davidcosta.tmdb.R


abstract class BaseFragment : Fragment() {

    abstract fun resourceView(): Int
    abstract fun setupView(view: View)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        val context = ContextThemeWrapper(requireContext(), R.style.Theme_TMDbTheme)
//        val localInflater = inflater.cloneInContext(context)
//        val view = localInflater.inflate(resourceView(), null, false)

        val view = inflater.inflate(resourceView(), container, false)
        this.setupView(view)
        return view
    }

}