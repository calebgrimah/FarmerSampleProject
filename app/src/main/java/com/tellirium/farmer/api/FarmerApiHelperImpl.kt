package com.tellirium.farmer.api

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FarmerApiHelperImpl @Inject constructor(
    private val farmerApi: FarmerApi
) : FarmerApiHelper{
    override suspend fun getFarmers() = farmerApi.getFarmers()
}