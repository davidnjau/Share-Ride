package com.dave.shareride.network_requests.classes

import android.provider.ContactsContract
import com.google.gson.annotations.SerializedName

class UserRegister (

    val phone_number: String,
    val email: String,
    val name: String,
    val gender: String,
    val password: String,
    val confirm_password: String,

)