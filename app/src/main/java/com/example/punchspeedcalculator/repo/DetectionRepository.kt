package com.example.punchspeedcalculator.repo

import com.example.punchspeedcalculator.database.DetectionDatabase
import com.example.punchspeedcalculator.models.Detection

class DetectionRepository(private val detectionDatabase: DetectionDatabase) {

    suspend fun insertDetection(detection: Detection){
        detectionDatabase.detectionDao().insertDetection(detection)
    }

    fun getAllDetections()= detectionDatabase.detectionDao().getAllDetections()

}