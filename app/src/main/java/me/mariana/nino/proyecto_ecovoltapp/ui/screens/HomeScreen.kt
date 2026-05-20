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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.WindowInsets
import me.mariana.nino.proyecto_ecovoltapp.R

@Composable
fun HomeScreen(
    onMapClick: () -> Unit,
    onFleetClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSupportClick: () -> Unit = {}
) {
    Scaffold(
        bottomBar = {
            HomeBottomNavigation(
                onMapClick = onMapClick,
                onFleetClick = onFleetClick,
                onHistoryClick = onHistoryClick,
                onProfileClick = onProfileClick
            )
        },
        containerColor = Color(0xFFF8FBF7),
        contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8FBF7))
        ) {
            Image(
                painter = painterResource(id = R.drawable.fondo_3),
                contentDescription = "Fondo Ecovolt",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
                    .padding(
                        top = 22.dp,
                        bottom = innerPadding.calculateBottomPadding() + 24.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HomeHeader()

                Spacer(modifier = Modifier.height(18.dp))

                StartPanel(
                    onMapClick = onMapClick
                )

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun HomeHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "INICIO",
            color = Color(0xFF007A3D),
            fontSize = 12.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 2.4.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "BIENVENIDO A\nECOVOLT",
            color = Color(0xFF132238),
            fontSize = 25.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            lineHeight = 30.sp,
            letterSpacing = 0.5.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        HomeDivider()

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Elige una estación, revisa la flota y reserva un vehículo disponible.",
            color = Color(0xFF405047),
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            lineHeight = 19.sp,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}

@Composable
private fun StartPanel(
    onMapClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE1F3E7)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color(0xFF007A3D),
                    modifier = Modifier.size(27.dp)
                )
            }

            Spacer(modifier = Modifier.height(13.dp))

            Text(
                text = "COMIENZA DESDE EL MAPA",
                color = Color(0xFF132238),
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                letterSpacing = 0.4.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Selecciona un parque cercano para ver los vehículos registrados en esa estación.",
                color = Color(0xFF536158),
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            FlowPills()

            Spacer(modifier = Modifier.height(18.dp))

            UsefulInfoBlock()

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onMapClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF007A3D),
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 3.dp,
                    pressedElevation = 1.dp
                )
            ) {
                Text(
                    text = "ABRIR MAPA",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.7.sp
                )
            }
        }
    }
}

@Composable
private fun FlowPills() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FlowPill(
            text = "Estación",
            modifier = Modifier.weight(1f)
        )

        FlowPill(
            text = "Flota",
            modifier = Modifier.weight(1f)
        )

        FlowPill(
            text = "Reserva",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun FlowPill(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
            .background(Color(0xFFEAF7EE))
            .padding(vertical = 9.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color(0xFF007A3D),
            fontSize = 11.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun UsefulInfoBlock() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFF3FAF5))
            .padding(horizontal = 14.dp, vertical = 14.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "ANTES DE RESERVAR",
                color = Color(0xFF132238),
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 0.7.sp
            )

            HomeFeatureLine(
                icon = Icons.Default.TwoWheeler,
                text = "Revisa batería, tarifa y estado del vehículo."
            )

            HomeFeatureLine(
                icon = Icons.Default.CheckCircle,
                text = "La reserva solo continúa si el vehículo está disponible."
            )
        }
    }
}

@Composable
private fun HomeFeatureLine(
    icon: ImageVector,
    text: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF007A3D),
                modifier = Modifier.size(17.dp)
            )
        }

        Text(
            text = text,
            color = Color(0xFF435249),
            fontSize = 12.sp,
            lineHeight = 17.sp,
            modifier = Modifier.padding(start = 10.dp, top = 2.dp)
        )
    }
}

@Composable
private fun HomeDivider() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(width = 42.dp, height = 1.dp)
                .background(Color(0xFF007A3D))
        )

        Box(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .size(5.dp)
                .clip(CircleShape)
                .background(Color(0xFF132238))
        )

        Box(
            modifier = Modifier
                .size(width = 42.dp, height = 1.dp)
                .background(Color(0xFF007A3D))
        )
    }
}

@Composable
private fun HomeBottomNavigation(
    onMapClick: () -> Unit,
    onFleetClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val navColors = NavigationBarItemDefaults.colors(
        selectedIconColor = Color.White,
        unselectedIconColor = Color(0xFF606C66),
        selectedTextColor = Color(0xFF007A3D),
        unselectedTextColor = Color(0xFF606C66),
        indicatorColor = Color.Transparent
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .navigationBarsPadding()
    ) {
        NavigationBar(
            containerColor = Color.White
        ) {
            NavigationBarItem(
                selected = false,
                onClick = onMapClick,
                colors = navColors,
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
                colors = navColors,
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
                selected = true,
                onClick = {},
                colors = navColors,
                icon = {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
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
                colors = navColors,
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
                colors = navColors,
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
}