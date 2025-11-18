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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tarea_1.R
import com.example.tarea_1.databinding.FragmentRegisterBinding
import com.example.tarea_1.viewmodels.Registerviewmodel
import java.util.Calendar

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

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
        val registerviewmodel = ViewModelProvider(this)[Registerviewmodel::class.java]
        registerviewmodel.isButtonEnabled.observe(viewLifecycleOwner, object: Observer<Boolean> {
            override fun onChanged(value: Boolean) {
                binding.registrar.isEnabled = value
            }
        })
        binding.usuarioText.addTextChangedListener{registerviewmodel.onTextChanged(it.toString())}
        binding.contraseniaText.addTextChangedListener{registerviewmodel.onTextpasswordChanged(it.toString())}
        binding.contrasenia2Text.addTextChangedListener{registerviewmodel.onTextpassword2Changed(it.toString())}

        binding.registrar.setOnClickListener {
            findNavController().navigate(R.id.register_to_login)
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