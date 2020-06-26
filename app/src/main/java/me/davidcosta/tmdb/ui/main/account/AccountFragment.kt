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
import kotlinx.android.synthetic.main.activity_main_fragment_account.view.*
import me.davidcosta.tmdb.BuildConfig
import me.davidcosta.tmdb.R
import me.davidcosta.tmdb.hide
import me.davidcosta.tmdb.show
import me.davidcosta.tmdb.ui.login.LoginActivity
import me.davidcosta.tmdb.ui.main.MainActivity

class AccountFragment : Fragment() {

    private lateinit var accountViewModel: AccountViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sessionId: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_main_fragment_account, container, false)

        accountViewModel = ViewModelProvider(this, AccountViewModelFactory()).get(AccountViewModel::class.java)
        sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.const_shared_preference), Context.MODE_PRIVATE)
        sessionId = sharedPreferences.getString(getString(R.string.const_key_session_id), "")!!

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
            view.loading.hide()
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
        editor.remove(getString(R.string.const_key_session_id))
        editor.remove(getString(R.string.const_key_account_id))
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
                view.loading.hide()
            }
        })
    }

}
