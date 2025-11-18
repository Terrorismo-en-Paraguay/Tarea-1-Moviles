package com.example.tarea_1.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Registerviewmodel : ViewModel() {
    private val _inputName = MutableLiveData<String>("")
    private val _inputPass = MutableLiveData<String>("")
    private val _inputPass2 = MutableLiveData<String>("")
    private val _isButtonEnabled = MutableLiveData<Boolean>()
    val isButtonEnabled: LiveData<Boolean> get()= _isButtonEnabled

    init {
        _isButtonEnabled.value = false
    }

    fun onTextChanged(value: String){
        _inputName.value = value
        _isButtonEnabled.value = validateInput()
    }

    fun onTextpasswordChanged(value: String){
        _inputPass.value = value
        _isButtonEnabled.value = validateInput()
    }
    fun onTextpassword2Changed(value: String){
        _inputPass2.value = value
        _isButtonEnabled.value = validateInput()
    }

    fun validateInput(): Boolean
    {
        if (_inputName.value.length >= 4 && _inputPass.value.length >= 4 && _inputPass.value == _inputPass2.value) {
            return true
        }else{
            return false
        }

    }
}