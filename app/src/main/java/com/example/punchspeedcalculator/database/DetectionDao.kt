package com.example.punchspeedcalculator.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.punchspeedcalculator.models.Detection
import kotlinx.coroutines.flow.Flow

@Dao
interface DetectionDao {
    @Insert
    suspend fun insertDetection(detection: Detection)

    @Query("SELECT * FROM detection_table ORDER BY date DESC")
    fun getAllDetections(): Flow<List<Detection>>

}