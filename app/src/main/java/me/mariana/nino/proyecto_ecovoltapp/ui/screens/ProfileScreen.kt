package me.mariana.nino.proyecto_ecovoltapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import me.mariana.nino.proyecto_ecovoltapp.R

@Composable
fun ProfileScreen(
    onBackClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onSupportClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val currentUser = auth.currentUser

    var userName by remember { mutableStateOf("Cargando...") }
    var userEmail by remember { mutableStateOf(currentUser?.email ?: "Sin correo registrado") }
    var userPhone by remember { mutableStateOf("Cargando...") }

    LaunchedEffect(currentUser?.uid) {
        val uid = currentUser?.uid

        if (uid != null) {
            db.collection("usuarios")
                .document(uid)
                .get()
                .addOnSuccessListener { document ->
                    userName = document.getString("nombre")
                        ?: document.getString("nombreCompleto")
                                ?: currentUser.displayName
                                ?: "Usuario Ecovolt"

                    userEmail = currentUser.email
                        ?: document.getString("correo")
                                ?: document.getString("email")
                                ?: "Sin correo registrado"

                    userPhone = document.getString("telefono")
                        ?: document.getString("phone")
                                ?: "Sin teléfono registrado"
                }
                .addOnFailureListener {
                    userName = currentUser.displayName ?: "Usuario Ecovolt"
                    userEmail = currentUser.email ?: "Sin correo registrado"
                    userPhone = "Sin teléfono registrado"
                }
        } else {
            userName = "Usuario Ecovolt"
            userEmail = "Sin correo registrado"
            userPhone = "Sin teléfono registrado"
        }
    }

    val green = Color(0xFF007A3D)
    val darkText = Color(0xFF172033)
    val softGreen = Color(0xFFEAF7EF)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo_3),
            contentDescription = "Fondo perfil",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.12f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp)
                .padding(top = 24.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileTopBar(
                onBackClick = onBackClick
            )

            Spacer(modifier = Modifier.height(34.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Mi perfil",
                    color = darkText,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Consulta y administra tu información personal.",
                    color = Color(0xFF6B746F),
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.97f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(88.dp)
                            .clip(CircleShape)
                            .background(softGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Foto de perfil",
                            tint = green,
                            modifier = Modifier.size(48.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = userName,
                        color = darkText,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    ProfileInfoRow(
                        icon = Icons.Default.Email,
                        text = userEmail,
                        color = green
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 10.dp),
                        color = Color(0xFFE0E0E0)
                    )

                    ProfileInfoRow(
                        icon = Icons.Default.Phone,
                        text = userPhone,
                        color = green
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.97f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ProfileActionItem(
                        icon = Icons.Default.History,
                        title = "Ver historial",
                        subtitle = "Revisa tus viajes realizados",
                        iconColor = green,
                        onClick = onHistoryClick
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 18.dp),
                        color = Color(0xFFE0E0E0)
                    )

                    ProfileActionItem(
                        icon = Icons.Default.HeadsetMic,
                        title = "Soporte",
                        subtitle = "Reporta un problema o solicita ayuda",
                        iconColor = green,
                        onClick = onSupportClick
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 18.dp),
                        color = Color(0xFFE0E0E0)
                    )

                    ProfileActionItem(
                        icon = Icons.Default.ExitToApp,
                        title = "Cerrar sesión",
                        subtitle = "Salir de tu cuenta",
                        iconColor = Color(0xFFE53935),
                        onClick = {
                            auth.signOut()
                            onLogoutClick()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ProfileTopBar(
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(top = 8.dp)
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(44.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.78f))
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                tint = Color(0xFF1E1E1E)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ecosin),
            contentDescription = "Logo Ecovolt",
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 10.dp)
                .height(72.dp)
                .width(230.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun ProfileInfoRow(
    icon: ImageVector,
    text: String,
    color: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(19.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            color = Color(0xFF303A35),
            fontSize = 13.sp
        )
    }
}

@Composable
fun ProfileActionItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    iconColor: Color = Color(0xFF006B2D),
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1E1E1E)
            )
        },
        supportingContent = {
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = Color.Gray
            )
        },
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor
            )
        },
        trailingContent = {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.Gray
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 6.dp, vertical = 4.dp)
    )
}