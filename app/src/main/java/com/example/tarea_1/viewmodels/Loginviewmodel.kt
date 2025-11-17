package com.example.tarea_1.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar

class Loginviewmodel : ViewModel() {
    private val _inputText = MutableLiveData<String>()
    private val _isButtonEnabled = MutableLiveData<Boolean>()
    val isButtonEnabled: LiveData<Boolean> get()= _isButtonEnabled

    init{
        _isButtonEnabled.value = false
    }

    fun onTextChanged(value: String)
    {
        _inputText.value = value
        _isButtonEnabled.value = validateInput()
    }

    fun validateInput(): Boolean
    {
        _inputText.value.toString().length
        return !_inputText.value.isNullOrBlank()
    }

}