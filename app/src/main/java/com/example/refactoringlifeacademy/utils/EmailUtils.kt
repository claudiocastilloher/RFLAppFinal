package com.example.refactoringlifeacademy.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri

object EmailUtils {
    private const val TO_EMAIL = "destinatario@example.com"//CAMBIAR EL DESTINATARIO UNA VEZ DEFINIDO
    private const val SUBJECT = "Fallos en aplicacion"
    private const val BODY = "Describa su problema a continuación"

    @SuppressLint("QueryPermissionsNeeded")
    fun sendEmail(context: Context){
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(TO_EMAIL))
            putExtra(Intent.EXTRA_SUBJECT, SUBJECT)
            putExtra(Intent.EXTRA_TEXT, BODY)
        }

        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(Intent.createChooser(intent, "Elige una aplicación de correo"))
        }
    }
}