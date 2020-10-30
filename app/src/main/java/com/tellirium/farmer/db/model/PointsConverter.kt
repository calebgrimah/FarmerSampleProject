package com.tellirium.farmer.db.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class PointsConverter {
    private val gson = Gson()

    @TypeConverter
    fun stringToPointsList(data: String?): List<Point> {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType = object : TypeToken<List<Point>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun pointsListToString(points: List<Point>?): String {
        if (points == null) {
            return gson.toJson(Collections.emptyList<Int>())
        }
        return gson.toJson(points)
    }
}