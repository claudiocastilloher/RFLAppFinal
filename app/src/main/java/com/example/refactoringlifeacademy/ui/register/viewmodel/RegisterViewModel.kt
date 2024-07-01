package com.example.refactoringlifeacademy.ui.register.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.refactoringlifeacademy.data.dto.request.RegisterRequest
import com.example.refactoringlifeacademy.data.repository.RegisterRepository
import com.example.refactoringlifeacademy.utils.StateRegister
import com.example.refactoringlifeacademy.utils.isValidEmail
import com.example.refactoringlifeacademy.utils.isValidPassword
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(private val regRepository: RegisterRepository = RegisterRepository()) :
    ViewModel() {
    private val _data = MutableLiveData<StateRegister>()
    val data: LiveData<StateRegister> = _data



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
        _data.postValue(StateRegister.FormValid(isEmailValid && isPasswordValid && isPasswordConfValid))
    }

    fun registerUser(requestRegister: RegisterRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            _data.postValue(StateRegister.Loading)
            val regResponse = regRepository.registerUser(requestRegister)
            if(regResponse.isSuccessful){
                regResponse.body()?.let {
                    _data.postValue(StateRegister.Success(it))
                } ?: _data.postValue(StateRegister.Error("No Data"))
            }else{
                _data.postValue(StateRegister.Error("Error Service"))
            }
        }
    }
}
