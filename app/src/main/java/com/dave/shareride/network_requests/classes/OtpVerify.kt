package com.dave.shareride.network_requests.classes

import android.provider.ContactsContract
import com.google.gson.annotations.SerializedName

class OtpVerify (

    val phone_number: String,
    val password: String,
    val otp_code: String


)