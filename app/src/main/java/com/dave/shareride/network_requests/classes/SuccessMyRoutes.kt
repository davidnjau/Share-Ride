package com.dave.shareride.network_requests.classes

import com.google.gson.annotations.SerializedName
import java.util.*

class SuccessMyRoutes(

    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,

    @SerializedName("results")
    val details: List<ResultsListDetails>

)
data class ResultsListDetails(
    @SerializedName("id") val id: String,
    @SerializedName("reference_number") val reference_number: String,
    @SerializedName("departure_time") val departure_time: String,
    @SerializedName("route_status") val route_status: String,
    @SerializedName("vehicle_registration_number") val vehicle_registration_number: String,
    @SerializedName("vehicle_model") val vehicle_model: String,
    @SerializedName("vehicle_slots") val vehicle_slots: Int,
    @SerializedName("destination") val destination: Destination,
    @SerializedName("other_points") val other_points: List<OtherPoints>)

data class Destination(
    @SerializedName("id") val id: String,
    @SerializedName("station_name") val station_name: String,
    @SerializedName("location") val location: LocationDetails,
    @SerializedName("station_state") val station_state: String,
    @SerializedName("is_pick_up_point") val is_pick_up_point: String,
    @SerializedName("charge_amt") val charge_amt: String?,

    )
data class LocationDetails(
    @SerializedName("type") val type: String,
    @SerializedName("coordinates") val coordinates: List<String>,

    )
data class OtherPoints(
    @SerializedName("id") val id: String,
    @SerializedName("station_name") val station_name: String,
    @SerializedName("location") val location: LocationDetails,
    @SerializedName("station_state") val station_state: String,
    @SerializedName("is_pick_up_point") val is_pick_up_point: String,
    @SerializedName("charge_amt") val charge_amt: String?,
    )

