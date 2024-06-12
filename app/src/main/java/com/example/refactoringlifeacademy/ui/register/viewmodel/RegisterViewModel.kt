package com.example.refactoringlifeacademy.ui.register.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.refactoringlifeacademy.utils.isValidEmail
import com.example.refactoringlifeacademy.utils.isValidPassword
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _isFormValid = MutableLiveData<Boolean>()
    val isFormValid: LiveData<Boolean> = _isFormValid


    fun validateEmailPassword(email: String, password: String, confirmPassword: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val isEmailValid = email.isValidEmail()
            val isPasswordValid = password.isValidPassword()
            val isConfirmPasswordValid = confirmPassword.isValidPassword()
            updateFormValidity(isEmailValid, isPasswordValid, isConfirmPasswordValid)
        }
    }

    private fun updateFormValidity(
        isEmailValid: Boolean,
        isPasswordValid: Boolean,
        isPasswordConfValid: Boolean
    ) {
        _isFormValid.postValue(isEmailValid && isPasswordValid && isPasswordConfValid)
    }
}
