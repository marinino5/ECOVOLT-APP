package me.mariana.nino.proyecto_ecovoltapp.ui.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.mariana.nino.proyecto_ecovoltapp.data.Vehicle
import me.mariana.nino.proyecto_ecovoltapp.data.VehicleStatus

@Composable
fun VehicleDetailScreen(
    vehicle: Vehicle,
    onBack: () -> Unit,
    onReserveClick: () -> Unit,
    onRouteClick: () -> Unit
) {
    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFEFFAF1),
            Color.White,
            Color(0xFFE7F6EA)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundBrush)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 22.dp, vertical = 10.dp)
        ) {
            TextButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text(
                    text = "← Volver",
                    color = Color(0xFF1B5E20),
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "DETALLE DEL VEHÍCULO",
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF1B5E20),
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Revisa la información antes de reservar.",
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF4E5D52),
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(14.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.97f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 7.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DetailVehicleImage(vehicle = vehicle)

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = vehicle.model,
                        color = Color(0xFF143D1E),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = vehicle.code,
                        color = Color(0xFF6A756C),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(9.dp))

                    StatusBadgeDetail(status = vehicle.status)

                    Spacer(modifier = Modifier.height(14.dp))

                    BatterySectionDetail(battery = vehicle.battery)

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        DetailMetricCard(
                            title = "Tarifa",
                            value = "$${vehicle.pricePerMinute}",
                            subtitle = "COP/min",
                            modifier = Modifier.weight(1f)
                        )

                        DetailMetricCard(
                            title = "Autonomía",
                            value = "${vehicle.autonomyKm}",
                            subtitle = "km aprox.",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        DetailMetricCard(
                            title = "Distancia",
                            value = "${vehicle.distanceMeters}",
                            subtitle = "m de ti",
                            modifier = Modifier.weight(1f)
                        )

                        DetailMetricCard(
                            title = "Tipo",
                            value = vehicle.type.label,
                            subtitle = "eléctrico",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    DetailInfoBox(
                        title = "Ubicación actual",
                        description = vehicle.location
                    )

                    if (!vehicle.isAvailable) {
                        Spacer(modifier = Modifier.height(12.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFFEBEE)
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                        ) {
                            Text(
                                text = "Este vehículo está en uso. Puedes revisar la información, pero no reservarlo.",
                                modifier = Modifier.padding(13.dp),
                                color = Color(0xFFC62828),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 18.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = onRouteClick,
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color(0xFF1B5E20)
                            )
                        ) {
                            Text(
                                text = "Ver ruta",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Button(
                            onClick = onReserveClick,
                            enabled = vehicle.isAvailable,
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1B8F3A),
                                contentColor = Color.White,
                                disabledContainerColor = Color(0xFFBDBDBD),
                                disabledContentColor = Color.White
                            )
                        ) {
                            Text(
                                text = if (vehicle.isAvailable) "Reservar" else "En uso",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(70.dp))
        }
    }
}

@Composable
private fun DetailVehicleImage(
    vehicle: Vehicle
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(118.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFDDF5E4),
                        Color(0xFFF8FFF9)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = getVehicleImageRes(vehicle)),
            contentDescription = vehicle.model,
            modifier = Modifier.size(116.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun StatusBadgeDetail(
    status: VehicleStatus
) {
    val isAvailable = status == VehicleStatus.DISPONIBLE

    Surface(
        shape = RoundedCornerShape(50.dp),
        color = if (isAvailable) Color(0xFFE0F4E6) else Color(0xFFFFEBEE)
    ) {
        Text(
            text = status.label.uppercase(),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 7.dp),
            color = if (isAvailable) Color(0xFF1B8F3A) else Color(0xFFC62828),
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
private fun BatterySectionDetail(
    battery: Int
) {
    val batteryColor = when {
        battery >= 60 -> Color(0xFF1B8F3A)
        battery >= 35 -> Color(0xFFC77700)
        else -> Color(0xFFC62828)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Nivel de batería",
                color = Color(0xFF253328),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Text(
                text = "$battery%",
                color = batteryColor,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(7.dp))

        LinearProgressIndicator(
            progress = { battery / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .height(7.dp)
                .clip(RoundedCornerShape(50.dp)),
            color = batteryColor,
            trackColor = Color(0xFFDCE8DF)
        )
    }
}

@Composable
private fun DetailMetricCard(
    title: String,
    value: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF4FAF5)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(11.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                color = Color(0xFF6C776F),
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = value,
                color = Color(0xFF143D1E),
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )

            Text(
                text = subtitle,
                color = Color(0xFF6C776F),
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun DetailInfoBox(
    title: String,
    description: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEFF8F1)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(13.dp)
        ) {
            Text(
                text = title,
                color = Color(0xFF1B5E20),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = description,
                color = Color(0xFF4E5D52),
                fontSize = 12.sp,
                lineHeight = 17.sp
            )
        }
    }
}