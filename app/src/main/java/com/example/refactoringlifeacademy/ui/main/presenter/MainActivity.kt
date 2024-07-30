package com.example.refactoringlifeacademy.ui.main.presenter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.refactoringlifeacademy.databinding.ActivityMainBinding
import com.example.refactoringlifeacademy.ui.prelogin.presenter.PreLogin

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash= installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        screenSplash.setKeepOnScreenCondition{true}

        val intent = Intent(this, PreLogin::class.java)
        startActivity(intent)
        finish()
    }
}
