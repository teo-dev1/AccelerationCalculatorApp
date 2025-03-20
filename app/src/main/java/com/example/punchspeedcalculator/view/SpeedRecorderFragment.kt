package com.example.punchspeedcalculator.view

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.punchspeedcalculator.R
import com.example.punchspeedcalculator.database.DetectionDatabase
import com.example.punchspeedcalculator.repo.DetectionRepository
import com.example.punchspeedcalculator.models.AccelerationCalculator
import com.example.punchspeedcalculator.models.AccelerationCalculatorViewModelFactory
import com.example.punchspeedcalculator.models.AccelerationDataManager
import com.example.punchspeedcalculator.viewmodels.AccelerationCalculatorViewModel
import kotlinx.android.synthetic.main.fragment_speed_recorder.*


class SpeedRecorderFragment : Fragment() {
    var btn_start_color: Int = 0
    var btn_start_text = ""

    /**DEPENDENCIES**/
    val accelerationCalculator: AccelerationCalculator = AccelerationCalculator()//to inject
    val accelerationDataManager: AccelerationDataManager = AccelerationDataManager(accelerationCalculator)//to inject

    /**SENSORS**/
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor

    /**VIEWMODELS**/
    //private lateinit var sensorDataCollectorViewModel: SensorDataCollectorViewModel
    private lateinit var viewModel: AccelerationCalculatorViewModel  //si può inizializzare tramite by viewmodels solo se non viene passato nessun parametro al viewmodel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager =requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager // Ottieni il SensorManager e il sensore accelerometro
        accelerometer =sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) // Registra il listener per l'accelerometro
        accelerationDataManager.startCollectingData(sensorManager)
        val repository = DetectionRepository(DetectionDatabase.getDatabaseInstance(requireContext()))
        viewModel = ViewModelProvider(this,AccelerationCalculatorViewModelFactory(accelerationDataManager, repository)).get(AccelerationCalculatorViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_speed_recorder, container, false)
    }

    //FARE TEST

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //sensorDataCollectorViewModel= SensorDataCollectorViewModel(sensorManager)//
        /* sensorDataCollectorViewModel.rawSensorData.observe(viewLifecycleOwner){ rawData ->
             rawDataTextView.text = rawData // Aggiorna la TextView con i dati grezzi del sensore
         }*/
        val combinedLiveData = MediatorLiveData<Pair<Float, AccelerationCalculatorViewModel.DetectionRecordState>>()
        combinedLiveData.addSource(viewModel.accelerationLiveData) { acceleration ->
            val currentState = viewModel.recordStateLiveData.value
            if (currentState != null) {
                combinedLiveData.value = acceleration to currentState
            }
        }


        combinedLiveData.addSource(viewModel.recordStateLiveData) { state ->
            val currentAcceleration = viewModel.accelerationLiveData.value
            if (currentAcceleration != null) {
                combinedLiveData.value = currentAcceleration to state
            }
        }


        combinedLiveData.observe(viewLifecycleOwner) { (acceleration, state) ->
            accelerometerData.text = String.format("Accelerazione: %.1f m/s²", acceleration)
            when (state) {
                is AccelerationCalculatorViewModel.DetectionRecordState.RECORDING -> {
                    viewModel.addToDataSet(acceleration)
                }
            }
        }
        viewModel.recordStateLiveData.observe(viewLifecycleOwner) { state ->
            btn_start_color =
                if (state == (AccelerationCalculatorViewModel.DetectionRecordState.RECORDING)) R.color.not_recording else R.color.recording
            btn_start_text =
                if (state == (AccelerationCalculatorViewModel.DetectionRecordState.RECORDING)) getString(
                    R.string.no_recording
                ) else getString(R.string.record)
            when (state) {
                is AccelerationCalculatorViewModel.DetectionRecordState.NOT_RECORDING -> {
                    println("LAST PEAK ${viewModel.saveCurrentDataSetPeak()}")
                    viewModel.saveCurrentDataSetPeak()
                    //viewModel.clearDataSet()
                }
            }
        }

    }


    override fun onResume() {
        super.onResume()
        recordButton.setBackgroundColor(context!!.getColor(R.color.recording))
        recordButton.text = getString(R.string.record)
        recordButton.setOnClickListener {
            viewModel.toggleRecording()
            it.apply {
                setBackgroundColor(requireContext().getColor(btn_start_color))
                it as Button
                it.text = btn_start_text
            }
        }

    }

    override fun onPause() {
        super.onPause()
        accelerationDataManager.stopCollectingData(sensorManager)

    }


}