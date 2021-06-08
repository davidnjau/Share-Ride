package com.dave.shareride.helper_classes

import android.text.TextUtils
import android.util.Log
import java.util.regex.Pattern

class TextValidator {

    fun isEmailValid(email : String): Boolean{

        return Pattern.compile(
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()

    }

    fun isPhoneNumberValid(phoneNumber : String):Pair<Boolean, Int>{

        val phoneCount = phoneNumber.length
        return if (phoneCount < 10)
            Pair(false, phoneCount)
        else
            Pair(true, phoneCount)

    }

    fun isPasswordStrong(password: String):Triple<Boolean, String, String>{

        val passwordStrength = PasswordStrength.calculate(password)


        val passwordCount = password.length.toString()

        return if (passwordCount.toInt() > 8){
            Triple(true, passwordCount, passwordStrength.toString())
        }else{
            Triple(false, passwordCount, passwordStrength.toString())
        }

    }

    fun isPasswordSame(password : String, confirmPassword: String): Boolean{

        return password == confirmPassword


    }

    fun textNotNull(text : String):Pair<String, Boolean>{

        var value = ""
        var isText = false

        if (!TextUtils.isEmpty(text)){
            value = text
            isText = true
        }else{
            value = "Field cannot be empty"
            isText = false
        }

        return Pair(value, isText)

    }
}