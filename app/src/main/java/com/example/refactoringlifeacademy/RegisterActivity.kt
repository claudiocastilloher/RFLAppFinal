package com.example.refactoringlifeacademy

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.refactoringlifeacademy.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initListeners()
    }
    private fun initListeners() {
        binding.cbShowPassword.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                binding.etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }else{
                binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            binding.etPassword.setSelection(binding.etPassword.text.length)
        }

        binding.cbShowPasswordComfirm.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                binding.etConfirmPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            }else{
                binding.etConfirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            binding.etConfirmPassword.setSelection(binding.etConfirmPassword.text.length)
        }
    }
}
