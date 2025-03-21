package com.example.punchspeedcalculator.models

sealed class LoginResult{
    data class Success(val userId: String) : LoginResult()
    data class Error(val errorMessage: String) : LoginResult()
    object Loading : LoginResult()

}
