package com.example.refactoringlifeacademy.ui.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.refactoringlifeacademy.data.dto.model.UserProduct
import com.example.refactoringlifeacademy.data.dto.request.LoginRequest
import com.example.refactoringlifeacademy.data.repository.LoginRepository
import com.example.refactoringlifeacademy.utils.LoginState
import com.example.refactoringlifeacademy.utils.isValidEmail
import com.example.refactoringlifeacademy.utils.isValidPassword
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ViewModelLogin(private val repository: LoginRepository = LoginRepository()) : ViewModel() {

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState

    private fun checkUserLogin(email: String, pass: String): Boolean {
        return email.isNotEmpty() && pass.isNotEmpty() && email.isValidEmail() && pass.isValidPassword()
    }
    fun checkAllFields(email: String, pass: String) {
        _loginState.postValue(LoginState.FormValid(checkUserLogin(email, pass)))
    }


    fun loginUser(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _loginState.postValue(LoginState.Loading)
            try {
                val response = repository.loginUser(LoginRequest(email, password))
                if (response.isSuccessful) {
                    response.body()?.let {
                        UserProduct.userToken = it.token
                        _loginState.postValue(LoginState.Success(it))
                    } ?: _loginState.postValue(LoginState.Error("No Data"))
                } else {
                    _loginState.postValue(LoginState.Error("Error Service"))
                }
            }catch (e: Exception){
                _loginState.postValue(LoginState.Error("Error: ${e.message}"))
            }
        }
    }

}
