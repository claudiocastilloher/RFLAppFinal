package com.example.refactoringlifeacademy.utils
import android.util.Patterns


    fun String.isValidEmail(): Boolean {
        val emailRegex = Regex("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
        return emailRegex.matches(this)
    }

    fun String.isValidPassword(): Boolean {
        if (this.length !in 8..20) return false

        val specialCharacters = "@$!%*?&"
        val hasUpperCase = any { it.isUpperCase() }
        val hasLowerCase = any { it.isLowerCase() }
        val hasDigit = any { it.isDigit() }
        val hasSpecialChar = any { it in specialCharacters }
        val hasInvalidChar = any { !it.isLetterOrDigit() && it !in specialCharacters }

        return hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar && !hasInvalidChar
    }

    fun String.validationEmail(): Boolean {
        return this.isNotEmpty() && this.length <= 30 && Patterns.EMAIL_ADDRESS.matcher(this)
            .matches() && !this.contains(" ")
    }

