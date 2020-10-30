package com.tellirium.farmer.db.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Farmer(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val farmer_id: String?,
    val reg_no: String?,
    val bvn: String?,
    val first_name: String?,
    val middle_name: String?,
    val surname: String?,
    val dob: String?,
    val gender: String?,
    val nationality: String?,
    val occupation: String?,
    val marital_status: String?,
    val spouse_name: String?,
    val address: String?,
    val city: String?,
    val lga: String?,
    val state: String?,
    val mobile_no: String?,
    val email_address: String?,
    val id_type: String?,
    val id_no: String?,
    val issue_date: String?,
    val expiry_date: String?,
    val id_image: String?,
    val passport_photo: String?,
    val fingerprint: String?
) : Parcelable