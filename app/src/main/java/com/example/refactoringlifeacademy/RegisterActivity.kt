package com.example.refactoringlifeacademy

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.refactoringlifeacademy.databinding.ActivityRegisterBinding
import com.example.refactoringlifeacademy.viewmodels.RegisterViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observer()
        activateButton()
    }

    // Obserever del cambio de valor cuando son validos o no los campos de email y contraseña
    private fun observer() {
        viewModel.isFormValid.observe(this) { isValid ->
            binding.buttonRegister.isEnabled = isValid
            activateButton()
        }
    }

    // Funcion que a traves de textwatch para activar o desactivar boton de registro dependiendo de la validacion de campos
    private fun activateButton() {
        binding.etEmail.addTextChangedListener { text ->
            viewModel.validateEmailPassword(
                text.toString(),
                binding.etPassword.text.toString(),
                binding.etConfirmPassword.text.toString()
            )
        }
        binding.etPassword.addTextChangedListener { text ->
            viewModel.validateEmailPassword(
                binding.etEmail.text.toString(),
                text.toString(),
                binding.etConfirmPassword.text.toString()
            )
        }
        binding.etConfirmPassword.addTextChangedListener { text ->
            viewModel.validateEmailPassword(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString(),
                text.toString()
            )
        }
    }
}
