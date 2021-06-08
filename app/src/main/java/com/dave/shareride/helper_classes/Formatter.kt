package com.dave.shareride.helper_classes

import android.content.Context
import android.text.TextUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject

class Formatter {

    private lateinit var context: Context

    suspend fun getObjectKeys(jsonObject: JSONObject):List<String>{

        val errorList = ArrayList<String> ()

        val keyList = ArrayList<String>()
        val job = Job()
        CoroutineScope(Dispatchers.IO + job).launch {

            val iterator = jsonObject.keys()
            while (iterator.hasNext()){

                val key = iterator.next()
                keyList.add(key)

            }

        }.join()

        for (keys in keyList){

            val value = getObjectValues(keys, jsonObject)
            errorList.add("$keys:\n $value")

        }


        return errorList

    }
    private fun getObjectValues(key: String, jsonObject: JSONObject): String{
        return formatString(jsonObject.getString(key))
    }
    private fun formatString(value: String):String{

        val lengthFilter = value.length
        return value.substring(1, lengthFilter - 1)
    }



}