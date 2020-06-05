package me.davidcosta.tmdb.ui.main.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.davidcosta.tmdb.data.api.ApiService
import me.davidcosta.tmdb.data.repository.AccountRepository

@Suppress("UNCHECKED_CAST")
class AccountViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            return AccountViewModel(
                    accountRepository = AccountRepository(
                        api = ApiService.instance
                    )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
