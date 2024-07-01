package com.example.refactoringlifeacademy.ui.register.presenter

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.refactoringlifeacademy.data.dto.request.RegisterRequest
import com.example.refactoringlifeacademy.databinding.ActivityRegisterBinding
import com.example.refactoringlifeacademy.ui.home.presenter.HomeActivity
import com.example.refactoringlifeacademy.ui.register.viewmodel.RegisterViewModel
import com.example.refactoringlifeacademy.utils.StateRegister


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
        registerButton()
    }

    private fun observer() {
        viewModel.data.observe(this) { state ->
            when(state){
                is StateRegister.FormValid ->{
                    binding.buttonRegister.isEnabled = state.state

                }
                is StateRegister.Loading ->{
                    binding.progressBar.rlProgressBar.visibility = View.VISIBLE
                    binding.incMsjError.tvEmailError.visibility = View.GONE

                }
                is StateRegister.Success ->{
                    binding.progressBar.rlProgressBar.visibility = View.GONE
                    goToHome()

                }
                is StateRegister.Error ->{
                    binding.progressBar.rlProgressBar.visibility = View.GONE
                    binding.incMsjError.tvEmailError.visibility = View.VISIBLE

                }
            }

        }
    }
    fun goToHome(){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

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

    private fun registerButton() {
        binding.buttonRegister.isEnabled = false
        binding.buttonRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()
            if (password == confirmPassword) {
                val requestRegister = RegisterRequest(email, password)
                viewModel.registerUser(requestRegister)
            }
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
