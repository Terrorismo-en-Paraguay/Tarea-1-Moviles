package com.example.tarea_1.fragment

import android.app.DatePickerDialog
import android.icu.text.Edits
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tarea_1.R
import com.example.tarea_1.databinding.FragmentRegisterBinding
import com.example.tarea_1.ui.UserUIState
import com.example.tarea_1.viewmodels.RegisterViewModelFactory
import com.example.tarea_1.viewmodels.Registerviewmodel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.util.Calendar

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    private val registerviewmodel: Registerviewmodel by viewModels { RegisterViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerviewmodel.isButtonEnabled.observe(viewLifecycleOwner, object: Observer<Boolean> {
            override fun onChanged(value: Boolean) {
                binding.registrar.isEnabled = value
            }
        })
        binding.usuarioText.addTextChangedListener{registerviewmodel.onTextChanged(it.toString())}
        binding.contraseniaText.addTextChangedListener{registerviewmodel.onTextpasswordChanged(it.toString())}
        binding.contrasenia2Text.addTextChangedListener{registerviewmodel.onTextpassword2Changed(it.toString())}
        binding.contraseniaText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun afterTextChanged(p0: Editable?) {
                validarContrasenias()
            }
        })
        binding.contrasenia2Text.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun afterTextChanged(p0: Editable?) {
                validarContrasenias()
            }
        })
        binding.registrar.setOnClickListener {
            registerviewmodel.registrarCuenta(binding.usuarioText.text.toString(), binding.contraseniaText.text.toString())
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                registerviewmodel.userUIState.collect {state ->
                    when (state) {
                        is UserUIState.Loading -> {
                            binding.registrar.visibility = View.VISIBLE
                        }
                        is UserUIState.Success -> {
                            binding.registrar.visibility = View.GONE
                            findNavController().navigate(R.id.register_to_login)
                            Snackbar.make(binding.registrar, "Registro perfecto", Snackbar.LENGTH_SHORT).show()
                        }
                        is UserUIState.Error -> {
                            binding.registrar.visibility = View.VISIBLE
                            Snackbar.make(binding.root, state.message, Snackbar.LENGTH_SHORT).show()
                        }
                        is UserUIState.Idle -> {
                            binding.registrar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }


        binding.fechaNacimientoEdittext.setOnClickListener {
            datepicker(binding.fechaNacimientoEdittext)
        }
    }

    private fun datepicker(editText: EditText){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            requireContext(),
            { view, selectedYear, selectedMonth, selectedDay ->


                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"

                editText.setText(selectedDate)
            },
            year,
            month,
            day
        )
        dpd.show()
    }

    private fun validarContrasenias() {

        val contrasenia = binding.contraseniaText.text.toString()
        val confirmacion = binding.contrasenia2Text.text.toString()

        val layoutContrasenia = binding.nuevaContrasenia
        val layoutConfirmacion = binding.repiteContrasenia

        if (contrasenia.isEmpty() || confirmacion.isEmpty()) {
            layoutContrasenia.error = null
            layoutConfirmacion.error = null
            return
        }

        if (contrasenia != confirmacion) {
            val mensajeError = "Las contraseñas no coinciden"

            layoutContrasenia.isErrorEnabled = true
            layoutContrasenia.error = mensajeError

            layoutConfirmacion.isErrorEnabled = true
            layoutConfirmacion.error = mensajeError

        } else {

            layoutContrasenia.error = null
            layoutContrasenia.isErrorEnabled = false

            layoutConfirmacion.error = null
            layoutConfirmacion.isErrorEnabled = false
        }
    }

}