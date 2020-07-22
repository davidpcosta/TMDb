package me.davidcosta.tmdb.ui.highlight

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_cast_rail.view.*
import kotlinx.android.synthetic.main.fragment_more_details.view.*
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.data.model.Cast

class CastRailFragment : Fragment() {

    private lateinit var castViewManager: LinearLayoutManager
    private lateinit var castAdapter: CastRailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        castViewManager = LinearLayoutManager(requireActivity().applicationContext).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        castAdapter = CastRailAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cast_rail, container, false)
        view.fragment_cast_rail_persons.apply {
            layoutManager = castViewManager
            adapter = castAdapter
        }
        return view
    }

    fun setCast(cast: List<Cast>) {
        castAdapter.cast = cast
        castAdapter.notifyDataSetChanged()
    }

}