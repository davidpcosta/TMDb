package me.davidcosta.tmdb.ui.highlight

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_more_details.view.*
import me.davidcosta.tmdb.R

class MoreDetailsFragment : Fragment() {

    private lateinit var itemsViewManager: LinearLayoutManager
    private lateinit var itemsAdapter: MoreDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        itemsViewManager = LinearLayoutManager(requireActivity().applicationContext)
        itemsAdapter = MoreDetailsAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_more_details, container, false)
        view.fragment_more_details_items.apply {
            layoutManager = itemsViewManager
            adapter = itemsAdapter
        }
        return view
    }

    fun setItems(items: List<Pair<String, String>>) {
        itemsAdapter.items = items
        itemsAdapter.notifyDataSetChanged()
    }
}