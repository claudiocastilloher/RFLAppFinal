package com.example.refactoringlifeacademy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.refactoringlifeacademy.utils.StringUtils.validationEmail
import com.example.refactoringlifeacademy.utils.StringUtils.validationPassword
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


import kotlinx.coroutines.launch




class ViewModelLogin : ViewModel() {
    var validationFields = MutableLiveData<Boolean>()

    private val _validateData = MutableLiveData<Boolean>()
    val validateData: LiveData<Boolean> = _validateData
    fun checkUserLogin(email: String, pass: String): Boolean {
        return email.validationEmail() && pass.validationPassword()
    }
    fun checkAllFields(email: String, pass: String) {
        validationFields.postValue(checkUserLogin(email, pass))
    }


    fun loginUser(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val isValid = checkUserLogin(email, password)
            _validateData.postValue(isValid)
        }
    }

}
