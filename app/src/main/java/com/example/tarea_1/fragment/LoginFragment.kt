package com.example.tarea_1.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tarea_1.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.example.tarea_1.R
import com.example.tarea_1.viewmodels.Loginviewmodel


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        val loginviewmodel = ViewModelProvider(this)[Loginviewmodel::class.java]
        loginviewmodel.isButtonEnabled.observe(viewLifecycleOwner, object: Observer<Boolean> {
            override fun onChanged(value: Boolean) {
                binding.iniciarSesion.isEnabled = value
            }
        })
        binding.nombreUsuarioContenido.addTextChangedListener{loginviewmodel.onTextChanged(it.toString())}
        binding.contraseniaContenido.addTextChangedListener{loginviewmodel.onTextChanged(it.toString())}

        binding.iniciarSesion.setOnClickListener {
            if ((binding.nombreUsuarioContenido.text.toString() == "admin") && binding.contraseniaContenido.text.toString() == "1234")
            {
                val snackbar = Snackbar.make(binding.root, "Login correcto",Snackbar.LENGTH_LONG)
                snackbar.show()
            }else{
                val snackbar = Snackbar.make(binding.root, "Login incorrecto",Snackbar.LENGTH_LONG)
                snackbar.show()
            }
        }

        binding.sesionGoogle.setOnClickListener {
            val snackbar = Snackbar.make(binding.root, "Esta funcíon no esta disponible",Snackbar.LENGTH_LONG)
            snackbar.show()
        }

        binding.contraseniaOlvidada.setOnClickListener {
            findNavController().navigate(R.id.login_to_register)
        }


    }

}