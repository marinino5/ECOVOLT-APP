package me.mariana.nino.proyecto_ecovoltapp.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.mariana.nino.proyecto_ecovoltapp.R
import me.mariana.nino.proyecto_ecovoltapp.data.Vehicle
import me.mariana.nino.proyecto_ecovoltapp.data.VehicleRepository
import me.mariana.nino.proyecto_ecovoltapp.data.VehicleStatus
import me.mariana.nino.proyecto_ecovoltapp.data.VehicleType

@Composable
fun FleetScreen(
    stationName: String = "Estación Ecovolt",
    onMapClick: () -> Unit = {},
    onFleetClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onDetailClick: (String) -> Unit = {},
    onReserveClick: (String) -> Unit = {}
) {
    var selectedType by remember { mutableStateOf(VehicleType.PATINETA) }

    val vehicles = VehicleRepository.vehicles
    val filteredFleet = vehicles.filter { vehicle ->
        vehicle.type == selectedType
    }

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            FleetBottomNavigation(
                onMapClick = onMapClick,
                onFleetClick = onFleetClick,
                onHomeClick = onHomeClick,
                onHistoryClick = onHistoryClick,
                onProfileClick = onProfileClick
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.fondo_2),
                contentDescription = "Fondo de flota",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.30f))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                FleetHeader(stationName = stationName)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                ) {
                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        FleetFilterChip(
                            text = "Patinetas",
                            selected = selectedType == VehicleType.PATINETA,
                            onClick = {
                                selectedType = VehicleType.PATINETA
                            },
                            modifier = Modifier.weight(1f)
                        )

                        FleetFilterChip(
                            text = "Motos",
                            selected = selectedType == VehicleType.MOTO,
                            onClick = {
                                selectedType = VehicleType.MOTO
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        contentPadding = PaddingValues(bottom = 100.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(filteredFleet) { vehicle ->
                            FleetVehicleCard(
                                vehicle = vehicle,
                                onDetailClick = {
                                    onDetailClick(vehicle.code)
                                },
                                onReserveClick = {
                                    onReserveClick(vehicle.code)
                                }
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(18.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FleetHeader(
    stationName: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "FLOTA DISPONIBLE",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 25.sp,
                color = Color(0xFF0B5D2A),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Selecciona el vehículo que quieres revisar o reservar",
                fontSize = 13.sp,
                color = Color(0xFF263A2C),
                textAlign = TextAlign.Center,
                lineHeight = 18.sp,
                modifier = Modifier.padding(horizontal = 18.dp)
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FleetFilterChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val green = Color(0xFF0B6B35)

    FilterChip(
        selected = selected,
        onClick = onClick,
        modifier = modifier.height(44.dp),
        label = {
            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.Medium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        shape = RoundedCornerShape(16.dp),
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = Color(0xFFDFF3E4),
            selectedLabelColor = green,
            containerColor = Color.White.copy(alpha = 0.94f),
            labelColor = Color(0xFF4E5D52)
        ),
        border = null
    )
}

@Composable
private fun FleetVehicleCard(
    vehicle: Vehicle,
    onDetailClick: () -> Unit,
    onReserveClick: () -> Unit
) {
    val green = Color(0xFF0B7A3A)
    val darkGreen = Color(0xFF064D25)
    val isAvailable = vehicle.status == VehicleStatus.DISPONIBLE

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.97f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                VehicleThumbnail(vehicle = vehicle)

                Spacer(modifier = Modifier.width(14.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = vehicle.code,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 17.sp,
                            color = Color(0xFF1F2A22),
                            modifier = Modifier.weight(1f)
                        )

                        StatusBadge(status = vehicle.status)
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = vehicle.model,
                        fontSize = 13.sp,
                        color = Color(0xFF606D64),
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(9.dp))

                    BatteryIndicator(battery = vehicle.battery)

                    Spacer(modifier = Modifier.height(9.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${vehicle.pricePerMinute} COP/min",
                            fontWeight = FontWeight.Bold,
                            color = darkGreen,
                            fontSize = 13.sp
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Distancia",
                            tint = green,
                            modifier = Modifier.size(15.dp)
                        )

                        Spacer(modifier = Modifier.width(3.dp))

                        Text(
                            text = "${vehicle.distanceMeters} m",
                            color = Color(0xFF6C756F),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            if (!isAvailable) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFFFF0F0))
                        .padding(horizontal = 12.dp, vertical = 9.dp)
                ) {
                    Text(
                        text = "Este vehículo está en uso. Puedes ver el detalle, pero no reservarlo.",
                        color = Color(0xFFC62828),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = onDetailClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(46.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = darkGreen
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = "Ver detalle",
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "Detalle",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = onReserveClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(46.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isAvailable) green else Color(0xFFBDBDBD),
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Reservar",
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = if (isAvailable) "Reservar" else "En uso",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusBadge(
    status: VehicleStatus
) {
    val isAvailable = status == VehicleStatus.DISPONIBLE

    val textColor = if (isAvailable) Color(0xFF0B6B35) else Color(0xFFC62828)
    val backgroundColor = if (isAvailable) Color(0xFFDFF3E4) else Color(0xFFFFDADA)

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(backgroundColor)
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(
            text = status.label,
            color = textColor,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 10.sp
        )
    }
}

@Composable
private fun BatteryIndicator(
    battery: Int
) {
    val green = Color(0xFF0B7A3A)
    val orange = Color(0xFFC77700)
    val red = Color(0xFFC62828)

    val batteryColor = when {
        battery >= 60 -> green
        battery >= 35 -> orange
        else -> red
    }

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Batería",
                fontSize = 12.sp,
                color = Color(0xFF6C756F)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = "$battery%",
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold,
                color = batteryColor
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        LinearProgressIndicator(
            progress = { battery / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(50)),
            color = batteryColor,
            trackColor = Color(0xFFE7EDE8)
        )
    }
}

@Composable
private fun FleetBottomNavigation(
    onMapClick: () -> Unit,
    onFleetClick: () -> Unit,
    onHomeClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.navigationBarsPadding()
    ) {
        NavigationBarItem(
            selected = false,
            onClick = onMapClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Mapa"
                )
            },
            label = {
                Text("Mapa")
            }
        )

        NavigationBarItem(
            selected = true,
            onClick = onFleetClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.TwoWheeler,
                    contentDescription = "Flota"
                )
            },
            label = {
                Text("Flota")
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = onHomeClick,
            icon = {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xFF0B7A3A)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Inicio",
                        tint = Color.White
                    )
                }
            },
            label = {
                Text("")
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = onHistoryClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.ReceiptLong,
                    contentDescription = "Viajes"
                )
            },
            label = {
                Text("Viajes")
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = onProfileClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil"
                )
            },
            label = {
                Text("Perfil")
            }
        )
    }
}