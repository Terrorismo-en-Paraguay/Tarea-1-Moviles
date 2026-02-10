package com.example.tarea_1.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarea_1.ui.AuthRepository
import com.example.tarea_1.ui.UserUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class Loginviewmodel(private val repo: AuthRepository) : ViewModel() {

    private val _userUIState = MutableStateFlow<UserUIState>(UserUIState.Idle)
    val userUIState: StateFlow<UserUIState> get() = _userUIState.asStateFlow()
    private val _inputUser = MutableLiveData<String>("")
    private val _inputpass = MutableLiveData<String>("")
    private val _isButtonEnabled = MutableLiveData<Boolean>()
    val isButtonEnabled: LiveData<Boolean> get()= _isButtonEnabled

    init{
        _isButtonEnabled.value = false
    }

    fun onTextChanged(value: String)
    {
        _inputUser.value = value
        _isButtonEnabled.value = validateInput()
    }

    fun onTextpassword(value: String){
        _inputpass.value = value
        _isButtonEnabled.value = validateInput()
    }
    fun validateInput(): Boolean
    {
        if (_inputUser.value.isNotEmpty() && _inputpass.value.length >= 6 && _inputUser.value.isNotBlank() && _inputpass.value.isNotBlank()) {
            return true
        }else{
            return false
        }

    }

    fun iniciarSesion(email: String, password: String){
        viewModelScope.launch {
            _userUIState.value = UserUIState.Loading
            val result = repo.loguearte(email, password)
            result.onSuccess {
                user -> _userUIState.value = UserUIState.Success(user)
            }.onFailure {
                val error = _userUIState.value as UserUIState.Error
                _userUIState.value = UserUIState.Error(error.message ?: "Login incorrecto")
            }
        }
    }



}