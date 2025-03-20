package com.example.punchspeedcalculator.models

class AccelerationCalculator {

    // Array per mantenere i valori della gravità sui tre assi
    private val gravity = FloatArray(3)

    // Fattore di smorzamento per il filtro passa-basso
    private val alpha = 0.8f

    // Funzione per calcolare l'accelerazione lineare rimuovendo la gravità
    fun calculateLinearAcceleration(x: Float, y: Float, z: Float): FloatArray {
        // Isola la gravità usando un filtro passa-basso
        gravity[0] = alpha * gravity[0] + (1 - alpha) * x
        gravity[1] = alpha * gravity[1] + (1 - alpha) * y
        gravity[2] = alpha * gravity[2] + (1 - alpha) * z

        // Calcola l'accelerazione lineare
        return FloatArray(3).apply {
            this[0] = x - gravity[0]
            this[1] = y - gravity[1]
            this[2] = z - gravity[2]
        }
    }

    // Funzione per calcolare l'accelerazione totale (magnitudo) in m/s²
    fun calculateTotalAcceleration(x: Float, y: Float, z: Float): Float {
        val linearAcc = calculateLinearAcceleration(x, y, z)
        return Math.sqrt(
            (linearAcc[0] * linearAcc[0] +
                    linearAcc[1] * linearAcc[1] +
                    linearAcc[2] * linearAcc[2]).toDouble()
        ).toFloat()
    }
}
