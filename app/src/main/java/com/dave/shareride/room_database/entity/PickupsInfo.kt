package com.dave.shareride.room_database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob

@Entity(tableName = "share_ride_pick_ups")
data class PickupsInfo(

        val point_name: String,
        val longitude: String,
        val latitude: String,
        val charge_amt: String,
        val routeId: String,

){
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null
}