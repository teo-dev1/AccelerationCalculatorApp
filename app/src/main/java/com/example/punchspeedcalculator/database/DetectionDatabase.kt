package com.example.punchspeedcalculator.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.punchspeedcalculator.models.Detection


@Database(entities = [Detection::class], version = 1)
abstract class DetectionDatabase:RoomDatabase() {
    abstract fun detectionDao(): DetectionDao



    companion object{
        @Volatile
        private var INSTANCE: DetectionDatabase?=null

        private fun buildDatabase(context:Context): DetectionDatabase {
            return Room.databaseBuilder(context, DetectionDatabase::class.java,"detections.db").build()
        }

        fun getDatabaseInstance(context: Context): DetectionDatabase {
            if(INSTANCE ==null){
                synchronized(this){
                    INSTANCE = buildDatabase(context)
                }
            }
            return INSTANCE!!
        }
    }
}