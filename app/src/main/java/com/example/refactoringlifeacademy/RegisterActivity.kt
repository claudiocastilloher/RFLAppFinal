package com.example.refactoringlifeacademy

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
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
        initListeners()
    }

    // Obserever del cambio de valor cuando son validos o no los campos de email y contraseña
    private fun observer() {
        viewModel.isFormValid.observe(this) { isValid ->
            binding.butttonRegister.isEnabled = isValid
            activateButton()
        }
    }

    // Funcion que a traves de un textwatch para activar o desactivar boton de registro dependiendo de la validacion de campos
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

    private fun initListeners() {
        binding.cbShowPassword.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.etPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            } else {
                binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            binding.etPassword.setSelection(binding.etPassword.text.length)
        }

        binding.cbShowPasswordComfirm.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.etConfirmPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            } else {
                binding.etConfirmPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            }
            binding.etConfirmPassword.setSelection(binding.etConfirmPassword.text.length)
        }
    }
}
