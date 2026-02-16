package com.example.tarea_1.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarea_1.ui.AuthRepository
import com.example.tarea_1.ui.NewUserUiState
import com.example.tarea_1.ui.UserUIState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class Registerviewmodel(private val repo: AuthRepository) : ViewModel() {
    private val _userUIState = MutableStateFlow<NewUserUiState>(NewUserUiState.Idle)
    val userUIState: StateFlow<NewUserUiState> = _userUIState.asStateFlow()
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
        return _inputName.value.length >= 4 && _inputPass.value.length >= 6 && _inputPass.value == _inputPass2.value
    }

    fun registrarCuenta(email: String, password: String, fechaNacimiento: String){
        viewModelScope.launch {
            _userUIState.value = NewUserUiState.Loading
            val result = repo.registrarse(email, password, fechaNacimiento)
            result.onSuccess {
                user -> _userUIState.value = NewUserUiState.Created(user)
            }.onFailure { error ->
                _userUIState.value = NewUserUiState.Error(error.message ?: "No se pudo registrar")
            }
        }
    }
}