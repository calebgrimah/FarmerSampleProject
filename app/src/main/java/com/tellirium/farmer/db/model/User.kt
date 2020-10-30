
package com.tellirium.farmer.db.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class User (
    @PrimaryKey(autoGenerate = false)
    val id : Int? = null,
    val email: String?,
    val password: String?
) : Parcelable