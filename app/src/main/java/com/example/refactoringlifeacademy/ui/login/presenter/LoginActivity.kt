package com.example.refactoringlifeacademy.ui.login.presenter

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.refactoringlifeacademy.ui.login.viewmodel.ViewModelLogin
import com.example.refactoringlifeacademy.databinding.ActivityLoginBinding
import com.example.refactoringlifeacademy.ui.home.presenter.HomeActivity
import com.example.refactoringlifeacademy.ui.register.presenter.RegisterActivity
import com.example.refactoringlifeacademy.utils.LoginState

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val viewModel: ViewModelLogin by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observer()
        activateButton()
        initListeners()
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
        binding.tvRegisterHere.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnEnter.isEnabled = false

        binding.btnEnter.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.loginUser(email, password)
        }
    }

    private fun observer() {
        viewModel.loginState.observe(this){state ->
            when(state){
                is LoginState.FormValid -> {
                    binding.btnEnter.isEnabled = state.state
                }
                is LoginState.Succes -> {
                    binding.progressBar.rlProgressBar.visibility = View.GONE
                    goToHome()
                }
                is LoginState.Error -> {
                    binding.progressBar.rlProgressBar.visibility = View.GONE
                    binding.incMsjError.tvEmailError.visibility = View.VISIBLE
                }
                is LoginState.Loading -> {
                    binding.progressBar.rlProgressBar.visibility = View.VISIBLE
                    binding.incMsjError.tvEmailError.visibility = View.GONE
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
        binding.etEmail.addTextChangedListener {
            validateFields()
        }
        binding.etPassword.addTextChangedListener {
            validateFields()
        }
    }

    private fun validateFields() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        viewModel.checkAllFields(email, password)
    }
}
