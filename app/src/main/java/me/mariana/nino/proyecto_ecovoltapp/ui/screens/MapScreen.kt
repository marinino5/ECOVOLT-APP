package me.mariana.nino.proyecto_ecovoltapp.ui.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material3.Button
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import me.mariana.nino.proyecto_ecovoltapp.R
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import me.mariana.nino.proyecto_ecovoltapp.data.VehicleRepository
import me.mariana.nino.proyecto_ecovoltapp.data.VehicleStatus
import me.mariana.nino.proyecto_ecovoltapp.data.VehicleType
import android.widget.Toast
import androidx.compose.foundation.layout.width
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.google.maps.android.compose.rememberCameraPositionState

data class EcoStation(
    val name: String,
    val address: String,
    val totalVehicles: Int,
    val availableVehicles: Int,
    val inUseVehicles: Int,
    val scooterCount: Int,
    val motoCount: Int,
    val distanceText: String,
    val batteryAverage: Int,
    val position: LatLng
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    onFleetClick: (String) -> Unit = {},
    onHomeClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val bucaramanga = LatLng(7.1193, -73.1227)

    val vehicles = VehicleRepository.vehicles

    val totalVehicles = vehicles.size
    val availableVehicles = vehicles.count { it.status == VehicleStatus.DISPONIBLE }
    val inUseVehicles = vehicles.count { it.status == VehicleStatus.EN_USO }
    val scooterCount = vehicles.count { it.type == VehicleType.PATINETA }
    val motoCount = vehicles.count { it.type == VehicleType.MOTO }
    val batteryAverage = vehicles.map { it.battery }.average().toInt()

    val stations = remember {
        listOf(
            EcoStation(
                name = "Parque San Pío",
                address = "Cabecera, Bucaramanga",
                totalVehicles = totalVehicles,
                availableVehicles = availableVehicles,
                inUseVehicles = inUseVehicles,
                scooterCount = scooterCount,
                motoCount = motoCount,
                distanceText = "540 m",
                batteryAverage = batteryAverage,
                position = LatLng(7.1139, -73.1104)
            ),
            EcoStation(
                name = "Parque de los Niños",
                address = "Cra. 27, Bucaramanga",
                totalVehicles = totalVehicles,
                availableVehicles = availableVehicles,
                inUseVehicles = inUseVehicles,
                scooterCount = scooterCount,
                motoCount = motoCount,
                distanceText = "1.2 km",
                batteryAverage = batteryAverage,
                position = LatLng(7.1255, -73.1195)
            ),
            EcoStation(
                name = "Parque Santander",
                address = "Centro, Bucaramanga",
                totalVehicles = totalVehicles,
                availableVehicles = availableVehicles,
                inUseVehicles = inUseVehicles,
                scooterCount = scooterCount,
                motoCount = motoCount,
                distanceText = "1.5 km",
                batteryAverage = batteryAverage,
                position = LatLng(7.1190, -73.1258)
            ),
            EcoStation(
                name = "Parque Las Palmas",
                address = "Cabecera, Bucaramanga",
                totalVehicles = totalVehicles,
                availableVehicles = availableVehicles,
                inUseVehicles = inUseVehicles,
                scooterCount = scooterCount,
                motoCount = motoCount,
                distanceText = "850 m",
                batteryAverage = batteryAverage,
                position = LatLng(7.1151, -73.1087)
            )
        )
    }

    var selectedStation by remember { mutableStateOf<EcoStation?>(null) }
    var showStationSheet by remember { mutableStateOf(false) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(bucaramanga, 13.2f)
    }

    val sheetState: SheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            stations.forEach { station ->
                Marker(
                    state = MarkerState(position = station.position),
                    title = station.name,
                    snippet = "${station.availableVehicles} disponibles de ${station.totalVehicles} vehículos",
                    onClick = {
                        selectedStation = station
                        showStationSheet = true
                        false
                    }
                )
            }
        }

        MapTopBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 34.dp, start = 16.dp, end = 16.dp)
        )

        Column(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FloatingActionButton(
                onClick = {
                    // Luego aquí conectamos ubicación real
                },
                containerColor = Color.White,
                contentColor = Color(0xFF007A3D)
            ) {
                Icon(
                    imageVector = Icons.Default.MyLocation,
                    contentDescription = "Mi ubicación"
                )
            }

            FloatingActionButton(
                onClick = {
                    // Luego aquí conectamos filtros
                },
                containerColor = Color.White,
                contentColor = Color(0xFF007A3D)
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Filtrar estaciones"
                )
            }
        }

        MapBottomNavigation(
            modifier = Modifier.align(Alignment.BottomCenter),
            onMapClick = {
                // Ya estamos en el mapa
            },
            onFleetClick = {
                onFleetClick(selectedStation?.name ?: "Parque San Pío")
            },
            onHomeClick = onHomeClick,
            onHistoryClick = onHistoryClick,
            onProfileClick = onProfileClick
        )

        if (showStationSheet && selectedStation != null) {
            ModalBottomSheet(
                onDismissRequest = {
                    showStationSheet = false
                },
                sheetState = sheetState,
                containerColor = Color.White
            ) {
                StationDetailCard(
                    station = selectedStation!!,
                    onViewFleetClick = {
                        onFleetClick(selectedStation!!.name)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .navigationBarsPadding()
                )
            }
        }
    }
}

@Composable
private fun MapTopBar(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.96f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFE0F4E6)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Mapa de estaciones",
                    tint = Color(0xFF007A3D),
                    modifier = Modifier.size(26.dp)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 14.dp)
            ) {
                Text(
                    text = "MAPA DE ESTACIONES",
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF0B5D2A),
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Selecciona un parque para ver la flota disponible",
                    color = Color(0xFF4E5D52),
                    fontSize = 13.sp,
                    lineHeight = 17.sp
                )
            }
        }
    }
}

@Composable
private fun StationDetailCard(
    station: EcoStation,
    onViewFleetClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(bottom = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFFEFF8F1))
                .padding(18.dp)
        ) {
            Column {
                Text(
                    text = station.name,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF123D20)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color(0xFF007A3D),
                        modifier = Modifier.size(18.dp)
                    )

                    Text(
                        text = station.address,
                        modifier = Modifier.padding(start = 6.dp),
                        color = Color(0xFF4E5D52)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StationMetricBox(
                        title = "Registrados",
                        value = "${station.totalVehicles}",
                        modifier = Modifier.weight(1f)
                    )

                    StationMetricBox(
                        title = "Disponibles",
                        value = "${station.availableVehicles}",
                        modifier = Modifier.weight(1f)
                    )

                    StationMetricBox(
                        title = "En uso",
                        value = "${station.inUseVehicles}",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "${station.scooterCount} patinetas · ${station.motoCount} motos",
                    color = Color(0xFF263238),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Batería promedio: ${station.batteryAverage}%",
                    color = Color(0xFF4E5D52)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Desde tu ubicación: ${station.distanceText}",
                    color = Color(0xFF4E5D52)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onViewFleetClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF007A3D),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Ver flota disponible",
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun StationMetricBox(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                color = Color(0xFF007A3D),
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = title,
                color = Color(0xFF6A756C)
            )
        }
    }
}
@Composable
private fun MapBottomNavigation(
    modifier: Modifier = Modifier,
    onMapClick: () -> Unit,
    onFleetClick: () -> Unit,
    onHomeClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    NavigationBar(
        modifier = modifier,
        containerColor = Color.White
    ) {
        NavigationBarItem(
            selected = true,
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
            selected = false,
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