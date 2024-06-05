package com.example.refactoringlifeacademy

// Esta clase tiene metodos que devuelven si un usuario(email) y contraseña son validos
// Emplea expresiones regulares obtenidas de internet para hacer las validaciones pueden ser comprobadas en https://regex101.com/
class RegisterValidation {

    // Función para validar el correo electrónico
    fun isValidRegisterEmail(email: String): Boolean {
        val emailRegex = Regex("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
        return emailRegex.matches(email)
    }

    // Función para validar la contraseña
    fun isValidRegisterPassword(password: String): Boolean {
        // Reglas de validación de contraseña (Faltarian o se modificarian segun lo que responda Backend respecto a la consulta de caracteristicas de las contraseñas):
        // - Mínimo 8 caracteres
        // - Al menos una letra mayúscula
        // - Al menos una letra minúscula
        // - Al menos un número
        // - Al menos un carácter especial (@$!%*+#=.?&)
        val passwordRegex =
            Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*+#=.?&]{8,}\$")
        return passwordRegex.matches(password)
    }
}
