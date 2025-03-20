package com.example.punchspeedcalculator.listeners

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class AccelerometerManager():SensorEventListener {

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
        }
    }

    override fun onAccuracyChanged(event: Sensor?, p1: Int) {

    }


     fun startAccelerometer(sensorManager: SensorManager,accelerometer: Sensor?){
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

     fun stopAccelerometer(sensorManager: SensorManager,accelerometer: Sensor?){
        sensorManager.unregisterListener(this)
    }

}