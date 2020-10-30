package com.tellirium.farmer.api

import com.tellirium.farmer.api.model.FarmerApiResponse
import com.tellirium.farmer.api.model.Resource
import retrofit2.Response
import retrofit2.http.GET

interface FarmerApi {

    @GET("get-sample-farmers")
    suspend fun getFarmers(): FarmerApiResponse
}