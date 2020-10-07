package me.davidcosta.tmdb.ui.highlight

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_cast_rail.view.*
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.base.BaseFragment
import me.davidcosta.tmdb.data.vo.CreditsVO
import me.davidcosta.tmdb.data.vo.PersonVO
import me.davidcosta.tmdb.data.vo.TitleVO
import me.davidcosta.tmdb.enums.Keys
import me.davidcosta.tmdb.ui.credits.CreditsActivity


private const val MAX_CAST = 10

class CastRailFragment : BaseFragment() {

    private lateinit var castViewManager: LinearLayoutManager
    private lateinit var castAdapter: CastRailAdapter
    private lateinit var credits: CreditsVO

    override fun resourceView() =
        R.layout.fragment_cast_rail

    override fun setupView(view: View) {
        castViewManager = LinearLayoutManager(requireActivity().applicationContext).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        castAdapter = CastRailAdapter(requireContext())

        view.fragment_cast_rail_persons.apply {
            layoutManager = castViewManager
            adapter = castAdapter
        }

        view.fragment_cast_button_credits.apply {
            setOnClickListener {
                goToCredits()
            }
        }
    }

    fun setCredits(credits: CreditsVO) {
        this.credits = credits

        val size = if (credits.cast.size > MAX_CAST) MAX_CAST else credits.cast.size
        setCast(credits.cast.subList(0, size))
    }

    private fun setCast(cast: List<PersonVO>) {
        castAdapter.cast = cast
        castAdapter.notifyDataSetChanged()
    }

    private fun getMediaExtras(): TitleVO {
        return requireActivity().intent.getSerializableExtra(Keys.EXTRAS_MEDIA.value) as TitleVO
    }

    private fun goToCredits() {
        val intent = Intent(requireContext(), CreditsActivity::class.java)
        intent.putExtra(Keys.EXTRAS_CREDITS.value, credits)
        intent.putExtra(Keys.EXTRAS_MEDIA.value, getMediaExtras())
        requireActivity().startActivity(intent)
    }

}