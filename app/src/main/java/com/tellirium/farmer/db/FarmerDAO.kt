package com.tellirium.farmer.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tellirium.farmer.db.model.Farm
import com.tellirium.farmer.db.model.Farmer
import com.tellirium.farmer.db.model.User
import kotlinx.coroutines.selects.select

@Dao
interface FarmerDAO {
    //FARMERS
    @Query("select * from farmer")
    fun getAllFarmers(): LiveData<List<Farmer>>

    @Update
    suspend fun updateFarmer(farmer: Farmer)

    @Insert
    suspend fun addFarmers(farmers : List<Farmer>)

    //FARMS
    @Query("select * from farm where farmerId = :id")
    fun getUserFarms (id: String?) : LiveData<List<Farm>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFarm(farm: Farm)

    //USERS
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser (user: User)

    @Query("select * from user where email like :username and password like :password limit 1")
    fun getUser(username : String?, password : String?) : LiveData<User>

    @Query("select COUNT(*) from farmer")
    fun getTotalFarmerRecords(): LiveData<Int>

    @Query("select COUNT(gender) from farmer where gender like :conditional")
    fun getTotalIndividualSexes(conditional: String) : LiveData<Int>

    @Query("select COUNT(marital_status) from farmer where marital_status like :conditional")
    fun getTotalMarital(conditional: String) : LiveData<Int>

}