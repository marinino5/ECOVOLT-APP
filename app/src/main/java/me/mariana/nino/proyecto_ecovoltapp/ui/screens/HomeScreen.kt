package me.mariana.nino.proyecto_ecovoltapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricScooter
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onMapClick: () -> Unit,
    onFleetClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSupportClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFEFFFF4),
                        Color(0xFFFFFFFF)
                    )
                )
            )
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ElectricScooter,
                    contentDescription = null,
                    tint = Color(0xFF007A3D),
                    modifier = Modifier.size(34.dp)
                )

                Text(
                    text = "ECOVOLT",
                    modifier = Modifier.padding(start = 10.dp),
                    color = Color(0xFF007A3D),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Hola, bienvenida",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF263238),
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Selecciona qué quieres hacer hoy con Ecovolt.",
                color = Color.DarkGray,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(26.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF007A3D)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(34.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Mapa de estaciones",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Encuentra parques con patinetes disponibles en Bucaramanga.",
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = onMapClick,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFF007A3D)
                        )
                    ) {
                        Text(
                            text = "Ver mapa",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Accesos rápidos",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF263238),
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(14.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                HomeOptionCard(
                    title = "Flota disponible",
                    subtitle = "Consulta los patinetes por estación.",
                    icon = Icons.Default.TwoWheeler,
                    onClick = onFleetClick
                )

                HomeOptionCard(
                    title = "Historial",
                    subtitle = "Revisa tus recorridos anteriores.",
                    icon = Icons.Default.History,
                    onClick = onHistoryClick
                )

                HomeOptionCard(
                    title = "Perfil",
                    subtitle = "Consulta tu información de usuario.",
                    icon = Icons.Default.Person,
                    onClick = onProfileClick
                )

                HomeOptionCard(
                    title = "Soporte",
                    subtitle = "Reporta inconvenientes o dudas.",
                    icon = Icons.Default.SupportAgent,
                    onClick = onSupportClick
                )
            }
        }
    }
}

@Composable
private fun HomeOptionCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        color = Color(0xFFE6F4EC),
                        shape = RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF007A3D)
                )
            }

            Column(
                modifier = Modifier.padding(start = 14.dp)
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF263238)
                )

                Text(
                    text = subtitle,
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}