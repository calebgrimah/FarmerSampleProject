package com.tellirium.farmer.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.tellirium.farmer.R
import com.tellirium.farmer.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dashboard.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment  : Fragment(R.layout.fragment_dashboard)
{

    private val viewModel: MainViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchTotalFarms()
        viewModel.fetchTotalMaleFarmers()
        viewModel.fetchTotalFemaleFarmers()
        viewModel.fetchTotalMarriedFarmers()


        viewModel.totalFarmers.observe(viewLifecycleOwner, Observer {
            tvTotalFarmers.text = "Total number of farmers saved on this platform is : $it"
        })
        viewModel.totalMaleFarmers.observe(viewLifecycleOwner, Observer {
            tvTotalMaleFarmers.text = "Total number of male farmers is : $it"
        })
        viewModel.totalFemaleFarmers.observe(viewLifecycleOwner, Observer {
            tvTotalFemaleFarmers.text = "Total number of female farmers is : $it"
        })
        viewModel.totalMarriedFarmers.observe(viewLifecycleOwner, Observer {
            tvTotalMarriedFarmers.text = "Total number of married farmers is : $it"
        })

        btnFarmerList.setOnClickListener {
            findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToFarmerListFragment())
        }
    }
}