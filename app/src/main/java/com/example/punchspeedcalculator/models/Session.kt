package com.example.punchspeedcalculator.models

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener


class Session {
    var userIsLogged=false
    var context:Context?=null
    var username=""
    var instance:FirebaseAuth=FirebaseAuth.getInstance()


    fun sessionTokenListener(){//va spostato, non va qui
        val firebaseAuth = FirebaseAuth.getInstance()
        val authStateListener = AuthStateListener { firebaseAuth1: FirebaseAuth ->
            val user = firebaseAuth1.currentUser
            if (user != null) {
                Log.d("AuthState", "Utente autenticato: " + user.uid)
            } else {
                Log.d("AuthState", "Utente non autenticato")
            }
        }
        firebaseAuth.addAuthStateListener(authStateListener)
    }
}