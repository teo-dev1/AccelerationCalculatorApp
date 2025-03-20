package com.example.punchspeedcalculator.models

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class FireBaseAuthClient(val firebaseAuth: FirebaseAuth){//la parte di logica di bussiness non dovrebbe avere connessioni strette con librerie usate nel progetto o librerie/funzionalità android, tuttavia per quello che riguarda firebase e dove è necessario implementare un salvataggio nelle shared prefs si fa un eccezione, perchè si hanno le "mani abbastanza legate

     suspend fun login(email:String,password:String):LoginResult{
        return try {
            val result= firebaseAuth
                .signInWithEmailAndPassword(email,password)
                .await()
            val userId=result.user?.uid
            return if (userId!=null) LoginResult.Success(userId) else LoginResult.Error("UID non trovato")
        }catch (e:Exception){
            LoginResult.Error(e.message ?: "Errore sconosciuto")//stringa di fallback
        }
    }

    fun logout(){
       firebaseAuth.signOut()
    }

}