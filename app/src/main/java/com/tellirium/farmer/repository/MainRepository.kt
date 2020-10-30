package com.tellirium.farmer.repository

import com.tellirium.farmer.api.FarmerApi
import com.tellirium.farmer.db.FarmerDAO
import com.tellirium.farmer.db.model.Farm
import com.tellirium.farmer.db.model.Farmer
import com.tellirium.farmer.db.model.User
import com.tellirium.farmer.util.Constants
import javax.inject.Inject

class MainRepository @Inject constructor(
    val farmerDAO: FarmerDAO, val api: FarmerApi
) {
    //USER
    suspend fun createUser(user : User) = farmerDAO.addUser(user)

    fun fetchUser(username :String, password : String) = farmerDAO.getUser(username, password)

    //Farmers
    suspend fun fetchOnlineFarmers() = api.getFarmers()

    fun fetchLocalFarmers() = farmerDAO.getAllFarmers()

    suspend fun updateFarmerData(farmer: Farmer) =  farmerDAO.updateFarmer(farmer)

    fun fetchTotalFarmers() = farmerDAO.getTotalFarmerRecords()

    fun fetchTotalMaleFarmers() = farmerDAO.getTotalIndividualSexes(Constants.MALE_FARMERS)

    fun fetchTotalFemaleFarmers() = farmerDAO.getTotalIndividualSexes(Constants.FEMALE_FARMERS)

    fun fetchTotalSingleFarmers() = farmerDAO.getTotalMarital(Constants.SINGLE_FARMERS)

    fun fetchTotalMarriedFarmers() = farmerDAO.getTotalMarital(Constants.MARRIED_FARMERS)

    fun fetchTotalWidowedFarmers() = farmerDAO.getTotalMarital(Constants.WIDOWED_FARMERS)


    //farms
    suspend fun createFarm(farm : Farm) = farmerDAO.addFarm(farm)

    fun fetchUserFarms(farmerId: String) = farmerDAO.getUserFarms(farmerId)

}