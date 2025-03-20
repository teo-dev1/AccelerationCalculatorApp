package com.example.punchspeedcalculator.repo

import android.content.Context
import android.content.SharedPreferences
import com.example.punchspeedcalculator.models.FireBaseAuthClient
import com.example.punchspeedcalculator.models.LoginResult
import com.google.firebase.auth.FirebaseAuth

class FireBaseAuthRepository(private val fireBaseClient: FireBaseAuthClient, val sharedPrefs: SharedPreferences) {

    fun getCurrentUserId(): String? {
        return sharedPrefs.getString("UID", "")
    }

    suspend fun login(email: String, password: String):LoginResult{
        val result=fireBaseClient.login(email, password)
        when (result){
            is LoginResult.Success ->{
                saveCurrentUserToSharedPrefs(result.userId)
            }
            is LoginResult.Error -> TODO()
            LoginResult.Loading -> TODO()
        }
        return result
    }

    fun logout() {
        fireBaseClient.logout()
    }

    private fun saveCurrentUserToSharedPrefs(uid: String) {
        sharedPrefs.edit().putString("UID", uid).apply()
    }


}