package com.dave.shareride.network_requests.classes

import com.google.gson.annotations.SerializedName

class SuccessLogin(

    @SerializedName("details")
    val details: String,

    @SerializedName("otp_code")
    val otp_code: String,

)
