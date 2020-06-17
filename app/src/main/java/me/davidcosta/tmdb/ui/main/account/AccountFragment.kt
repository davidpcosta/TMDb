package me.davidcosta.tmdb.ui.main.account

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import me.davidcosta.tmdb.BuildConfig
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.hide
import me.davidcosta.tmdb.show
import me.davidcosta.tmdb.ui.login.LoginActivity

class AccountFragment : Fragment() {

    private lateinit var accountViewModel: AccountViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sessionId: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_main_fragment_account, container, false)

        accountViewModel = ViewModelProvider(this, AccountViewModelFactory()).get(AccountViewModel::class.java)
        sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.const_shared_preference), Context.MODE_PRIVATE)
        sessionId = sharedPreferences.getString(getString(R.string.const_key_session_id), "")!!

        val logoutButton: Button = view.findViewById(R.id.logout_button)
        logoutButton.setOnClickListener {
            handleLogoutButtonClicked()
        }

        fetchAccountDetails()
        return view
    }

    private fun handleLogoutButtonClicked() {
        deleteUserInfo()
        goToLogin()
    }

    private fun deleteUserInfo() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.remove(getString(R.string.const_key_session_id))
        editor.remove(getString(R.string.const_key_account_id))
        editor.apply()
    }

    private fun goToLogin() {
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        requireActivity().startActivity(intent)
        requireActivity().finish()
    }

    private fun fetchAccountDetails() {
        accountViewModel.fetchAccountDetails(sessionId)
        accountViewModel.accountDetails.observe(viewLifecycleOwner, Observer {
            view?.let {view ->
                val avatar: ImageView = view.findViewById(R.id.avatar)
                val name: TextView = view.findViewById(R.id.name)
                val username: TextView = view.findViewById(R.id.username)
                val loading: ProgressBar = view.findViewById(R.id.loading)
                val logoutButton: Button = view.findViewById(R.id.logout_button)

                Picasso.with(activity)
                    .load(BuildConfig.GRAVATAR_BASE_URL + it.avatar.gravatar.hash)
                    .into(avatar)

                name.text = it.name
                username.text = it.username

                avatar.show()
                name.show()
                username.show()
                logoutButton.show()
                loading.hide()
            }
        })
    }

    // TODO: Handle login ckeck in account screen

    //    private lateinit var sharedPreferences: SharedPreferences
    //    sharedPreferences = getSharedPreferences(getString(R.string.const_shared_preference), MODE_PRIVATE)

    //    private fun checkUserLoggedIn() {
    //        if (isUserLoggedIn()) {
    //            goToMain()
    //        } else {
    //            goToLogin()
    //        }
    //    }

    //    private fun goToLogin() {
    //        val intent = Intent(this, LoginActivity::class.java)
    //        startActivity(intent)
    //        finish()
    //    }


    //    private fun isUserLoggedIn(): Boolean {
    //        val sessionId = sharedPreferences.getString(getString(R.string.const_key_session_id), "")
    //        val accountId = sharedPreferences.getLong(getString(R.string.const_key_account_id), 0)
    //        return sessionId!!.isNotBlank() && accountId > 0
    //    }

}
