package me.davidcosta.tmdb.ui.main.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.davidcosta.tmdb.data.repository.SearchRepository
import me.davidcosta.tmdb.data.vo.TitleVO

class SearchViewModel(private val searchRepository: SearchRepository) : ViewModel() {

    private val mMedias: MutableLiveData<List<TitleVO>> = MutableLiveData()

    val medias: LiveData<List<TitleVO>>
        get() = mMedias

    fun multiSearch(query: String) {
        CoroutineScope(Dispatchers.Main).launch {
            mMedias.apply {
                value = searchRepository.multiSearch(query)
            }
        }
    }
}