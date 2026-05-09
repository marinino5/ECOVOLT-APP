package me.mariana.nino.proyecto_ecovoltapp.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import me.mariana.nino.proyecto_ecovoltapp.R
import me.mariana.nino.proyecto_ecovoltapp.validations.AuthValidation

@Composable
fun LoginScreen(
    onBackToWelcome: () -> Unit,
    onGoToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val auth = FirebaseAuth.getInstance()

    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mostrarContrasena by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    var showResetDialog by remember { mutableStateOf(false) }
    var resetDialogMessage by remember { mutableStateOf("") }

    val verdeEcovolt = Color(0xFF0A8A33)
    val verdeOscuro = Color(0xFF04551F)
    val verdeClaro = Color(0xFFEAF6EA)
    val verdeSuave = Color(0xFFF3FAF4)
    val textoPrincipal = Color(0xFF102333)
    val textoSecundario = Color(0xFF66736D)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo_2),
            contentDescription = "Fondo Ecovolt",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.18f),
                            Color.White.copy(alpha = 0.06f),
                            Color.White.copy(alpha = 0.14f)
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginTopBar(
                onBackToWelcome = onBackToWelcome,
                textoPrincipal = textoPrincipal,
                verdeOscuro = verdeOscuro
            )

            Spacer(modifier = Modifier.height(28.dp))

            Image(
                painter = painterResource(id = R.drawable.ecosin),
                contentDescription = "Logo Ecovolt",
                modifier = Modifier
                    .fillMaxWidth(0.82f)
                    .height(98.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(34.dp))

            LoginFormCard(
                correo = correo,
                onCorreoChange = { correo = it },
                contrasena = contrasena,
                onContrasenaChange = { contrasena = it },
                mostrarContrasena = mostrarContrasena,
                onToggleMostrarContrasena = {
                    mostrarContrasena = !mostrarContrasena
                },
                errorMessage = errorMessage,
                isLoading = isLoading,
                verdeEcovolt = verdeEcovolt,
                verdeClaro = verdeClaro,
                verdeSuave = verdeSuave,
                textoPrincipal = textoPrincipal,
                textoSecundario = textoSecundario,
                onForgotPassword = {
                    errorMessage = ""

                    val emailError = AuthValidation.validateEmailForPasswordReset(correo)

                    if (emailError != null) {
                        errorMessage = emailError
                    } else {
                        isLoading = true

                        auth.sendPasswordResetEmail(correo.trim())
                            .addOnSuccessListener {
                                isLoading = false
                                resetDialogMessage =
                                    "Te enviamos un correo para restablecer tu contraseña."
                                showResetDialog = true
                            }
                            .addOnFailureListener { exception ->
                                isLoading = false
                                errorMessage =
                                    AuthValidation.getPasswordResetFirebaseErrorMessage(exception)
                            }
                    }
                },
                onGoToRegister = onGoToRegister,
                onLogin = {
                    errorMessage = ""

                    val validationError = AuthValidation.validateLoginFields(
                        correo = correo,
                        contrasena = contrasena
                    )

                    if (validationError != null) {
                        errorMessage = validationError
                    } else {
                        isLoading = true

                        auth.signInWithEmailAndPassword(
                            correo.trim(),
                            contrasena
                        ).addOnSuccessListener {
                            isLoading = false
                            onLoginSuccess()
                        }.addOnFailureListener { exception ->
                            isLoading = false
                            errorMessage =
                                AuthValidation.getLoginFirebaseErrorMessage(exception)
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(46.dp))
        }

        if (showResetDialog) {
            AlertDialog(
                onDismissRequest = {
                    showResetDialog = false
                },
                title = {
                    Text(
                        text = "CORREO ENVIADO",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = textoPrincipal,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                text = {
                    Text(
                        text = resetDialogMessage,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = textoSecundario,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                confirmButton = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(
                            onClick = {
                                showResetDialog = false
                            }
                        ) {
                            Text(
                                text = "Entendido",
                                color = verdeEcovolt,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun LoginTopBar(
    onBackToWelcome: () -> Unit,
    textoPrincipal: Color,
    verdeOscuro: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        IconButton(
            onClick = onBackToWelcome,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(44.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.92f))
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver",
                tint = verdeOscuro
            )
        }

        Text(
            text = "Iniciar sesión",
            modifier = Modifier.align(Alignment.Center),
            color = textoPrincipal,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
private fun LoginFormCard(
    correo: String,
    onCorreoChange: (String) -> Unit,
    contrasena: String,
    onContrasenaChange: (String) -> Unit,
    mostrarContrasena: Boolean,
    onToggleMostrarContrasena: () -> Unit,
    errorMessage: String,
    isLoading: Boolean,
    verdeEcovolt: Color,
    verdeClaro: Color,
    verdeSuave: Color,
    textoPrincipal: Color,
    textoSecundario: Color,
    onForgotPassword: () -> Unit,
    onGoToRegister: () -> Unit,
    onLogin: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.97f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bienvenido de nuevo",
                color = textoPrincipal,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Ingresa con tu correo y contraseña para continuar.",
                color = textoSecundario,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(22.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                LoginTextField(
                    value = correo,
                    onValueChange = onCorreoChange,
                    label = "Correo electrónico",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null,
                            tint = verdeEcovolt
                        )
                    },
                    keyboardType = KeyboardType.Email,
                    verdeEcovolt = verdeEcovolt
                )

                LoginTextField(
                    value = contrasena,
                    onValueChange = onContrasenaChange,
                    label = "Contraseña",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = verdeEcovolt
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = onToggleMostrarContrasena) {
                            Icon(
                                imageVector = if (mostrarContrasena) {
                                    Icons.Default.Visibility
                                } else {
                                    Icons.Default.VisibilityOff
                                },
                                contentDescription = "Mostrar contraseña",
                                tint = Color.Gray
                            )
                        }
                    },
                    keyboardType = KeyboardType.Password,
                    visualTransformation = if (mostrarContrasena) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    verdeEcovolt = verdeEcovolt
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = onForgotPassword
                ) {
                    Text(
                        text = "¿Olvidé mi contraseña?",
                        color = verdeEcovolt,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            LoginSecurityBox(
                verdeEcovolt = verdeEcovolt,
                verdeSuave = verdeSuave
            )

            if (errorMessage.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = errorMessage,
                    color = Color(0xFFB3261E),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (isLoading) {
                Spacer(modifier = Modifier.height(10.dp))

                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(50.dp)),
                    color = verdeEcovolt,
                    trackColor = verdeClaro
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = onLogin,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = verdeEcovolt,
                    disabledContainerColor = verdeEcovolt.copy(alpha = 0.55f)
                ),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(22.dp)
                    )
                } else {
                    Text(
                        text = "Ingresar",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = Color(0xFFE5E5E5)
                )

                Text(
                    text = "  acceso seguro  ",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )

                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = Color(0xFFE5E5E5)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "¿No tienes cuenta?",
                    color = textoSecundario,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.width(4.dp))

                TextButton(onClick = onGoToRegister) {
                    Text(
                        text = "Regístrate",
                        color = verdeEcovolt,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: @Composable () -> Unit,
    keyboardType: KeyboardType,
    verdeEcovolt: Color,
    modifier: Modifier = Modifier,
    trailingIcon: (@Composable () -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = label,
                color = Color(0xFF6F6F6F),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation,
        singleLine = true,
        shape = RoundedCornerShape(16.dp),
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = Color(0xFF102333)
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = verdeEcovolt,
            unfocusedBorderColor = Color(0xFFD9D9D9),
            cursorColor = verdeEcovolt,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color(0xFFFCFCFC)
        )
    )
}

@Composable
private fun LoginSecurityBox(
    verdeEcovolt: Color,
    verdeSuave: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(verdeSuave)
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Text(
            text = "Tu acceso está protegido",
            color = verdeEcovolt,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Usamos Firebase Authentication para validar tu inicio de sesión.",
            color = Color(0xFF6F6F6F),
            style = MaterialTheme.typography.bodySmall
        )
    }
}