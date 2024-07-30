package com.example.refactoringlifeacademy.ui.register.presenter

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.refactoringlifeacademy.data.dto.model.UserProduct
import com.example.refactoringlifeacademy.data.dto.request.RegisterRequest
import com.example.refactoringlifeacademy.databinding.ActivityRegisterBinding
import com.example.refactoringlifeacademy.ui.login.presenter.LoginActivity
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
                    binding.prBarr.rlProgressBar.visibility = View.GONE
                    binding.incMsjError.tvEmailError.visibility = View.GONE

                }
                is StateRegister.Loading ->{
                    binding.incMsjError.tvEmailError.visibility = View.GONE
                    binding.prBarr.rlProgressBar.visibility = View.VISIBLE

                }
                is StateRegister.Success ->{
                    binding.prBarr.rlProgressBar.visibility = View.GONE
                    binding.incMsjError.tvEmailError.visibility = View.GONE
                    UserProduct.userToken = state.info.token
                    goToLogin()

                }
                is StateRegister.Error ->{
                    binding.prBarr.rlProgressBar.visibility = View.GONE
                    binding.incMsjError.tvEmailError.visibility = View.VISIBLE
                    binding.buttonRegister.isEnabled = false
                }
            }

        }
    }

    private fun goToLogin(){
        val intent = Intent(this, LoginActivity::class.java)
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

        binding.tvLoginHere.setOnClickListener{
            goToLogin()
        }
    }
}
