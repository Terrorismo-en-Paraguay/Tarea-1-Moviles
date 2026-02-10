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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tarea_1.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.example.tarea_1.R
import com.example.tarea_1.ui.UserUIState
import com.example.tarea_1.viewmodels.LoginViewModelFactory
import com.example.tarea_1.viewmodels.Loginviewmodel
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private val loginviewmodel: Loginviewmodel by viewModels { LoginViewModelFactory() }

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
        loginviewmodel.isButtonEnabled.observe(viewLifecycleOwner, object: Observer<Boolean> {
            override fun onChanged(value: Boolean) {
                binding.iniciarSesion.isEnabled = value
            }
        })
        binding.nombreUsuarioContenido.addTextChangedListener{loginviewmodel.onTextChanged(it.toString())}
        binding.contraseniaContenido.addTextChangedListener{loginviewmodel.onTextpassword(it.toString())}

        binding.iniciarSesion.setOnClickListener {
            loginviewmodel.iniciarSesion(binding.nombreUsuarioContenido.text.toString(),binding.contraseniaContenido.text.toString())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginviewmodel.userUIState.collect { state ->
                    when(state) {
                        is UserUIState.Loading -> {
                            binding.iniciarSesion.visibility = View.VISIBLE
                        }
                        is UserUIState.Success -> {
                            binding.iniciarSesion.visibility = View.GONE
                            findNavController().navigate(R.id.login_to_list)
                        }
                        is UserUIState.Error -> {
                            binding.iniciarSesion.visibility = View.GONE
                            Snackbar.make(view, state.message, Snackbar.LENGTH_LONG).show()
                        }
                        is  UserUIState.Idle -> {
                            binding.iniciarSesion.visibility = View.GONE
                        }
                    }
                }
            }
        }

        binding.sesionGoogle.setOnClickListener {
            findNavController().navigate(R.id.login_to_list)
        }

        binding.contraseniaOlvidada.setOnClickListener {
            findNavController().navigate(R.id.login_to_register)
        }


    }

}