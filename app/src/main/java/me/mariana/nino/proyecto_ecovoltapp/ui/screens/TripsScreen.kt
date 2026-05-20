package me.mariana.nino.proyecto_ecovoltapp.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import me.mariana.nino.proyecto_ecovoltapp.R
import me.mariana.nino.proyecto_ecovoltapp.data.Trip
import me.mariana.nino.proyecto_ecovoltapp.data.TripRepository
import me.mariana.nino.proyecto_ecovoltapp.data.TripStatus
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricScooter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.border
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import me.mariana.nino.proyecto_ecovoltapp.data.VehicleRepository

@Composable
fun TripsScreen(
    onMapClick: () -> Unit,
    onFleetClick: () -> Unit,
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val greenDark = Color(0xFF006B3F)
    val greenButton = Color(0xFF1B8F3A)
    val greenSoft = Color(0xFFEAF7EF)
    val textDark = Color(0xFF1D1D1F)
    val textGray = Color(0xFF6E6E73)
    val borderSoft = Color(0xFFE3E8E5)

    val trips = TripRepository.trips
    val scheduledTrips = trips.filter { it.status == TripStatus.PROGRAMADO }
    val finishedTrips = trips.filter { it.status == TripStatus.FINALIZADO }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo_3),
            contentDescription = "Fondo viajes",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp, vertical = 14.dp)
                .padding(bottom = 86.dp)
        ) {
            Text(
                text = "MIS VIAJES",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = greenDark,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Consulta tus reservas programadas y el historial de recorridos anteriores.",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 13.sp,
                color = textGray,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Viaje programado",
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                color = textDark
            )

            Spacer(modifier = Modifier.height(10.dp))

            if (scheduledTrips.isEmpty()) {
                EmptyTripCard(
                    message = "Aún no tienes viajes programados. Cuando confirmes un pago simulado, aparecerá aquí.",
                    greenSoft = greenSoft,
                    greenDark = greenDark
                )
            } else {
                scheduledTrips.forEach { trip ->
                    TripCard(
                        trip = trip,
                        greenDark = greenDark,
                        greenButton = greenButton,
                        greenSoft = greenSoft,
                        textDark = textDark,
                        textGray = textGray,
                        borderSoft = borderSoft
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Historial anterior",
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                color = textDark
            )

            Spacer(modifier = Modifier.height(10.dp))

            if (finishedTrips.isEmpty()) {
                EmptyTripCard(
                    message = "No tienes viajes anteriores registrados.",
                    greenSoft = greenSoft,
                    greenDark = greenDark
                )
            } else {
                finishedTrips.forEach { trip ->
                    TripCard(
                        trip = trip,
                        greenDark = greenDark,
                        greenButton = greenButton,
                        greenSoft = greenSoft,
                        textDark = textDark,
                        textGray = textGray,
                        borderSoft = borderSoft
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        TripsBottomBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            onMapClick = onMapClick,
            onFleetClick = onFleetClick,
            onHomeClick = onHomeClick,
            onProfileClick = onProfileClick,
            greenDark = greenDark
        )
    }
}

@Composable
private fun TripCard(
    trip: Trip,
    greenDark: Color,
    greenButton: Color,
    greenSoft: Color,
    textDark: Color,
    textGray: Color,
    borderSoft: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.94f)
        ),
        border = BorderStroke(1.dp, borderSoft),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TripVehicleImage(
                    vehicleCode = trip.vehicleCode,
                    vehicleModel = trip.vehicleModel,
                    greenSoft = greenSoft
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 12.dp)
                ) {
                    Text(
                        text = trip.vehicleModel,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = textDark
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "${trip.vehicleCode} · ${trip.minutes} min",
                        fontSize = 12.sp,
                        color = textGray,
                        fontWeight = FontWeight.Medium
                    )
                }

                TripStatusBadge(
                    status = trip.status,
                    greenDark = greenDark,
                    greenSoft = greenSoft
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Divider(color = Color(0xFFE8ECEA))

            Spacer(modifier = Modifier.height(12.dp))

            TripInfoRow("Fecha", trip.dateText, textGray, textDark)
            TripInfoRow("Ubicación", trip.location, textGray, textDark)
            TripInfoRow("Método de pago", trip.paymentMethod, textGray, textDark)
            TripInfoRow("Total pagado", formatCop(trip.totalPaid), textGray, greenDark)

            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = greenSoft
            ) {
                Text(
                    text = if (trip.status == TripStatus.PROGRAMADO)
                        "Reserva pagada. Tu viaje quedó programado correctamente."
                    else
                        "Viaje finalizado. Este registro hace parte de tu historial.",
                    modifier = Modifier.padding(12.dp),
                    color = greenDark,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 16.sp
                )
            }
        }
    }
}
@Composable
private fun TripVehicleImage(
    vehicleCode: String,
    vehicleModel: String,
    greenSoft: Color
) {
    val vehicle = VehicleRepository.getVehicleByCode(vehicleCode)

    Box(
        modifier = Modifier
            .size(58.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color.White,
                        greenSoft
                    )
                )
            )
            .border(
                width = 1.dp,
                color = Color(0xFFDDEBE2),
                shape = RoundedCornerShape(18.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (vehicle != null) {
            Image(
                painter = painterResource(id = getVehicleImageRes(vehicle)),
                contentDescription = vehicle.model,
                modifier = Modifier.size(50.dp),
                contentScale = ContentScale.Fit
            )
        } else {
            Text(
                text = if (vehicleModel.lowercase().contains("moto")) "🏍️" else "🛴",
                fontSize = 24.sp
            )
        }
    }
}
@Composable
private fun TripStatusBadge(
    status: TripStatus,
    greenDark: Color,
    greenSoft: Color
) {
    val isScheduled = status == TripStatus.PROGRAMADO

    Surface(
        shape = RoundedCornerShape(50.dp),
        color = if (isScheduled) greenSoft else Color(0xFFF2F2F2)
    ) {
        Text(
            text = status.label.uppercase(),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            color = if (isScheduled) greenDark else Color(0xFF616161),
            fontSize = 9.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
private fun TripInfoRow(
    label: String,
    value: String,
    labelColor: Color,
    valueColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
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
            modifier = Modifier.weight(1.35f),
            lineHeight = 16.sp
        )
    }
}

@Composable
private fun EmptyTripCard(
    message: String,
    greenSoft: Color,
    greenDark: Color
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = greenSoft
    ) {
        Text(
            text = message,
            modifier = Modifier.padding(15.dp),
            color = greenDark,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 18.sp
        )
    }
}

