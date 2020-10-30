package com.tellirium.farmer.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.tellirium.farmer.R
import com.tellirium.farmer.db.model.Farm
import com.tellirium.farmer.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_farm.*


@AndroidEntryPoint
class AddFarmFragment : Fragment(R.layout.fragment_add_farm) {

    private val viewModel: MainViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val farmerId = AddFarmFragmentArgs.fromBundle(requireArguments()).farmerId
        btnAddFarm.setOnClickListener {
            viewModel.addFarmerFarm(
                Farm(
                    farmName = tvFarmName.text.toString(),
                    farmerId = farmerId,
                    farmLocation = tvFarmLocation.text.toString(),
                    points = emptyList()
                )
            )
            Snackbar.make(view, "Farm Added Successfully", Snackbar.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

        btnSelectMapLocation.setOnClickListener {
            findNavController().navigate(AddFarmFragmentDirections.actionAddFarmFragmentToFarmMapFragment())
        }
    }



}