package me.mariana.nino.proyecto_ecovoltapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import me.mariana.nino.proyecto_ecovoltapp.data.TripRepository
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth
import me.mariana.nino.proyecto_ecovoltapp.R
import me.mariana.nino.proyecto_ecovoltapp.data.Vehicle
import java.text.NumberFormat
import java.util.Locale

@Composable
fun PaymentSimulatedScreen(
    vehicle: Vehicle,
    selectedMinutes: Int,
    onBack: () -> Unit,
    onCancel: () -> Unit,
    onPaymentApproved: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    var isProcessing by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var tripSaved by remember { mutableStateOf(false) }

    val greenDark = Color(0xFF006B3F)
    val greenMedium = Color(0xFF118B4F)
    val greenButton = Color(0xFF1B8F3A)
    val greenSoft = Color(0xFFEAF7EF)
    val textDark = Color(0xFF1D1D1F)
    val textGray = Color(0xFF6E6E73)
    val borderSoft = Color(0xFFE3E8E5)

    val reservationFee = 1200
    val timeCost = selectedMinutes * vehicle.pricePerMinute
    val totalToPay = reservationFee + timeCost

    val currencyFormatter = NumberFormat.getNumberInstance(Locale("es", "CO"))

    fun formatCop(value: Int): String {
        return "${"$"}${currencyFormatter.format(value)} COP"
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo_2),
            contentDescription = "Fondo de pago",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.White.copy(alpha = 0.88f),
                            Color.White.copy(alpha = 0.96f)
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
                .padding(horizontal = 18.dp, vertical = 10.dp)
        ) {
            TextButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text(
                    text = "← Volver",
                    color = greenDark,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }

            Text(
                text = "PAGO SIMULADO",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = greenDark,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Confirma el valor de tu reserva antes de iniciar el viaje.",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 13.sp,
                color = textGray,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            PaymentGreenCard(
                total = formatCop(totalToPay),
                greenDark = greenDark,
                greenMedium = greenMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            PaymentSummaryCard(
                vehicle = vehicle,
                selectedMinutes = selectedMinutes,
                greenDark = greenDark,
                textDark = textDark,
                textGray = textGray,
                borderSoft = borderSoft
            )

            Spacer(modifier = Modifier.height(12.dp))

            PaymentDetailCard(
                pricePerMinute = "${formatCop(vehicle.pricePerMinute)}/min",
                timeCost = formatCop(timeCost),
                reservationFee = formatCop(reservationFee),
                total = formatCop(totalToPay),
                greenDark = greenDark,
                textDark = textDark,
                textGray = textGray,
                borderSoft = borderSoft
            )

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = {
                    isProcessing = true

                    coroutineScope.launch {
                        delay(1500)

                        if (!tripSaved) {
                            val userId = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()
                            TripRepository.addScheduledTrip(
                                userId = userId,
                                vehicle = vehicle,
                                minutes = selectedMinutes,
                                reservationFee = reservationFee,
                                totalPaid = totalToPay
                            )
                            tripSaved = true
                        }

                        isProcessing = false
                        showSuccessDialog = true
                    }
                },
                enabled = !isProcessing,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(17.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = greenButton,
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFF8AB99C),
                    disabledContentColor = Color.White
                )
            ) {
                if (isProcessing) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Procesando pago...",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                } else {
                    Text(
                        text = "Hacer pago",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            PaymentNotice(
                greenDark = greenDark,
                greenSoft = greenSoft
            )

            Spacer(modifier = Modifier.height(18.dp))
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = {
                Text(
                    text = "Pago completado",
                    color = greenDark,
                    fontWeight = FontWeight.ExtraBold
                )
            },
            text = {
                Text(
                    text = "Reserva exitosa. El pago simulado fue aprobado correctamente y ya puedes ver el viaje.",
                    color = textGray,
                    lineHeight = 20.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = onPaymentApproved,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = greenDark,
                        contentColor = Color.White
                    )
                ) {
                    Text("Ver mis Viajes")
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(22.dp)
        )
    }
}

@Composable
private fun PaymentGreenCard(
    total: String,
    greenDark: Color,
    greenMedium: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = greenDark
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            greenDark,
                            greenMedium
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "ECOVOLT PAY",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "DEMO",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "****  ****  ****  4242",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text(
                            text = "Método simulado",
                            color = Color.White.copy(alpha = 0.75f),
                            fontSize = 10.sp
                        )

                        Text(
                            text = "Visa prueba",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "Total",
                            color = Color.White.copy(alpha = 0.75f),
                            fontSize = 10.sp
                        )

                        Text(
                            text = total,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PaymentSummaryCard(
    vehicle: Vehicle,
    selectedMinutes: Int,
    greenDark: Color,
    textDark: Color,
    textGray: Color,
    borderSoft: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.96f)
        ),
        border = BorderStroke(1.dp, borderSoft),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            SectionTitle(
                icon = "🧾",
                title = "Resumen de la reserva",
                greenDark = greenDark
            )

            Spacer(modifier = Modifier.height(10.dp))

            InfoRow("Vehículo", vehicle.model, textDark, textGray)
            InfoRow("Código", vehicle.code, textDark, textGray)
            InfoRow("Tipo", vehicle.type.label, textDark, textGray)
            InfoRow("Tiempo reservado", "$selectedMinutes min", textDark, textGray)
            InfoRow("Batería", "${vehicle.battery}%", textDark, textGray)
            InfoRow("Ubicación", vehicle.location, textDark, textGray)
        }
    }
}

@Composable
private fun PaymentDetailCard(
    pricePerMinute: String,
    timeCost: String,
    reservationFee: String,
    total: String,
    greenDark: Color,
    textDark: Color,
    textGray: Color,
    borderSoft: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.96f)
        ),
        border = BorderStroke(1.dp, borderSoft),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            SectionTitle(
                icon = "💳",
                title = "Detalle del pago",
                greenDark = greenDark
            )

            Spacer(modifier = Modifier.height(10.dp))

            InfoRow("Tarifa", pricePerMinute, textDark, textGray)
            InfoRow("Costo por tiempo", timeCost, textDark, textGray)
            InfoRow("Base de reserva", reservationFee, textDark, textGray)

            Spacer(modifier = Modifier.height(8.dp))

            Divider(color = Color(0xFFE8ECEA))

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total a pagar",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = textDark
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = total,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = greenDark
                )
            }
        }
    }
}

@Composable
private fun PaymentNotice(
    greenDark: Color,
    greenSoft: Color
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(17.dp),
        color = greenSoft
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "🛡️",
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Este pago es simulado. No se realizará ningún cobro real al usuario.",
                fontSize = 12.sp,
                color = greenDark,
                fontWeight = FontWeight.Medium,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun SectionTitle(
    icon: String,
    title: String,
    greenDark: Color
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = icon,
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.width(7.dp))

        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold,
            color = greenDark
        )
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    valueColor: Color,
    labelColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = labelColor,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = value,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = valueColor,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1.25f),
            lineHeight = 16.sp
        )
    }
}