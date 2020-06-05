package me.davidcosta.tmdb.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.davidcosta.tmdb.data.api.ApiService
import me.davidcosta.tmdb.data.repository.AccountRepository

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                    loginRepository = AccountRepository(
                        api = ApiService.instance
                    )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
