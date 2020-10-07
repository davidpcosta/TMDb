package me.davidcosta.tmdb.ui.main.search

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.android.synthetic.main.toolbar_search.view.*
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.base.BaseFragment
import me.davidcosta.tmdb.extensions.hide
import me.davidcosta.tmdb.extensions.show


class SearchFragment : BaseFragment() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchResultAdapter: SearchResultAdapter
    private lateinit var searchResultViewManager: LinearLayoutManager

    private var inputSearch: EditText? = null
    private var buttonClear: ImageButton? = null

    override fun resourceView() =
        R.layout.fragment_search

    override fun toolbarResourceView() =
        R.layout.toolbar_search

    override fun setupView(view: View) {
        searchViewModel = ViewModelProvider(this, SearchViewModelFactory(requireActivity().applicationContext)).get(SearchViewModel::class.java)

        searchResultAdapter = SearchResultAdapter(requireContext())
        searchResultViewManager = LinearLayoutManager(requireContext())

        view.fragment_search_reciclerview_result.apply {
            adapter = searchResultAdapter
            layoutManager = searchResultViewManager
        }

        with(requireActivity() as AppCompatActivity) {
            inputSearch = this.supportActionBar?.customView?.toolbar_search_input
            buttonClear = this.supportActionBar?.customView?.toolbar_search_button_clear

            inputSearch?.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(editable: Editable) {
                    if (editable.isNotEmpty()) {
                        searchViewModel.multiSearch(editable.toString())
                        buttonClear?.show()
                    } else {
                        clearSearchResult()
                    }
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            })

            buttonClear?.setOnClickListener {
                clearSearchInput()
            }
        }

        observeSearchResult()

    }

    private fun observeSearchResult() {
        searchViewModel.medias.observe(viewLifecycleOwner, Observer {
            searchResultAdapter.items = it
            searchResultAdapter.notifyDataSetChanged()
            if (it.isNotEmpty()) {
                view?.fragment_search_group_empty?.hide()
            }
        })
    }

//    private fun goToMedia(media: Media) {
//        if (media.getMediaType == MediaType.MOVIE) {
//            val intent = Intent(requireContext(), HighlightMovieActivity::class.java)
//            intent.putExtra(Keys.EXTRAS_MEDIA.value, media)
//            requireActivity().startActivity(intent)
//        } else {
//            val intent = Intent(requireContext(), HighlightTvActivity::class.java)
//            intent.putExtra(Keys.EXTRAS_MEDIA.value, media)
//            requireActivity().startActivity(intent)
//        }
//    }

    private fun clearSearchResult() {
        searchResultAdapter.items = listOf()
        searchResultAdapter.notifyDataSetChanged()
        buttonClear?.hide()
        view?.fragment_search_group_empty?.show()
    }

    private fun clearSearchInput() {
        inputSearch?.setText("")
    }
}
