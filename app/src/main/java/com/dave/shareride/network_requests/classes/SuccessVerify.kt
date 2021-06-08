package com.dave.shareride.network_requests.classes

import com.google.gson.annotations.SerializedName

class SuccessVerify(

    @SerializedName("details")
    val details: LoginDetails,

)
data class LoginDetails(
    @SerializedName("access_token") val access_token: String,
    @SerializedName("expires_in") val expires_in: String,
    @SerializedName("token_type") val token_type: String,
    @SerializedName("refresh_token") val refresh_token: String,
    @SerializedName("jwt") val jwt: String)
