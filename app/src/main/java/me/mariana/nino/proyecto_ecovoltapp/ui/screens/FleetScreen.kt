package me.mariana.nino.proyecto_ecovoltapp.ui.screens

import androidx.annotation.DrawableRes
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
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.mariana.nino.proyecto_ecovoltapp.R

data class FleetVehicle(
    val id: String,
    val name: String,
    val type: String,
    val battery: Int,
    val pricePerMinute: Int,
    val distanceMeters: Int,
    val status: VehicleStatus,
    @DrawableRes val imageRes: Int
)

enum class VehicleStatus(
    val label: String,
    val color: Color,
    val background: Color
) {
    AVAILABLE(
        label = "Disponible",
        color = Color(0xFF0B6B35),
        background = Color(0xFFDFF3E4)
    ),
    IN_USE(
        label = "En uso",
        color = Color(0xFFC62828),
        background = Color(0xFFFFDADA)
    )
}

private val sampleFleet = listOf(
    FleetVehicle(
        id = "ECO-1258",
        name = "Patineta Eco Urbana",
        type = "Patinete",
        battery = 78,
        pricePerMinute = 450,
        distanceMeters = 120,
        status = VehicleStatus.AVAILABLE,
        imageRes = R.drawable.patineta1
    ),
    FleetVehicle(
        id = "ECO-0987",
        name = "Patineta Volt Lite",
        type = "Patinete",
        battery = 45,
        pricePerMinute = 480,
        distanceMeters = 250,
        status = VehicleStatus.AVAILABLE,
        imageRes = R.drawable.patineta2
    ),
    FleetVehicle(
        id = "MOTO-5643",
        name = "Moto Volt City",
        type = "Moto",
        battery = 67,
        pricePerMinute = 700,
        distanceMeters = 300,
        status = VehicleStatus.AVAILABLE,
        imageRes = R.drawable.moto1
    ),
    FleetVehicle(
        id = "MOTO-7712",
        name = "Moto Eco Ride",
        type = "Moto",
        battery = 30,
        pricePerMinute = 750,
        distanceMeters = 500,
        status = VehicleStatus.IN_USE,
        imageRes = R.drawable.moto2
    )
)

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
    var selectedFilter by remember { mutableStateOf("Patinetes") }

    val filteredFleet = sampleFleet.filter { vehicle ->
        when (selectedFilter) {
            "Patinetes" -> vehicle.type == "Patinete"
            "Motos" -> vehicle.type == "Moto"
            else -> vehicle.type == "Patinete"
        }
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
                contentDescription = "Fondo de flota $stationName",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.40f))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                FleetHeader()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                ) {
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        FleetFilterChip(
                            text = "Patinetes",
                            selected = selectedFilter == "Patinetes",
                            onClick = {
                                selectedFilter = "Patinetes"
                            }
                        )

                        FleetFilterChip(
                            text = "Motos",
                            selected = selectedFilter == "Motos",
                            onClick = {
                                selectedFilter = "Motos"
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(filteredFleet) { vehicle ->
                            FleetVehicleCard(
                                vehicle = vehicle,
                                onDetailClick = {
                                    onDetailClick(vehicle.id)
                                },
                                onReserveClick = {
                                    onReserveClick(vehicle.id)
                                }
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(14.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FleetHeader() {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF007A3D),
            Color(0xFF1FAE5B)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(start = 20.dp, end = 20.dp, top = 18.dp, bottom = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp))
                .background(gradientBrush)
                .padding(horizontal = 22.dp, vertical = 26.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White.copy(alpha = 0.18f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.TwoWheeler,
                        contentDescription = "Flota",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "FLOTA DISPONIBLE",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 27.sp,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Elige entre patinetes y motos eléctricas",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.92f)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FleetFilterChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val green = Color(0xFF007A3D)

    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {
            Text(
                text = text,
                fontSize = 13.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = Color(0xFFDFF3E4),
            selectedLabelColor = green,
            containerColor = Color.White.copy(alpha = 0.96f),
            labelColor = Color.DarkGray
        )
    )
}

@Composable
private fun FleetVehicleCard(
    vehicle: FleetVehicle,
    onDetailClick: () -> Unit,
    onReserveClick: () -> Unit
) {
    val green = Color(0xFF007A3D)
    val darkGreen = Color(0xFF005C2F)
    val isAvailable = vehicle.status == VehicleStatus.AVAILABLE

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.97f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(78.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFF1F6F1)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = vehicle.imageRes),
                        contentDescription = vehicle.name,
                        modifier = Modifier
                            .size(64.dp)
                            .padding(4.dp),
                        contentScale = ContentScale.Fit
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = vehicle.id,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = Color(0xFF202020),
                            modifier = Modifier.weight(1f)
                        )

                        StatusBadge(vehicle.status)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = vehicle.name,
                        fontSize = 13.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    BatteryIndicator(battery = vehicle.battery)

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$ ${vehicle.pricePerMinute} / min",
                            fontWeight = FontWeight.SemiBold,
                            color = darkGreen,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Distancia",
                            tint = green,
                            modifier = Modifier.size(15.dp)
                        )

                        Text(
                            text = "${vehicle.distanceMeters} m",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = onDetailClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp),
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
                        text = "Ver detalle",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Button(
                    onClick = onReserveClick,
                    enabled = isAvailable,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = green,
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFFE0E0E0),
                        disabledContentColor = Color.Gray
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Reservar",
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "Reservar",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusBadge(status: VehicleStatus) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(status.background)
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(
            text = status.label,
            color = status.color,
            fontWeight = FontWeight.Bold,
            fontSize = 10.sp
        )
    }
}

@Composable
private fun BatteryIndicator(battery: Int) {
    val green = Color(0xFF007A3D)
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
                color = Color.Gray
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = "$battery%",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = batteryColor
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        LinearProgressIndicator(
            progress = { battery / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .clip(RoundedCornerShape(50)),
            color = batteryColor,
            trackColor = Color(0xFFE8E8E8)
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
                        .background(Color(0xFF007A3D)),
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
                    contentDescription = "Historial"
                )
            },
            label = {
                Text("Historial")
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