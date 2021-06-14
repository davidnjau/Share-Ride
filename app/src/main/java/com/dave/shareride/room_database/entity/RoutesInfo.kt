package com.dave.shareride.room_database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob

@Entity(tableName = "share_ride_routes")
data class RoutesInfo(

        val departure_time: String,
        val vehicle_reg_number: String,
        val vehicle_model: String,
        val vehicle_slots: String

){
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null
}