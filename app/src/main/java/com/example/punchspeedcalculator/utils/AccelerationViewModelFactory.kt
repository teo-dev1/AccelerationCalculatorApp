package com.example.punchspeedcalculator.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.punchspeedcalculator.repo.DetectionRepository
import com.example.punchspeedcalculator.viewmodels.AccelerationCalculatorViewModel

class AccelerationCalculatorViewModelFactory(
    private val accelerationDataManager: AccelerationDataManager,
    private val repository: DetectionRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccelerationCalculatorViewModel::class.java)) {
            return AccelerationCalculatorViewModel(accelerationDataManager, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}