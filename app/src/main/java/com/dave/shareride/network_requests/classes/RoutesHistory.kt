package com.dave.shareride.network_requests.classes

import com.google.gson.annotations.SerializedName

class RoutesHistory(

    var id : String,
    var destinationName : String,
    var routeStatus : String,
    var departureTime : String,
    var pickPointsNumber : String,
    val vehicleModel : String,
    val vehicleRegistration : String,
    val vehicleSlots : String,
)
