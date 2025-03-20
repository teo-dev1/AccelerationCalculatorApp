package com.example.punchspeedcalculator.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FireBaseInstantiator:RemoteServiceProvider<FirebaseFirestore>{
    override fun provideDatabaseSevice(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}
object FirebaseAuthInstantiator:RemoteLoginServiceProvider<FirebaseAuth>{
    override fun provideLoginService(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

}