package com.dave.shareride.room_database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dave.shareride.room_database.dao.ShareRideDao
import com.dave.shareride.room_database.entity.PickupsInfo
import com.dave.shareride.room_database.entity.RoutesInfo


@Database(
        entities = [
            RoutesInfo::class, PickupsInfo::class
        ],
        version = 1,
        exportSchema = false)
public abstract class ShareRideRoomDatabase : RoomDatabase() {

    abstract fun imagesDao() : ShareRideDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ShareRideRoomDatabase? = null

        fun getDatabase(context: Context): ShareRideRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        ShareRideRoomDatabase::class.java,
                        "share_ride_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}