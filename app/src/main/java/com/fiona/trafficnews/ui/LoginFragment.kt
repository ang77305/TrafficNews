package com.fiona.trafficnews.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.fiona.trafficnews.R
import com.fiona.trafficnews.viewmodel.UserViewModel
import com.fiona.trafficnews.data.Status
import com.fiona.trafficnews.utils.AppUtils

import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    private lateinit var userViewModel: UserViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }



    override fun onStart() {
        super.onStart()
        userViewModel = ViewModelProviders.of(requireActivity())[UserViewModel::class.java]
        val dialog = ProgressDialog.show(
            activity, "",
            "Loading. Please wait...", true
        )
        dialog.hide()
        userViewModel.userInfoLiveData.observe(viewLifecycleOwner, Observer {
            if (dialog.isShowing) {
                dialog.hide()
            }
        })


        userViewModel.userInfoLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    dialog.hide()
                    Navigation.findNavController(btnLogin).navigate(R.id.to_news_fragment)
                }
                Status.LOADING -> {
                    dialog.show()
                }
                Status.ERROR -> {
                    dialog.hide()
                    AppUtils().showSnack(username_layout, it.message.toString())
                }
            }
        })

        userViewModel.userNameError.observe(viewLifecycleOwner) {
            if (it.isEmpty())
                username_layout.error = null
            else
                username_layout.error = it
        }
        userViewModel.pwdError.observe(viewLifecycleOwner) {
            if (it.isEmpty())
                pwd_layout.error = null
            else
                pwd_layout.error = it
        }
        btnLogin.setOnClickListener {
            userViewModel.checkInputValid(edit_username.text.toString(), edit_pwd.text.toString())
        }

    }



}