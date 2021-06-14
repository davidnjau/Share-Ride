package com.dave.shareride.room_database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dave.shareride.room_database.entity.PickupsInfo
import com.dave.shareride.room_database.entity.RoutesInfo


@Dao
interface ShareRideDao {

    @Insert
    suspend fun addShareRide(routesInfo: RoutesInfo)

    //Delete Route
    @Query("DELETE FROM share_ride_routes WHERE id =:id")
    suspend fun deleteRoute(id : Int)

    //Delete Route and Pickups
    @Query("DELETE FROM share_ride_pick_ups WHERE routeId =:id")
    suspend fun deleteRoutePickUp(id : Int)

    //Fetch routes from sqlite
    @Query("SELECT * from share_ride_routes WHERE id LIKE :id")
    suspend fun getRoutes(id: String): RoutesInfo


    @Insert
    suspend fun addPickUp(pickupsInfo: PickupsInfo)

    //Delete Pickup
    @Query("DELETE FROM share_ride_pick_ups WHERE id =:id")
    suspend fun deletePickUp(id : Int)

    //Fetch pickup from sqlite
    @Query("SELECT * from share_ride_pick_ups WHERE id LIKE :id")
    suspend fun getPickUps(id: String): PickupsInfo

    //Fetch pickup using route from sqlite
    @Query("SELECT * from share_ride_pick_ups WHERE routeId LIKE :id")
    suspend fun getPickUpsRoute(id: String): List<PickupsInfo>

    //Pick ups live data
//    @Query("SELECT * from share_ride_pick_ups WHERE routeId =:id ORDER BY id ASC")
//    fun getLivePickups(id: String): LiveData<List<PickupsInfo>>




}