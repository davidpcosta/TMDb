package me.davidcosta.tmdb.ui.main.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import me.davidcosta.tmdb.data.model.AccountDetails
import me.davidcosta.tmdb.data.repository.AccountRepository

class AccountViewModel(private val accountRepository: AccountRepository) : ViewModel() {

    lateinit var accountDetails: LiveData<AccountDetails>

    fun fetchAccountDetails(sessionId: String) {
        accountDetails = accountRepository.accountDetails(sessionId)
    }

}