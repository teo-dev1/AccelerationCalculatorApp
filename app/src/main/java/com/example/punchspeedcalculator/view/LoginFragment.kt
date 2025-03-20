package com.example.punchspeedcalculator.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.punchspeedcalculator.R
import com.example.punchspeedcalculator.models.LoginResult
import com.example.punchspeedcalculator.viewmodels.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login_screen.*
import kotlinx.coroutines.launch

class LoginFragment: Fragment() {

    private lateinit var loginViewModel: LoginViewModel  //si può inizializzare tramite by viewmodels solo se non viene passato nessun parametro al viewmodel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        observeLoginResponse()
        onClickListeners()
    }


    private fun onClickListeners(){
        btn_login.setOnClickListener {
            lifecycleScope.launch {
                loginViewModel.login(et_email.text.toString(),et_password.text.toString())
            }
        }
    }


    private fun setupViewModel(){
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)//creare factory
    }

    private fun observeLoginResponse(){
        loginViewModel.loginResult.observe(viewLifecycleOwner){result->
            when (result) {
                is LoginResult.Success -> {
                    Toast.makeText(requireContext(),"logged in", Toast.LENGTH_SHORT).show()
                    // Esempio: Naviga alla schermata principale
                }
                is LoginResult.Error -> {
                    Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}