package com.example.punchspeedcalculator.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.punchspeedcalculator.R
import com.example.punchspeedcalculator.adapters.DetectionListAdapter
import com.example.punchspeedcalculator.database.DetectionDatabase
import com.example.punchspeedcalculator.repo.DetectionRepository
import com.example.punchspeedcalculator.models.AccelerationCalculator
import com.example.punchspeedcalculator.models.AccelerationCalculatorViewModelFactory
import com.example.punchspeedcalculator.models.AccelerationDataManager
import com.example.punchspeedcalculator.viewmodels.AccelerationCalculatorViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*

class MainMenuFragment:Fragment() {

    /**DEPENDENCIES**/
    private lateinit var detectionListAdapter: DetectionListAdapter
    val accelerationCalculator = AccelerationCalculator()//to inject
    val accelerationDataManager= AccelerationDataManager(accelerationCalculator)//to inject
    private lateinit var viewModel: AccelerationCalculatorViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = DetectionRepository(DetectionDatabase.getDatabaseInstance(requireContext()))
        viewModel = ViewModelProvider(
            requireActivity(),
            AccelerationCalculatorViewModelFactory(accelerationDataManager, repository)
        ).get(AccelerationCalculatorViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Fragment", "onCreateView chiamato")
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val accelerationCalculator = AccelerationCalculator() //to inject
        val accelerationDataManager = AccelerationDataManager(accelerationCalculator) //to inject
        val repository =
            DetectionRepository(DetectionDatabase.getDatabaseInstance(requireContext()))

        // Inizializza il ViewModel qui
        viewModel = ViewModelProvider(
            this, AccelerationCalculatorViewModelFactory(accelerationDataManager, repository)
        ).get(AccelerationCalculatorViewModel::class.java)
        // Inizializza l'adapter
        detectionListAdapter = DetectionListAdapter(emptyList())

        rv_detections.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = detectionListAdapter
        }

        // Osserva i valori
        viewModel.getAllDetections().observe(requireActivity()) { detections ->
            detections.forEach { peak ->
                detectionListAdapter.detections = detections
                detectionListAdapter.notifyDataSetChanged()
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val date = Date(peak.date)
                println("Peak ${peak.peakValue} Date:${dateFormat.format(date)}")
            }

        }

        // Configura il pulsante
        btn_Start.setOnClickListener {
            val speedRecorderFragment = SpeedRecorderFragment()
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.main_fragment_container, speedRecorderFragment)
                addToBackStack(null)
                commit()
            }
        }

        btn_Profile.setOnClickListener {
            val loginScreenFragment = if(FirebaseAuth.getInstance().currentUser==null){LoginFragment()}else{ProfileFragment()}
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.main_fragment_container, loginScreenFragment)
                addToBackStack(null)
                commit()
            }
        }
    }

}