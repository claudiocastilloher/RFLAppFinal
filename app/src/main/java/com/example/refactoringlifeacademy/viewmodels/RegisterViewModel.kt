package com.example.refactoringlifeacademy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.refactoringlifeacademy.utils.isValidEmail
import com.example.refactoringlifeacademy.utils.isValidPassword

class RegisterViewModel : ViewModel() {
    private val _isFormValid = MutableLiveData<Boolean>()
    val isFormValid: LiveData<Boolean> = _isFormValid

    // Funcion en hilo secundario para validar email y password
    fun validateEmailPassword(email: String, password: String, confirmPassword: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val isEmailValid = email.isValidEmail()
            val isPasswordValid = password.isValidPassword()
            val isConfirmPasswordValid = confirmPassword.isValidPassword()
            updateFormValidity(isEmailValid, isPasswordValid, isConfirmPasswordValid)
        }
    }

    // Funcion que actualiza el valor del LiveData
    private fun updateFormValidity(
        isEmailValid: Boolean,
        isPasswordValid: Boolean,
        isPasswordConfValid: Boolean
    ) {
        _isFormValid.postValue(isEmailValid && isPasswordValid && isPasswordConfValid)
    }
}
