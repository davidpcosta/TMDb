package me.davidcosta.tmdb.ui.main.account

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_account.view.*
import me.davidcosta.tmdb.BuildConfig
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.enums.Keys
import me.davidcosta.tmdb.extensions.hide
import me.davidcosta.tmdb.extensions.show
import me.davidcosta.tmdb.ui.login.LoginActivity
import me.davidcosta.tmdb.ui.main.MainActivity

class AccountFragment : Fragment() {

    private lateinit var accountViewModel: AccountViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sessionId: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)

        accountViewModel = ViewModelProvider(this, AccountViewModelFactory()).get(AccountViewModel::class.java)
        sharedPreferences = requireActivity().getSharedPreferences(Keys.PREFERENCES_USER_LOGIN.value, Context.MODE_PRIVATE)
        sessionId = sharedPreferences.getString(Keys.PREFERENCES_SESSION_ID.value, "")!!

        view.logout_button.setOnClickListener {
            handleLogoutButtonClick()
        }

        view.login_button.setOnClickListener {
            handleLoginButtonClick()
        }

        if (sessionId.isNotBlank()) {
            fetchAccountDetails()
        } else {
            view.login_button.show()
            view.fragment_home_popular_movies_loading.hide()
        }
        return view
    }

    private fun handleLogoutButtonClick() {
        deleteUserInfo()
        goToMain()
        Toast.makeText(activity, "Sessão encerrada", Toast.LENGTH_SHORT).show()
    }

    private fun handleLoginButtonClick() {
        goToLogin()
    }

    private fun deleteUserInfo() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.remove(Keys.PREFERENCES_SESSION_ID.value)
        editor.remove(Keys.PREFERENCES_ACCOUNT_ID.value)
        editor.apply()
    }

    private fun goToLogin() {
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        requireActivity().startActivity(intent)
    }

    private fun goToMain() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        requireActivity().startActivity(intent)
    }

    private fun fetchAccountDetails() {
        accountViewModel.fetchAccountDetails(sessionId)
        accountViewModel.accountDetails.observe(viewLifecycleOwner, Observer {
            view?.let {view ->

                Picasso.with(activity)
                    .load(BuildConfig.GRAVATAR_BASE_URL + it.avatar.gravatar.hash)
                    .into(view.avatar)

                view.name.text = it.name
                view.username.text = it.username

                view.account_info_group.show()
                view.fragment_home_popular_movies_loading.hide()
            }
        })
    }

}
