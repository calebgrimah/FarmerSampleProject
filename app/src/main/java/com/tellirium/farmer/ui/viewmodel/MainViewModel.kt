package com.tellirium.farmer.ui.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.tellirium.farmer.api.model.Content
import com.tellirium.farmer.api.model.Event
import com.tellirium.farmer.api.model.MyPreference
import com.tellirium.farmer.api.model.Resource
import com.tellirium.farmer.db.model.Farm
import com.tellirium.farmer.db.model.Farmer
import com.tellirium.farmer.db.model.User
import com.tellirium.farmer.repository.MainRepository
import com.tellirium.farmer.ui.fragments.FarmerListContract
import com.tellirium.farmer.ui.fragments.LoginContract
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository,
    application : Application,
    private val myPreference: MyPreference) : AndroidViewModel(application){

    val loginContract: MutableLiveData<Event<LoginContract>> = MutableLiveData()
    val farmerListContract: MutableLiveData<Event<FarmerListContract>> = MutableLiveData()
    private val localFarmerList = mainRepository.fetchLocalFarmers()

    val localFarmers = MediatorLiveData<List<Farmer>>()

    init {
        farmerListContract.value = Event(FarmerListContract.ProgressDisplay(true))
        localFarmers.addSource(localFarmerList) { result ->
            farmerListContract.value = Event(FarmerListContract.ProgressDisplay(false))
            if(result == null) {
                farmerListContract.value = Event(FarmerListContract.ReadFailed)
            }
            result?.let {
                localFarmers.value = it
            }
        }
    }

    //user
    private val _user = MediatorLiveData<User>()
    val user : LiveData<User>
        get() = _user

    //farms
    private val _farms = MediatorLiveData<List<Farm>>()
    val farms : LiveData<List<Farm>>
        get() = _farms

    fun fetchFarms(farmerId : String){
        val resp = mainRepository.fetchUserFarms(farmerId)
        _farms.addSource(resp){ result ->
            result?.let {
                _farms.value = result
            }
        }
    }

    private val _totalFarmers = MediatorLiveData<Int>()
    val totalFarmers : LiveData<Int>
        get() = _totalFarmers
    fun fetchTotalFarms (){
        val resp = mainRepository.fetchTotalFarmers()
        _totalFarmers.addSource(resp){result ->
            result?.let {
                _totalFarmers.value = result
            }
        }
    }

    private val _totalMaleFarmers= MediatorLiveData<Int>()
    val totalMaleFarmers : LiveData<Int>
        get() = _totalMaleFarmers
    fun fetchTotalMaleFarmers (){
        val resp = mainRepository.fetchTotalMaleFarmers()
        _totalMaleFarmers.addSource(resp){result ->
            result?.let {
                _totalMaleFarmers.value = result
            }
        }
    }

    private val _totalFemaleFarmers= MediatorLiveData<Int>()
    val totalFemaleFarmers : LiveData<Int>
        get() = _totalFemaleFarmers
    fun fetchTotalFemaleFarmers (){
        val resp = mainRepository.fetchTotalFemaleFarmers()
        _totalFemaleFarmers.addSource(resp){result ->
            result?.let {
                _totalFemaleFarmers.value = result
            }
        }
    }

    private val _totalMarriedFarmers= MediatorLiveData<Int>()
    val totalMarriedFarmers : LiveData<Int>
        get() = _totalMarriedFarmers
    fun fetchTotalMarriedFarmers (){
        val resp = mainRepository.fetchTotalMarriedFarmers()
        _totalMarriedFarmers.addSource(resp){result ->
            result?.let {
                _totalMarriedFarmers.value = result
            }
        }
    }

    fun login(username: String, password: String) {
        loginContract.value = Event(LoginContract.ProgressDisplay(true))
        val resp = mainRepository.fetchUser(username, password)
        _user.addSource(resp){ result ->
            loginContract.value = Event(LoginContract.ProgressDisplay(false))
            if(result == null) {
                loginContract.value = Event(LoginContract.ReadFailed)
            }
            result?.let {
                _user.value = result
            }
        }
    }


    fun addFarmers(){
        viewModelScope.launch {
            _farmers.postValue(Resource.loading(null))
            try {
                val farmers = mainRepository.fetchOnlineFarmers()
                mainRepository.farmerDAO.addFarmers(farmers = farmers.data.farmers)
                _farmers.value = Resource.success(data = farmers.data)
            } catch (exception : Exception){
                _farmers.value = Resource.error(data = null, message = exception.message ?: "Failed to Fetch")
            }
        }
    }



    fun isFirstTime () : Boolean = myPreference.getStoredTag()

    fun setFirstTime (isFirstTime: Boolean) {
        myPreference.setStoredTag(isFirstTime)
    }

    private val _farmers = MutableLiveData<Resource<Content>>()
    val farmers : LiveData<Resource<Content>>
        get() = _farmers

    fun insertUser(user: User) = viewModelScope.launch {
        mainRepository.createUser(user)
    }

    fun addFarmerFarm(farm : Farm) = viewModelScope.launch {
        mainRepository.createFarm(farm)
    }

    fun updateFarmer (farmer : Farmer) = viewModelScope.launch {
        mainRepository.updateFarmerData(farmer)
    }

}