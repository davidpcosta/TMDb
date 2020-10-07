package me.davidcosta.tmdb.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration


abstract class BaseFragment : Fragment() {

    abstract fun resourceView(): Int
    abstract fun setupView(view: View)

    open fun toolbarResourceView(): Int? =
        null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(resourceView(), container, false)

        // Setup custom toolbar if defined
        if (toolbarResourceView() != null) {
            setupCustomToolbar()
        } else {
            setupDefaultToolbar()
        }

        setupView(view)

        return view
    }

    private fun setupDefaultToolbar() {
        with(requireActivity() as AppCompatActivity) {
            this.supportActionBar?.apply {
                displayOptions  = ActionBar.DISPLAY_SHOW_TITLE
            }
        }
    }

    private fun setupCustomToolbar() {
        with(requireActivity() as AppCompatActivity) {
            this.supportActionBar?.apply {
                displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
                toolbarResourceView()?.let { setCustomView(it) }
            }
        }
    }

}