package me.davidpcosta.tmdb.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.davidpcosta.tmdb.data.AuthenticationRepository
import me.davidpcosta.tmdb.data.model.AuthenticationResult
import me.davidpcosta.tmdb.data.model.SessionResult


class LoginViewModel(private val loginRepository: AuthenticationRepository) : ViewModel() {

    var username: String = "davidpcosta"
    var password: String = "1234qwer"

    lateinit var requestToken: String

    val loginResult: LiveData<AuthenticationResult> = MutableLiveData()
    val sessionResult: LiveData<SessionResult> = MutableLiveData()

    init {
        createRequestToken()
    }

    fun login() {
        loginRepository.login(username, password, requestToken).subscribe({
            if (it.success) {
                loginResult as MutableLiveData
                loginResult.value = it
                createSession()
            } else {
                TODO("Mensagem de erro")
            }
        },
            { e ->
                e.printStackTrace()
                TODO("Mensagem de erro")
            })
    }

    private fun createRequestToken() {
        loginRepository.createRequestToken().subscribe ({
            if (it.success) {
                requestToken = it.requestToken // TODO: Necessário checar o requestToken?
            } else {
                TODO("Mensagem de erro")
            }
        },
            { e ->
                e.printStackTrace()
                TODO("Mensagem de erro")
            })
    }

    private fun createSession() {
        loginRepository.createSession(requestToken).subscribe({
            if (it.success) {
                sessionResult as MutableLiveData
                sessionResult.value = it
            } else {
                TODO("Mensagem de erro")
            }
        },
            { e ->
                e.printStackTrace()
                TODO("Mensagem de erro")
            })
    }
}
