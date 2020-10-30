package com.tellirium.farmer.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.tellirium.farmer.R
import com.tellirium.farmer.api.model.Status
import com.tellirium.farmer.db.model.User
import com.tellirium.farmer.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login){

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.isFirstTime()) {
            viewModel.insertUser(User(email = getString(R.string.email), password = getString(R.string.password)))
            viewModel.addFarmers()
        }

        viewModel.user.observe(viewLifecycleOwner, Observer {
            it?.let {
                Snackbar.make(view, "Login Success", Snackbar.LENGTH_LONG).show()
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToDashboardFragment(it))
            }
        })


        viewModel.loginContract.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { data ->
                when(data){
                    is LoginContract.ProgressDisplay -> {
                        if(data.display){
                            Snackbar.make(view, "Logging In", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    LoginContract.ReadFailed -> {
                        Snackbar.make(view, "Login Failed", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        })

        btnLogin.setOnClickListener {
            viewModel.login(etLoginUsername.text.toString(), etLoginPassword.text.toString())

        }


        viewModel.farmers.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.LOADING -> {
                    btnLogin.isVisible = false
                    progressBar2.isVisible = true
                   Snackbar.make(view,"Loading Farmers for the first time", Snackbar.LENGTH_SHORT).show()
                }
                Status.SUCCESS -> {
                    progressBar2.isVisible = false
                    btnLogin.isVisible = true
                    viewModel.setFirstTime(false)
                    Snackbar.make(view,"Successfully Loaded Farmers to local Database", Snackbar.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    progressBar2.isVisible = false
                    Snackbar.make(view, "Failed to setup application", Snackbar.LENGTH_LONG).show()
                }
            }
        })


    }
}