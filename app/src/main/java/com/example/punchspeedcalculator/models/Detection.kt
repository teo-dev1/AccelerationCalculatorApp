package com.example.punchspeedcalculator.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "detection_table")
data class Detection(
    @PrimaryKey(autoGenerate = true)val id:Int?=0,
    val peakValue: Float,
    val date:Long
)
