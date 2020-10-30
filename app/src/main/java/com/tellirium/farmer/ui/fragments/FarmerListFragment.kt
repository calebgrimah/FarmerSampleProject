package com.tellirium.farmer.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tellirium.farmer.R
import com.tellirium.farmer.ui.adapter.FarmerAdapter
import com.tellirium.farmer.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_farmer_list.*

@AndroidEntryPoint
class FarmerListFragment : Fragment(R.layout.fragment_farmer_list) {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var farmerAdapter: FarmerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        viewModel.farmerListContract.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { data ->
                when(data){
                    is FarmerListContract.ProgressDisplay -> {
                        if(data.display){
                            progressBar.isVisible = true
                            rvFarmer.isVisible = false
                        }
                    }
                    FarmerListContract.ReadFailed -> {
                        progressBar.isVisible = false
                        Snackbar.make(view, "Farmer List Failed to load", Snackbar.LENGTH_INDEFINITE).show()
                    }
                }
            }
        })

        viewModel.localFarmers.observe(viewLifecycleOwner, Observer {
            progressBar.isVisible = false
            rvFarmer.isVisible = true
            farmerAdapter.submitList(it)
        })

        farmerAdapter.setOnItemClickListener {
            findNavController().navigate(FarmerListFragmentDirections.actionFarmerListFragmentToFarmerFragmentDetail(it))
        }

    }

    private fun setupRecyclerView() = rvFarmer.apply {
        farmerAdapter = FarmerAdapter()
        adapter = farmerAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }


}