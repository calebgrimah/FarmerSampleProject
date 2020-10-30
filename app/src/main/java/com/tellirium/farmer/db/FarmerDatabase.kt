package com.tellirium.farmer.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tellirium.farmer.db.model.Farm
import com.tellirium.farmer.db.model.Farmer
import com.tellirium.farmer.db.model.PointsConverter
import com.tellirium.farmer.db.model.User

@Database(entities = [Farmer::class, User::class, Farm::class], version = 1)
@TypeConverters(PointsConverter::class)
abstract class FarmerDatabase : RoomDatabase() {

    abstract fun getFarmerDao() : FarmerDAO
}