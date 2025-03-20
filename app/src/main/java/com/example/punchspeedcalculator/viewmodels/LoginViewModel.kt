package com.example.punchspeedcalculator.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.punchspeedcalculator.models.LoginResult
import com.example.punchspeedcalculator.repo.FireBaseAuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class LoginViewModel(
    val repo:FireBaseAuthRepository
) : ViewModel(){

    //TODO farsi restituire l'uid dopo l'autenticazione e vedere come salvarlo nelle shared, per poterlo usare poi per farsi restituire i dati da firestore
    //TODO creare la factory questo viewmodel


    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> get() = _loginResult

    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    suspend fun login(email:String,password:String){
        if(email.isEmpty() || password.isEmpty()){
            _loginResult.value=LoginResult.Error("email o username vuoti")
            return
        }
        _loginResult.value=LoginResult.Loading
        viewModelScope.launch {
            when(val result=repo.login(email, password)){
                is LoginResult.Success ->{
                    _loginResult.value=result
                }
                is LoginResult.Error -> TODO()
                LoginResult.Loading -> TODO()
            }
        }







//        firebaseAuth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    _loginResult.value = LoginResult.Success("Login effettuato con successo!")
//                }else {
//                    val errorMessage = task.exception?.message ?: "Errore sconosciuto"
//                    _loginResult.value = LoginResult.Error(errorMessage)
//                }
//            }
    }


    fun logout(){
        firebaseAuth.signOut()
    }

    fun newProfileCreation(){

    }

    fun getCurrentLoggedUser(){
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val uid = user.uid // Questo è l'UID unico dell'utente
        }
    }
}