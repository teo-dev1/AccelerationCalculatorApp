package com.example.punchspeedcalculator.viewmodels

import androidx.lifecycle.*
import com.example.punchspeedcalculator.models.Detection
import com.example.punchspeedcalculator.repo.DetectionRepository
import com.example.punchspeedcalculator.models.AccelerationDataManager
import com.example.punchspeedcalculator.utils.SensorDataListener
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AccelerationCalculatorViewModel(
    val accelerationDataManager: AccelerationDataManager,
    private val repository: DetectionRepository
) :
    ViewModel(), SensorDataListener {


    sealed class DetectionRecordState {
        object NOT_RECORDING : DetectionRecordState()
        object RECORDING : DetectionRecordState()
    }

    private val _accelerationLiveData = MutableLiveData<Float>()
    val accelerationLiveData: LiveData<Float> get() = _accelerationLiveData

    private val _peakValuesLiveData = MutableLiveData<List<Detection>>()
    val peakValuesLiveData: LiveData<List<Detection>> get() = _peakValuesLiveData

    private val _recordingStateLiveData = MutableLiveData<DetectionRecordState>()
    val recordStateLiveData: LiveData<DetectionRecordState> get() = _recordingStateLiveData

    init {
        // Registra il ViewModel come listener per ricevere gli aggiornamenti del sensore
        accelerationDataManager.listener = this
        _recordingStateLiveData.value = DetectionRecordState.NOT_RECORDING
    }


    fun toggleRecording() {
        _recordingStateLiveData.value = when (_recordingStateLiveData.value) {
            is DetectionRecordState.RECORDING -> DetectionRecordState.NOT_RECORDING
            is DetectionRecordState.NOT_RECORDING -> DetectionRecordState.RECORDING
            else -> DetectionRecordState.NOT_RECORDING
        }
        println(_recordingStateLiveData.value)
    }


    fun addToDataSet(value: Float) {
        accelerationDataManager.addDataToArray(value)
    }

    fun clearDataSet() {
        accelerationDataManager.clearDataSet()
    }

    fun saveCurrentDataSetPeak():Float?
    {
        val peak=accelerationDataManager.findPeakValue()
        peak?.let {
            savePeakInDataBase(it)  // Salva nel database se non è null
        }
        return peak
    }


    //metodi db

    fun savePeakInDataBase(peak: Float) {
        viewModelScope.launch {
            val detection = Detection(null, peak, System.currentTimeMillis())
            repository.insertDetection(detection)
        }
    }

    fun getAllDetections(): LiveData<List<Detection>> {
        repository.getAllDetections().onEach {detection ->
            _peakValuesLiveData.postValue(detection)
        }.launchIn(viewModelScope)
        return peakValuesLiveData
    }


    override fun onSensorDataChanged(totalAcceleration: Float) {
        _accelerationLiveData.value = totalAcceleration
    }


}