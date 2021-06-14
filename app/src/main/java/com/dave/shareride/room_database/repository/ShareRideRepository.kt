package com.dave.shareride.room_database.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import com.dave.shareride.room_database.dao.ShareRideDao
import com.dave.shareride.room_database.entity.PickupsInfo
import com.dave.shareride.room_database.entity.RoutesInfo
import kotlinx.coroutines.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit


class ShareRideRepository(private val shareRideDao: ShareRideDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.

    /**
     * Get LiveData from Sqlite
     */
//    val allCompliance: LiveData<List<PickupsInfo>> = shareRideDao.getLivePickups()

    /**
     * Save to SQLITE
     */
    suspend fun addShareRide(routesInfo: RoutesInfo){
        shareRideDao.addShareRide(routesInfo)
    }
    suspend fun addPickUp(pickupsInfo: PickupsInfo){
        shareRideDao.addPickUp(pickupsInfo)
    }

    /**
     *Delete from SQLITE
     */
    suspend fun deleteRoute(id: Int){
        shareRideDao.deleteRoute(id)

    }
    suspend fun deleteRoutePickUp(id: Int){
        shareRideDao.deleteRoutePickUp(id)

    }
    suspend fun deletePickUp(id: Int){
        shareRideDao.deletePickUp(id)

    }

    /**
     * Get routes and pickups
     */

    suspend fun getRoutes(id: String):RoutesInfo {
        return shareRideDao.getRoutes(id)
    }
    suspend fun getPickUps(id: String):PickupsInfo {
        return shareRideDao.getPickUps(id)
    }
    suspend fun getPickUpsRoute(id: String):List<PickupsInfo>{
        return shareRideDao.getPickUpsRoute(id)
    }

}