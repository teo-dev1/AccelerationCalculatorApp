package com.example.punchspeedcalculator.viewmodels
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SensorDataCollectorViewModel(private val sensorManager: SensorManager) : ViewModel(), SensorEventListener {

    private val _rawSensorData = MutableLiveData<String>()
    val rawSensorData: LiveData<String> get() = _rawSensorData

    private var accelerometer: Sensor? = null

    init {
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        startCollecting()
    }

    // Funzione per iniziare l'ascolto del sensore
    private fun startCollecting() {
        accelerometer?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    // Funzione per fermare l'ascolto del sensore
    fun stopCollecting() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            // Recupera i dati grezzi
            val x=String.format("%.2f",event.values[0])
            val y=String.format("%.2f",event.values[1])
            val z=String.format("%.2f",event.values[2])
            val rawData = "X: $x, Y: $y, Z: $z"
            _rawSensorData.postValue(rawData)
        }
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Non utilizzato
    }
}
