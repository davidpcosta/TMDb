package me.davidcosta.tmdb.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.davidcosta.tmdb.data.repository.AccountRepository
import me.davidcosta.tmdb.data.model.LoginResponse
import java.lang.Error


class LoginViewModel(private val loginRepository: AccountRepository) : ViewModel() {

    var username: String = "davidpcosta"
    var password: String = "1234qwer"

    val errorMessage: LiveData<String> = MutableLiveData()
//    lateinit var authenticationResult: LiveData<AuthenticationResult>


    fun validateLogin(): LiveData<LoginResponse> {
        try {
            return loginRepository.validateLogin(username, password)
        }
        catch (e: Error) {
            errorMessage as MutableLiveData
            errorMessage.value = e.message
        }

        return MutableLiveData()
    }
}
