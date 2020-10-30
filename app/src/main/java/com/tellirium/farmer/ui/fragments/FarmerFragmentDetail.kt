package com.tellirium.farmer.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.tellirium.farmer.R
import com.tellirium.farmer.db.model.Farm
import com.tellirium.farmer.ui.adapter.FarmAdapter
import com.tellirium.farmer.ui.adapter.FarmerAdapter
import com.tellirium.farmer.ui.viewmodel.MainViewModel
import com.tellirium.farmer.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.farmer_list_item.view.*
import kotlinx.android.synthetic.main.fragment_farmer_detail.*
import kotlinx.android.synthetic.main.fragment_farmer_list.*

@AndroidEntryPoint
class FarmerFragmentDetail : Fragment(R.layout.fragment_farmer_detail) {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var farmAdapter: FarmAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFarmRecyclerView()

        val farmer = FarmerFragmentDetailArgs.fromBundle(requireArguments()).farmer
        Picasso.get().load(Constants.BASE_IMAGE_URL + farmer.passport_photo).fit().centerCrop()
            .into(imageView2);

        tvFarmerName.text = farmer.first_name + " " + farmer.surname
        tvFarmerBvn.text = farmer.bvn
        tvFarmerReg.text = farmer.occupation

        farmer.farmer_id?.let { viewModel.fetchFarms(farmerId = it) }

        viewModel.farms.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()){
                tvSizeLabel.isVisible = true
                rvFarms.isVisible = false
            } else {
                rvFarms.isVisible = true
                tvSizeLabel.isVisible = false
                farmAdapter.submitList(it)
            }
        })


        btnAddFarm.setOnClickListener {
            findNavController().navigate(FarmerFragmentDetailDirections.actionFarmerFragmentDetailToAddFarmFragment(farmer.farmer_id!!))
        }

        btnUpdateFarmer.setOnClickListener {
            findNavController().navigate(FarmerFragmentDetailDirections.actionFarmerFragmentDetailToUpdateFarmerFragment(farmer))
        }


    }

    private fun setupFarmRecyclerView() = rvFarms.apply {
        farmAdapter = FarmAdapter()
        adapter = farmAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

}