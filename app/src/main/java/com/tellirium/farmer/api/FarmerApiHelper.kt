package com.tellirium.farmer.api

import com.tellirium.farmer.api.model.FarmerApiResponse

interface FarmerApiHelper {

    suspend fun getFarmers() : FarmerApiResponse
}