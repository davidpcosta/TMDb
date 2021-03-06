package me.davidpcosta.tmdb.ui.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import me.davidpcosta.tmdb.*
import me.davidpcosta.tmdb.data.model.LoginResponse
import me.davidpcosta.tmdb.databinding.ActivityLoginBinding
import me.davidpcosta.tmdb.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loading: ProgressBar
    private lateinit var loginButton: Button
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory()).get(LoginViewModel::class.java)
        sharedPreferences = getSharedPreferences(getString(R.string.const_shared_preference), MODE_PRIVATE)

        DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login).apply {
            this.viewModel = loginViewModel
            this.activity = this@LoginActivity
        }

        loading = findViewById(R.id.loading)
        loginButton = findViewById(R.id.login_button)

        observeErrorMessage()
    }

    fun handleLoginClick () {
        loading.show()
        loginButton.disable()
        loginViewModel.validateLogin().observe(this, Observer {
            loading.hide()
            loginButton.enable()
            if (it.success!!) {
                saveUserInfo(it)
                goToMainActivity()
                finish()
            }
            else {
                toast(it.errorMessage!!)
            }
        })
    }

    private fun observeErrorMessage() {
        loginViewModel.errorMessage.observe(this, Observer {
            loading.hide()
            loginButton.enable()
            toast(it)
        })
    }

    private fun saveUserInfo(authenticationResult: LoginResponse) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putLong(getString(R.string.const_key_account_id), authenticationResult.accountId!!)
        editor.putString(getString(R.string.const_key_session_id), authenticationResult.sessionId)
        editor.apply()
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
