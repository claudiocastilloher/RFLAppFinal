package com.example.refactoringlifeacademy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.refactoringlifeacademy.utils.StringUtils.isValidEmail
import com.example.refactoringlifeacademy.utils.StringUtils.isValidPassword

class RegisterViewModel : ViewModel() {
    private val _isFormValid = MutableLiveData<Boolean>()
    val isFormValid: LiveData<Boolean> = _isFormValid

    // Funcion en hilo secundario para validar email
    fun validateEmail(email: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val isEmailValid = email.isValidEmail()
            updateFormValidity(isEmailValid, true, true)
        }
    }

    // Funcion en hilo secundario para validar contraseña
    fun validatePassword(password: String, option: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val isPasswordValid = password.isValidPassword()
            when (option) {
                0 ->
                    updateFormValidity(true, isPasswordValid, true)

                1 ->
                    updateFormValidity(true, true, isPasswordValid)
            }
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
