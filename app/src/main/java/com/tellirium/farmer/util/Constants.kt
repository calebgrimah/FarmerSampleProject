package com.tellirium.farmer.util

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

object Constants {
    const val FARMER_DATABASE_NAME = "farmer_db"
    const val BASE_URL = "https://theagromall.com/api/v2/"
    const val BASE_IMAGE_URL = "https://s3-eu-west-1.amazonaws.com/agromall-storage/"
    const val SHARED_PREFERENCES_NAME = "sharedPref"
    const val KEY_FIRST_TIME = "KEY_FIRST_TIME_TOGGLE"

    const val MALE_FARMERS = "Male"
    const val FEMALE_FARMERS = "Female"
    const val SINGLE_FARMERS = "Single"
    const val MARRIED_FARMERS = "Married"
    const val WIDOWED_FARMERS = "Widowed"


}
