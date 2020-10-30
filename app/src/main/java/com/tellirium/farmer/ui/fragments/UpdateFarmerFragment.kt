package com.tellirium.farmer.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tellirium.farmer.R
import com.tellirium.farmer.db.model.Farmer
import com.tellirium.farmer.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_update_farmer.*


@AndroidEntryPoint
class UpdateFarmerFragment : Fragment(R.layout.fragment_update_farmer) {

    private val viewModel: MainViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val farmer = FarmerFragmentDetailArgs.fromBundle(requireArguments()).farmer

        farmer.let {
            etFarmerEmail.setText(it.email_address)
            etFarmerCity.setText(it.city)
            etFarmerOccupation.setText(it.occupation)
        }

        btnUpdateFarmerDetail.setOnClickListener {
            viewModel.updateFarmer(
                Farmer (
                    id = farmer.id,
                    farmer_id = farmer.farmer_id,
                    reg_no = farmer.reg_no,
                    bvn = farmer.bvn,
                    first_name = etFarmerName.text.toString(),
                    middle_name = etFarmerName.text.toString(),
                    surname = farmer.surname,
                    dob = farmer.dob,
                    gender = farmer.gender,
                    nationality = farmer.nationality,
                    occupation = etFarmerOccupation.text.toString(),
                    marital_status = farmer.marital_status,
                    spouse_name = farmer.spouse_name,
                    address = farmer.address,
                    city = etFarmerCity.text.toString(),
                    lga = farmer.lga, state = farmer.state, mobile_no = farmer.mobile_no,
                    email_address = etFarmerEmail.text.toString(), id_type = farmer.id_type,
                    id_no = farmer.id_no, issue_date = farmer.issue_date,expiry_date = farmer.expiry_date,
                    id_image = farmer.id_image, passport_photo = farmer.passport_photo, fingerprint = farmer.fingerprint
                )
            )
        }
    }
}