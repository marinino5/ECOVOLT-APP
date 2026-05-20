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
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.mariana.nino.proyecto_ecovoltapp.data.Vehicle
import me.mariana.nino.proyecto_ecovoltapp.data.VehicleStatus

@Composable
fun ReservationScreen(
    vehicle: Vehicle,
    onBack: () -> Unit,
    onConfirmReservation: (Int) -> Unit,
    onCancel: () -> Unit
) {
    var selectedMinutes by remember { mutableIntStateOf(15) }

    val reservationFee = 1200
    val estimatedCost = reservationFee + (selectedMinutes * vehicle.pricePerMinute)

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
                .padding(horizontal = 22.dp, vertical = 14.dp)
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
                text = "CONFIRMAR RESERVA",
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF1B5E20),
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Revisa los datos del vehículo antes de continuar.",
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF4E5D52),
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

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
                        .padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ReservationVehicleImage(vehicle = vehicle)

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 14.dp)
                        ) {
                            Text(
                                text = vehicle.model,
                                color = Color(0xFF143D1E),
                                fontSize = 17.sp,
                                fontWeight = FontWeight.ExtraBold,
                                lineHeight = 21.sp
                            )

                            Spacer(modifier = Modifier.height(3.dp))

                            Text(
                                text = vehicle.code,
                                color = Color(0xFF6A756C),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.height(7.dp))

                            ReservationStatusBadge(vehicle.status)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Divider(color = Color(0xFFE0E8E2))

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Tiempo estimado",
                        color = Color(0xFF253328),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TimeOptionButton(
                            minutes = 10,
                            selectedMinutes = selectedMinutes,
                            onClick = { selectedMinutes = 10 },
                            modifier = Modifier.weight(1f)
                        )

                        TimeOptionButton(
                            minutes = 15,
                            selectedMinutes = selectedMinutes,
                            onClick = { selectedMinutes = 15 },
                            modifier = Modifier.weight(1f)
                        )

                        TimeOptionButton(
                            minutes = 30,
                            selectedMinutes = selectedMinutes,
                            onClick = { selectedMinutes = 30 },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    ReservationInfoRow(
                        label = "Tipo",
                        value = vehicle.type.label
                    )

                    ReservationInfoRow(
                        label = "Batería",
                        value = "${vehicle.battery}%"
                    )

                    ReservationInfoRow(
                        label = "Tarifa",
                        value = "$${vehicle.pricePerMinute} COP/min"
                    )

                    ReservationInfoRow(
                        label = "Base reserva",
                        value = "$reservationFee COP"
                    )

                    ReservationInfoRow(
                        label = "Ubicación",
                        value = vehicle.location
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFEFF8F1)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(15.dp)
                        ) {
                            Text(
                                text = "Resumen estimado",
                                color = Color(0xFF1B5E20),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.ExtraBold
                            )

                            Spacer(modifier = Modifier.height(7.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "$selectedMinutes minutos",
                                    color = Color(0xFF4E5D52),
                                    fontSize = 13.sp
                                )

                                Text(
                                    text = "$$estimatedCost COP",
                                    color = Color(0xFF143D1E),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }

                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                text = "Valor simulado. No se realizará ningún cobro real.",
                                color = Color(0xFF6A756C),
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                        }
                    }

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
                                text = "Este vehículo está en uso. No es posible reservarlo en este momento.",
                                modifier = Modifier.padding(13.dp),
                                color = Color(0xFFC62828),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                lineHeight = 18.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = {
                            onConfirmReservation(selectedMinutes)
                        },
                        enabled = vehicle.isAvailable,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1B8F3A),
                            contentColor = Color.White,
                            disabledContainerColor = Color(0xFFBDBDBD),
                            disabledContentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Confirmar reserva",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedButton(
                        onClick = onCancel,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFF1B5E20)
                        )
                    ) {
                        Text(
                            text = "Cancelar",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
private fun ReservationVehicleImage(
    vehicle: Vehicle
) {
    Box(
        modifier = Modifier
            .size(84.dp)
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
            modifier = Modifier.size(76.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun ReservationStatusBadge(
    status: VehicleStatus
) {
    val isAvailable = status == VehicleStatus.DISPONIBLE

    Surface(
        shape = RoundedCornerShape(50.dp),
        color = if (isAvailable) Color(0xFFE0F4E6) else Color(0xFFFFEBEE)
    ) {
        Text(
            text = status.label.uppercase(),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
            color = if (isAvailable) Color(0xFF1B8F3A) else Color(0xFFC62828),
            fontSize = 10.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
private fun TimeOptionButton(
    minutes: Int,
    selectedMinutes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isSelected = minutes == selectedMinutes

    Button(
        onClick = onClick,
        modifier = modifier.height(42.dp),
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFF1B8F3A) else Color(0xFFEFF8F1),
            contentColor = if (isSelected) Color.White else Color(0xFF1B5E20)
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Text(
            text = "${minutes} min",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ReservationInfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = Color(0xFF6A756C),
            fontSize = 12.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = value,
            color = Color(0xFF253328),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1.25f),
            lineHeight = 16.sp
        )
    }
}