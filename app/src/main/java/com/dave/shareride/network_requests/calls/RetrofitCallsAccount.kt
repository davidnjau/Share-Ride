package com.dave.shareride.network_requests.calls

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import com.dave.shareride.R
import com.dave.shareride.helper_classes.CustomDialogToast
import com.dave.shareride.helper_classes.Formatter
import com.dave.shareride.helper_classes.SharedPreferenceStorage
import com.dave.shareride.helper_classes.UrlData
import com.dave.shareride.network_requests.classes.RoutesHistory
import com.dave.shareride.network_requests.classes.SuccessMyRoutes
import com.dave.shareride.network_requests.classes.UserLogin
import com.dave.shareride.user_management.VerifyOtp
import com.uptech.easymeal.network_request.`interface`.Interface
import com.uptech.easymeal.network_request.builder.RetrofitBuilder
import kotlinx.coroutines.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class RetrofitCallsAccount {

    var customDialogToast = CustomDialogToast()

    fun getMyRoutes(context: Context) = runBlocking{
        fetchMyRoutes(context)
    }
    private suspend fun fetchMyRoutes(context: Context):List<RoutesHistory> {

        var myRoutesList = ArrayList<RoutesHistory>()
        val job = Job()

        CoroutineScope(Dispatchers.IO + job).launch {

            val baseUrl = context.getString(UrlData.BASE_URL_SHARE_RIDE.message)
            val apiService = RetrofitBuilder.getRetrofit(baseUrl).create(Interface::class.java)
            val stringHeader = Formatter().getHeaders(context)
            val callSync: Call<SuccessMyRoutes> = apiService.getMyRoutes(stringHeader)

            try {
                val response: Response<SuccessMyRoutes> = callSync.execute()
                val routeList = response.body()?.details
                if (routeList != null) {

                    for (items in routeList) {

                        val id = items.id
                        val destinationName = items.destination.station_name
                        val routeStatus = items.route_status
                        val departureTime = items.departure_time

                        val pickPointsList = items.other_points
                        val pickPointsNumber = pickPointsList.size

                        val vehicleModel = items.vehicle_model
                        val vehicleRegistration = items.vehicle_registration_number
                        val vehicleSlots = items.vehicle_slots

                        val routesHistory = RoutesHistory(
                            id, destinationName, routeStatus, departureTime,
                            pickPointsNumber.toString(), vehicleModel, vehicleRegistration,
                            vehicleSlots.toString()
                        )

                        myRoutesList.add(routesHistory)
                    }

                }

            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }.join()

        return myRoutesList

    }



}