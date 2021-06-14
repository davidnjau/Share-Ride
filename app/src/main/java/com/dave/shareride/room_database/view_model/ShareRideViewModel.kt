package com.dave.shareride.room_database.view_model

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dave.shareride.room_database.database.ShareRideRoomDatabase
import com.dave.shareride.room_database.entity.PickupsInfo
import com.dave.shareride.room_database.entity.RoutesInfo
import com.dave.shareride.room_database.repository.ShareRideRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ShareRideViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ShareRideRepository
//    val allWords: LiveData<List<ComplianceInfo>>

    init {
        val complianceDao = ShareRideRoomDatabase.getDatabase(application).imagesDao()
        repository = ShareRideRepository(complianceDao)
//        allWords = repository.allCompliance
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */

    fun addShareRide(routesInfo: RoutesInfo) = viewModelScope.launch(Dispatchers.IO) {
        repository.addShareRide(routesInfo)
    }
    fun addPickUp(pickupsInfo: PickupsInfo) = viewModelScope.launch(Dispatchers.IO) {
        repository.addPickUp(pickupsInfo)
    }

    fun deleteRoute(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteRoute(id)
    }
    fun deleteRoutePickUp(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteRoutePickUp(id)
    }
    fun deletePickUp(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deletePickUp(id)
    }

    fun getRoutes(id: String) = runBlocking{
        repository.getRoutes(id)
    }
    fun getPickUps(id: String) = runBlocking{
        repository.getPickUps(id)
    }
    fun getPickUpsRoute(id: String) = runBlocking {
        repository.getPickUpsRoute(id)
    }

}