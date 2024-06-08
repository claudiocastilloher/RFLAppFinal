package com.example.refactoringlifeacademy.utils


fun String.isValidEmail(): Boolean {
    val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}\$")
    return emailRegex.matches(this)
}

fun String.isValidPassword(): Boolean {
    val passwordRegex =
        Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,20}\$")
    return passwordRegex.matches(this)
}
