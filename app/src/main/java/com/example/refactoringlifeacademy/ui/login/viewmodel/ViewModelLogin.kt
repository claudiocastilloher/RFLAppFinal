package com.example.refactoringlifeacademy.ui.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.refactoringlifeacademy.data.dto.request.LoginRequest
import com.example.refactoringlifeacademy.data.repository.LoginRepository
import com.example.refactoringlifeacademy.utils.isValidEmail
import com.example.refactoringlifeacademy.utils.isValidPassword
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ViewModelLogin(private val repository: LoginRepository = LoginRepository()) : ViewModel() {
    var validationFields = MutableLiveData<Boolean>()

    private val _validateData = MutableLiveData<Boolean>()
    val validateData: LiveData<Boolean> = _validateData

    private fun checkUserLogin(email: String, pass: String): Boolean {
        return email.isNotEmpty() && pass.isNotEmpty() && email.isValidEmail() && pass.isValidPassword()
    }
    fun checkAllFields(email: String, pass: String) {
        validationFields.postValue(checkUserLogin(email, pass))
    }


    fun loginUser(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.loginUser(LoginRequest(email, password))
        }
    }

}
