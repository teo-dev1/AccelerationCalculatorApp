package com.example.punchspeedcalculator.utils

interface SensorDataListener {
    fun onSensorDataChanged(totalAcceleration: Float)
}