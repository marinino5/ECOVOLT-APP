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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        containerColor = Color.Transparent
    ) { innerPadding ->

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.fondo_1),
                contentDescription = "Fondo Ecovolt",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.98f),
                                Color.White.copy(alpha = 0.93f),
                                Color.White.copy(alpha = 0.70f),
                                Color.White.copy(alpha = 0.35f),
                                Color.Transparent
                            ),
                            radius = 1120f
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.White.copy(alpha = 0.06f),
                                Color.White.copy(alpha = 0.22f),
                                Color.White.copy(alpha = 0.52f),
                                Color.White.copy(alpha = 0.84f)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(30.dp))

                HomeHeader()

                Spacer(modifier = Modifier.height(20.dp))

                StartPanel(
                    onMapClick = onMapClick
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Después podrás seleccionar el vehículo desde la flota disponible.",
                    color = Color(0xFF5D6B63),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 17.sp,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                Spacer(modifier = Modifier.weight(1f))
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
            letterSpacing = 2.6.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(13.dp))

        Text(
            text = "BIENVENIDO A\nECOVOLT",
            color = Color(0xFF132238),
            fontSize = 27.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            lineHeight = 32.sp,
            letterSpacing = 0.6.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        HomeDivider()

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Elige una estación, revisa la flota y reserva un vehículo disponible.",
            color = Color(0xFF405047),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp,
            modifier = Modifier.padding(horizontal = 14.dp)
        )
    }
}

@Composable
private fun StartPanel(
    onMapClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.84f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp, vertical = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE1F3E7)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = Color(0xFF007A3D),
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "COMIENZA DESDE EL MAPA",
                color = Color(0xFF132238),
                fontSize = 17.sp,
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
                lineHeight = 19.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            FlowPills()

            Spacer(modifier = Modifier.height(20.dp))

            UsefulInfoBlock()

            Spacer(modifier = Modifier.height(22.dp))

            Button(
                onClick = onMapClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF007A3D),
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 5.dp,
                    pressedElevation = 2.dp
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
            letterSpacing = 0.7.sp,
            modifier = Modifier.padding(start = 2.dp)
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
                .size(31.dp)
                .clip(CircleShape)
                .background(Color(0xFFEAF7EE)),
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
                selected = true,
                onClick = {},
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
}