@Composable
private fun TripsBottomBar(
    modifier: Modifier = Modifier,
    onMapClick: () -> Unit,
    onFleetClick: () -> Unit,
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit,
    greenDark: Color
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 18.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavBarItem(
                icon = Icons.Filled.LocationOn,
                label = "Mapa",
                selected = false,
                onClick = onMapClick,
                greenDark = greenDark
            )

            NavBarItem(
                icon = Icons.Filled.ElectricScooter,
                label = "Flota",
                selected = false,
                onClick = onFleetClick,
                greenDark = greenDark
            )

            Surface(
                modifier = Modifier.size(44.dp),
                shape = CircleShape,
                color = greenDark,
                shadowElevation = 3.dp,
                onClick = onHomeClick
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Inicio",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            NavBarItem(
                icon = Icons.Filled.ReceiptLong,
                label = "Viajes",
                selected = true,
                onClick = { },
                greenDark = greenDark
            )

            NavBarItem(
                icon = Icons.Filled.Person,
                label = "Perfil",
                selected = false,
                onClick = onProfileClick,
                greenDark = greenDark
            )
        }
    }
}

@Composable
private fun NavBarItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    greenDark: Color
) {
    TextButton(
        onClick = onClick,
        contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = RoundedCornerShape(50.dp),
                color = if (selected) Color(0xFFE8F3EC) else Color.Transparent
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = if (selected) greenDark else Color(0xFF5F6368),
                    modifier = Modifier
                        .padding(horizontal = 13.dp, vertical = 5.dp)
                        .size(21.dp)
                )
            }

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                color = if (selected) greenDark else Color(0xFF2E2E2E)
            )
        }
    }
}
@Composable
private fun BottomItem(
    icon: String,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    greenDark: Color
) {
    TextButton(onClick = onClick) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = icon,
                fontSize = 18.sp
            )

            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.Medium,
                color = if (selected) greenDark else Color(0xFF5F6368)
            )
        }
    }
}

private fun formatCop(value: Int): String {
    val formatter = NumberFormat.getNumberInstance(Locale("es", "CO"))
    return "$${formatter.format(value)} COP"
}