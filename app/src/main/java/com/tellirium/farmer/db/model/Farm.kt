package com.tellirium.farmer.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Farm(
    @PrimaryKey(autoGenerate = true)
    val farmId : Int? = null,
    val farmName : String?,
    val farmerId : String,
    val farmLocation : String,
    val points : List<Point>
)