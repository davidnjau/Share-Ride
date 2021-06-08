package com.dave.shareride.network_requests.classes

import android.provider.ContactsContract
import com.google.gson.annotations.SerializedName

class UserLogin (

    val phone_number: String,
    val password: String,
    val usertype: String


)