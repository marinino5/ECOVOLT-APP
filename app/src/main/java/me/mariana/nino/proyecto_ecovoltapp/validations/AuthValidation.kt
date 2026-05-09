package me.mariana.nino.proyecto_ecovoltapp.validations

import android.util.Patterns
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

object AuthValidation {

    fun validateRegisterFields(
        nombre: String,
        correo: String,
        telefono: String,
        contrasena: String,
        confirmarContrasena: String
    ): String? {
        return when {
            nombre.isBlank() || correo.isBlank() || telefono.isBlank() ||
                    contrasena.isBlank() || confirmarContrasena.isBlank() -> {
                "Todos los campos son obligatorios."
            }

            !Patterns.EMAIL_ADDRESS.matcher(correo.trim()).matches() -> {
                "Ingresa un correo electrónico válido."
            }

            telefono.trim().length < 7 -> {
                "Ingresa un número de teléfono válido."
            }

            contrasena.length < 8 -> {
                "La contraseña debe tener mínimo 8 caracteres."
            }

            contrasena != confirmarContrasena -> {
                "Las contraseñas no coinciden."
            }

            else -> null
        }
    }

    fun validateLoginFields(
        correo: String,
        contrasena: String
    ): String? {
        return when {
            correo.isBlank() || contrasena.isBlank() -> {
                "Correo y contraseña son obligatorios."
            }

            !Patterns.EMAIL_ADDRESS.matcher(correo.trim()).matches() -> {
                "Ingresa un correo electrónico válido."
            }

            contrasena.length < 8 -> {
                "La contraseña debe tener mínimo 8 caracteres."
            }

            else -> null
        }
    }

    fun validateEmailForPasswordReset(
        correo: String
    ): String? {
        return when {
            correo.isBlank() -> {
                "Ingresa tu correo electrónico para recuperar la contraseña."
            }

            !Patterns.EMAIL_ADDRESS.matcher(correo.trim()).matches() -> {
                "Ingresa un correo electrónico válido."
            }

            else -> null
        }
    }

    fun getRegisterFirebaseErrorMessage(
        exception: Exception
    ): String {
        return when (exception) {
            is FirebaseAuthUserCollisionException -> {
                "Este correo ya está registrado."
            }

            is FirebaseAuthInvalidCredentialsException -> {
                "El correo o la contraseña no son válidos."
            }

            else -> {
                exception.message ?: "No se pudo crear la cuenta. Intenta nuevamente."
            }
        }
    }

    fun getLoginFirebaseErrorMessage(
        exception: Exception
    ): String {
        return when (exception) {
            is FirebaseAuthInvalidUserException -> {
                "No existe una cuenta registrada con este correo."
            }

            is FirebaseAuthInvalidCredentialsException -> {
                "Correo o contraseña incorrectos."
            }

            else -> {
                exception.message ?: "No se pudo iniciar sesión. Intenta nuevamente."
            }
        }
    }

    fun getPasswordResetFirebaseErrorMessage(
        exception: Exception
    ): String {
        return when (exception) {
            is FirebaseAuthInvalidUserException -> {
                "No existe una cuenta registrada con este correo."
            }

            is FirebaseAuthInvalidCredentialsException -> {
                "El correo ingresado no es válido."
            }

            else -> {
                exception.message ?: "No se pudo enviar el correo de recuperación."
            }
        }
    }
}