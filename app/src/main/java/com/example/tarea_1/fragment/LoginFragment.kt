package com.example.tarea_1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tarea_1.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar

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

        if (binding.nombreUsuarioContenido.text.toString().isEmpty()){

        }

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

    }
}