package com.example.refactoringlifeacademy.ui.financeFragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.refactoringlifeacademy.data.repository.FinanceRepository
import com.example.refactoringlifeacademy.utils.FinanceState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FinanceViewModel(private val repository: FinanceRepository = FinanceRepository()) : ViewModel() {
    private val _financeState = MutableLiveData<FinanceState>()
    val financeState: LiveData<FinanceState> = _financeState

    fun getFinanceMethods(){
        CoroutineScope(Dispatchers.IO).launch {
            _financeState.postValue(FinanceState.Loading)
            val response = repository.getFinanceMethods()
            if (response.isSuccessful){
                response.body()?.let {
                    _financeState.postValue(FinanceState.Success(it))
                } ?: _financeState.postValue(FinanceState.Error("Empty response body"))
            }else {
                _financeState.postValue(FinanceState.Error("Failed: ${response.message()}"))
            }
        }
    }
}