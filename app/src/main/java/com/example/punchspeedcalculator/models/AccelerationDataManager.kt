package com.example.punchspeedcalculator.models

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.example.punchspeedcalculator.utils.SensorDataListener

class AccelerationDataManager(
    /*repo: DetectionRepository,*/
    val accelerationCalculator: AccelerationCalculator
):SensorEventListener
{
    var listener: SensorDataListener? = null
    var totalAcceleration: Float = 0f
       public get() {
           return field
       }
    private val dataSet = mutableListOf<Float>()

    fun findPeakValue():Float?{
        //printArray()
        return dataSet.max()
    }

    fun printArray(){//prova
        println(dataSet)
    }


    fun addDataToArray(value:Float){
        if(value>Constants.ACCELERATION_THRESHOLD) dataSet.add(value)
        //println(dataSet)
    }

    fun clearDataSet():Boolean{
        dataSet.clear()
        return (dataSet.isEmpty())
    }


    fun startCollectingData(sensorManager: SensorManager) {
        val accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        accelerometer?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun stopCollectingData(sensorManager: SensorManager) {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?){
        event?.let {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            totalAcceleration = accelerationCalculator.calculateTotalAcceleration(x, y, z)
            listener?.onSensorDataChanged(totalAcceleration)

        }
    }

    fun printAcceleration(){
        println(totalAcceleration)
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

}